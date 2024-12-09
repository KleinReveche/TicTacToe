package com.kleinreveche.tictactoe.server.room

import com.kleinreveche.tictactoe.server.domain.TicTacToeGameServer

class GameServerRoom(private val gameRoomManager: GameRoomManager, val roomId: String): TicTacToeGameServer() {
    override fun disconnectPlayer(player: Char) {
        super.disconnectPlayer(player)

        if (playerSockets.isEmpty()) {
            gameRoomManager.deleteRoom(roomId)
        }
    }
}