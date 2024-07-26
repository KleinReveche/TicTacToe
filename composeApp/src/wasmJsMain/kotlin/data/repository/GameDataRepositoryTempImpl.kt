package data.repository

import domain.model.GameData
import domain.repository.GameDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class GameDataRepositoryTempImpl(
    private var tempGameData: List<GameData>
) : GameDataRepository {
    override suspend fun upsertGameData(gameData: GameData) {
        tempGameData += gameData
    }

    override suspend fun deleteGameData(gameData: GameData) {
        tempGameData -= gameData
    }

    override fun getAllGameData(): Flow<List<GameData>> {
        return listOf(tempGameData).asFlow()
    }
}