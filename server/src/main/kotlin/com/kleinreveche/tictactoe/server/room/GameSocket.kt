package com.kleinreveche.tictactoe.server.room

import com.kleinreveche.tictactoe.server.extractAction
import domain.model.PlayerMove
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach

fun Route.gameSocket(gameRoomManager: GameRoomManager) {
    webSocket {
        val roomId = call.parameters["roomId"]
        val gameRoom: GameRoom? = roomId?.let { gameRoomManager.getRoom(it) }
        val player: Char?

        if (gameRoom != null) {
            player = gameRoom.connectPlayer(this)
        } else {
            call.respond(HttpStatusCode.NotFound, "Room not found")
            return@webSocket
        }

        if (player == null) {
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "2 players already connected"))
            return@webSocket
        }

        try {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val message = frame.readText()
                    when (val action = extractAction(message).second) {
                        is PlayerMove -> {
                            gameRoom.handleMove(player, action.move, action.gameVersion)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            gameRoom.disconnectPlayer(player)
        }
    }
}