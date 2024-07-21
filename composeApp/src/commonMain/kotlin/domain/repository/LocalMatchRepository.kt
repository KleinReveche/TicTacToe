package domain.repository

import domain.model.LocalMatch
import kotlinx.coroutines.flow.Flow

interface LocalMatchRepository {
  fun getLocalMatches(): Flow<List<LocalMatch>>

  fun getLocalMatch(id: Int): Flow<LocalMatch?>

  fun getLocalMatchByPlayer(player: String): Flow<List<LocalMatch>>

  fun getMatchBetweenPlayers(player1: String, player2: String): Flow<LocalMatch?>

  suspend fun upsertLocalMatch(localMatch: LocalMatch)

  suspend fun deleteLocalMatch(localMatch: LocalMatch)
}
