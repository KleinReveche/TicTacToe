package com.kleinreveche.tictactoe.data.repository

import com.kleinreveche.tictactoe.data.sources.GameDataDao
import com.kleinreveche.tictactoe.data.util.Converters.toGameData
import com.kleinreveche.tictactoe.data.util.Converters.toGameDataRoom
import com.kleinreveche.tictactoe.domain.model.GameData
import com.kleinreveche.tictactoe.domain.repository.GameDataRepository
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
