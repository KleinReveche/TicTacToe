package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.LocalMatchRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalMatchDao {
  @Query("SELECT * FROM localmatches") fun getLocalMatches(): Flow<List<LocalMatchRoom>>

  @Query("SELECT * FROM localmatches WHERE id = :id") fun getLocalMatch(id: Int): Flow<LocalMatchRoom?>

  @Query(
    "SELECT * FROM localmatches WHERE player1 = :player COLLATE NOCASE OR player2 = :player COLLATE NOCASE"
  )
  fun getLocalMatchByPlayer(player: String): Flow<List<LocalMatchRoom>>

  @Query(
    "SELECT * FROM localMatches WHERE (player1 = :player1 COLLATE NOCASE AND player2 = :player2 COLLATE NOCASE) OR (player1 = :player2 COLLATE NOCASE AND player2 = :player1 COLLATE NOCASE)"
  )
  fun getMatchBetweenPlayers(player1: String, player2: String): Flow<LocalMatchRoom?>

  @Upsert suspend fun upsertLocalMatch(localMatchRoom: LocalMatchRoom)

  @Delete suspend fun deleteLocalMatch(localMatchRoom: LocalMatchRoom)
}
