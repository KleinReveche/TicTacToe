package domain.repository

import domain.model.GameData
import kotlinx.coroutines.flow.Flow

interface GameDataRepository {
    suspend fun upsertGameData(gameData: GameData)
    suspend fun deleteGameData(gameData: GameData)
    fun getAllGameData(): Flow<List<GameData>>
}
