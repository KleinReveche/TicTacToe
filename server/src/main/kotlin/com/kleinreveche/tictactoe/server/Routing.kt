package com.kleinreveche.tictactoe.server

import com.kleinreveche.tictactoe.domain.model.PlayerMove
import com.kleinreveche.tictactoe.server.domain.TicTacToeGameServer
import com.kleinreveche.tictactoe.server.legacy.legacyGameSocket
import com.kleinreveche.tictactoe.server.room.GameRoomManager
import com.kleinreveche.tictactoe.server.room.roomGameSocket
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.websocket.*
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json

fun Application.configureRouting(gameRoomManager: GameRoomManager) {
    routing {
        get("/status") {
            call.respondText("Hello World!")
        }

        route("/play/v1/") {
            webSocket {
                val player = game.connectPlayer(this, "Legacy")
                processTicTacToeSocket(player, game)
            }
        }

        route("/play/v2/{roomId}") {
            roomGameSocket(gameRoomManager)
        }

        post("/get_room") {
            call.respondText(gameRoomManager.getOrCreateRoom())
        }
    }
}

suspend fun DefaultWebSocketServerSession.processTicTacToeSocket(
    player: Char?,
    gameServer: TicTacToeGameServer
) {
    if (player == null) {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "2 players already connected"))
        return
    }

    try {
        incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                val message = frame.readText()
                when (val action = extractAction(message).second) {
                    is PlayerMove -> {
                        gameServer.handleMove(player, action.move, action.gameVersion)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        gameServer.disconnectPlayer(player)
    }
}

private fun extractAction(message: String): Pair<String, Any> {
    // deviceToken$action_type#{...}
    val deviceToken = message.substringBefore("|")
    val type = message.substringBefore("#").substringAfter("|")
    val payload = message.substringAfter("#")

    return when (type) {
        "player_move" -> { Pair(deviceToken, Json.decodeFromString<PlayerMove>(payload)) }
        else -> throw IllegalArgumentException("Unknown message type")
    }
}