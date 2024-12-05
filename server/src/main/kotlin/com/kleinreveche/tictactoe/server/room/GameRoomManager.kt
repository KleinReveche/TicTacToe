package com.kleinreveche.tictactoe.server.room

class GameRoomManager {
    private val rooms = mutableMapOf<String, GameRoom>()

    private fun createRoom(): String {
        val roomChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val roomId = (1..6).map { roomChars.random() }.joinToString("")
        rooms[roomId] = GameRoom(this, roomId)
        return roomId
    }

    fun getRoom(roomId: String): GameRoom? { return rooms[roomId] }
    fun deleteRoom(roomId: String) { rooms.remove(roomId) }

    fun getOrCreateRoom(): String {
        return if (rooms.values.firstOrNull { it.getNumberOfPlayers() < 2 } != null) {
            rooms.values.first { it.getNumberOfPlayers() < 2 }.roomId
        } else {
            createRoom()
        }
    }
}