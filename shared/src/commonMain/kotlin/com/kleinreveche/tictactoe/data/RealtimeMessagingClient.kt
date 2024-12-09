package com.kleinreveche.tictactoe.data

import com.kleinreveche.tictactoe.domain.model.PlayerMove
import com.kleinreveche.tictactoe.domain.model.ServerGameState
import kotlinx.coroutines.flow.Flow

interface RealtimeMessagingClient {
    fun getGameStateStream(): Flow<ServerGameState>
    suspend fun sendAction(action: PlayerMove)
    suspend fun close()
}