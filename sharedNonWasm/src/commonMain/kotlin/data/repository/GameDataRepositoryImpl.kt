package data.repository

import data.sources.GameDataDao
import data.util.Converters.toGameData
import data.util.Converters.toGameDataRoom
import domain.model.GameData
import domain.repository.GameDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameDataRepositoryImpl(
    private val gameDataDao: GameDataDao,
) : GameDataRepository {
    override suspend fun upsertGameData(gameData: GameData) = gameDataDao.upsertGameData(gameData.toGameDataRoom())

    override suspend fun deleteGameData(gameData: GameData) = gameDataDao.deleteGameData(gameData.toGameDataRoom())

    override fun getAllGameData(): Flow<List<GameData>> =
        gameDataDao.getAllGameData().map { gameDataList ->
            gameDataList.map { it.toGameData() }
        }
}
