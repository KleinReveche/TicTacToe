package data.repository

import data.sources.LocalMatchDao
import domain.model.LocalMatch
import domain.repository.LocalMatchRepository

class LocalMatchRepositoryImpl(private val localMatchDao: LocalMatchDao) : LocalMatchRepository {
  override fun getLocalMatches() = localMatchDao.getLocalMatches()

  override fun getLocalMatch(id: Int) = localMatchDao.getLocalMatch(id)

  override fun getLocalMatchByPlayer(player: String) = localMatchDao.getLocalMatchByPlayer(player)

  override fun getMatchBetweenPlayers(player1: String, player2: String) =
    localMatchDao.getMatchBetweenPlayers(player1, player2)

  override suspend fun upsertLocalMatch(localMatch: LocalMatch) =
    localMatchDao.upsertLocalMatch(localMatch)

  override suspend fun deleteLocalMatch(localMatch: LocalMatch) =
    localMatchDao.deleteLocalMatch(localMatch)
}
