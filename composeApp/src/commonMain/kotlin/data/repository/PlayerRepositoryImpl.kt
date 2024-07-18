package data.repository

import data.sources.PlayerDao
import domain.model.Player
import domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class PlayerRepositoryImpl(private val playerDao: PlayerDao) : PlayerRepository {
  override suspend fun upsertPlayer(player: Player) = playerDao.upsertPlayer(player)

  override suspend fun deletePlayer(player: Player) = playerDao.deletePlayer(player)

  override fun getPlayers(): Flow<List<Player>> = playerDao.getPlayers()

  override fun getPlayerByName(name: String): Flow<Player> = playerDao.getPlayerByName(name)

  override suspend fun playerExists(name: String): Boolean = playerDao.playerExists(name)
}
