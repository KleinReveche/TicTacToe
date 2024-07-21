package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.LocalMatch
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalMatchDao {
  @Query("SELECT * FROM localmatch") fun getLocalMatches(): Flow<List<LocalMatch>>

  @Query("SELECT * FROM localmatch WHERE id = :id") fun getLocalMatch(id: Int): Flow<LocalMatch?>

  @Query(
    "SELECT * FROM localmatch WHERE player1 = :player COLLATE NOCASE OR player2 = :player COLLATE NOCASE"
  )
  fun getLocalMatchByPlayer(player: String): Flow<List<LocalMatch>>

  @Query(
    "SELECT * FROM LocalMatch WHERE (player1 = :player1 COLLATE NOCASE AND player2 = :player2 COLLATE NOCASE) OR (player1 = :player2 COLLATE NOCASE AND player2 = :player1 COLLATE NOCASE)"
  )
  fun getMatchBetweenPlayers(player1: String, player2: String): Flow<LocalMatch?>

  @Upsert suspend fun upsertLocalMatch(localMatch: LocalMatch)

  @Delete suspend fun deleteLocalMatch(localMatch: LocalMatch)
}
