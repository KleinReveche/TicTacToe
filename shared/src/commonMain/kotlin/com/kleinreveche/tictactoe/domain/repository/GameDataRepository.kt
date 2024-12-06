package com.kleinreveche.tictactoe.domain.repository

import com.kleinreveche.tictactoe.domain.model.GameData
import kotlinx.coroutines.flow.Flow

interface GameDataRepository {
    suspend fun upsertGameData(gameData: GameData)

    suspend fun deleteGameData(gameData: GameData)

    fun getAllGameData(): Flow<List<GameData>>
}
