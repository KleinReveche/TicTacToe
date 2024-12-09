package com.kleinreveche.tictactoe.server.room

import com.kleinreveche.tictactoe.server.processTicTacToeSocket
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket

fun Route.roomGameSocket(gameRoomManager: GameRoomManager) {
    webSocket {
        val roomId = call.parameters["roomId"]
        val gameRoom: GameServerRoom? = roomId?.let { gameRoomManager.getRoom(it) }
        val player: Char?

        if (gameRoom != null) {
            player = gameRoom.connectPlayer(this, roomId)
        } else {
            call.respond(HttpStatusCode.NotFound, "Room not found")
            return@webSocket
        }

        processTicTacToeSocket(player, gameRoom)
    }
}