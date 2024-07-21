package data.repository

import data.sources.PlayerVsComputerDao
import domain.model.LocalPlayer
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.Flow

class LocalPlayerRepositoryImpl(private val playerVsComputerDao: PlayerVsComputerDao) :
  LocalPlayerRepository {
  override suspend fun upsertPlayer(localPlayer: LocalPlayer) =
    playerVsComputerDao.upsertPlayer(localPlayer)

  override suspend fun deletePlayer(localPlayer: LocalPlayer) =
    playerVsComputerDao.deletePlayer(localPlayer)

  override fun getPlayers(): Flow<List<LocalPlayer>> = playerVsComputerDao.getPlayers()

  override fun getPlayerByName(name: String): Flow<LocalPlayer> =
    playerVsComputerDao.getPlayerByName(name)

  override suspend fun playerExists(name: String): Boolean = playerVsComputerDao.playerExists(name)
}
