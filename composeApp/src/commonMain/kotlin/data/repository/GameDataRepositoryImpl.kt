package data.repository

import data.sources.GameDataDao
import domain.model.GameData
import domain.repository.GameDataRepository
import kotlinx.coroutines.flow.Flow

class GameDataRepositoryImpl(
    private val gameDataDao: GameDataDao
) : GameDataRepository {
    override suspend fun upsertGameData(gameData: GameData) = gameDataDao.upsertGameData(gameData)
    override suspend fun deleteGameData(gameData: GameData) = gameDataDao.deleteGameData(gameData)
    override fun getAllGameData(): Flow<List<GameData>> = gameDataDao.getAllGameData()
}
