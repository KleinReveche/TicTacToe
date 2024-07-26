package data.repository

import domain.model.LocalMatch
import domain.repository.LocalMatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class LocalMatchRepositoryTempImpl(
    private var tempLocalMatches: List<LocalMatch>
) : LocalMatchRepository {
    override fun getLocalMatches(): Flow<List<LocalMatch>> {
        return listOf(tempLocalMatches).asFlow()
    }

    override fun getLocalMatch(id: Int): Flow<LocalMatch?> {
        return tempLocalMatches.find { it.id == id }.let {
            if (it != null) {
                listOf(it).asFlow()
            } else {
                emptyList<LocalMatch>().asFlow()
            }
        }
    }

    override fun getLocalMatchByPlayer(player: String): Flow<List<LocalMatch>> {
        return tempLocalMatches.filter { it.player1 == player || it.player2 == player }.let {
            listOf(it).asFlow()
        }
    }

    override fun getMatchBetweenPlayers(player1: String, player2: String): Flow<LocalMatch?> {
        return tempLocalMatches
            .find { (it.player1 == player1 && it.player2 == player2) || (it.player1 == player2 && it.player2 == player1) }
            .let {
                if (it != null) {
                    listOf(it).asFlow()
                } else {
                    emptyList<LocalMatch>().asFlow()
                }
            }
    }

    override suspend fun upsertLocalMatch(localMatch: LocalMatch) {
        tempLocalMatches += localMatch
    }

    override suspend fun deleteLocalMatch(localMatch: LocalMatch) {
        tempLocalMatches -= localMatch
    }
}