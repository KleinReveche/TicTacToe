package data.repository

import data.sources.LocalMatchDao
import data.util.Converters.toLocalMatch
import data.util.Converters.toLocalMatchRoom
import domain.model.LocalMatch
import domain.repository.LocalMatchRepository
import kotlinx.coroutines.flow.map

class LocalMatchRepositoryImpl(
    private val localMatchDao: LocalMatchDao,
) : LocalMatchRepository {
    override fun getLocalMatches() =
        localMatchDao.getLocalMatches().map { localMatches ->
            localMatches.map { it.toLocalMatch() }
        }

    override fun getLocalMatch(id: Int) = localMatchDao.getLocalMatch(id).map { it?.toLocalMatch() }

    override fun getLocalMatchByPlayer(player: String) =
        localMatchDao.getLocalMatchByPlayer(player).map { localMatches ->
            localMatches.map { it.toLocalMatch() }
        }

    override fun getMatchBetweenPlayers(
        player1: String,
        player2: String,
    ) = localMatchDao.getMatchBetweenPlayers(player1, player2).map { it?.toLocalMatch() }

    override suspend fun upsertLocalMatch(localMatch: LocalMatch) = localMatchDao.upsertLocalMatch(localMatch.toLocalMatchRoom())

    override suspend fun deleteLocalMatch(localMatch: LocalMatch) = localMatchDao.deleteLocalMatch(localMatch.toLocalMatchRoom())
}
