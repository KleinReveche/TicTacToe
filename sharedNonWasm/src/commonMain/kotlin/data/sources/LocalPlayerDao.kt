package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.LocalPlayerRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalPlayerDao {
  @Upsert suspend fun upsertPlayer(localPlayerRoom: LocalPlayerRoom)

  @Delete suspend fun deletePlayer(localPlayerRoom: LocalPlayerRoom)

  @Query("SELECT * FROM localplayers") fun getPlayers(): Flow<List<LocalPlayerRoom>>

  @Query("SELECT * FROM localplayers WHERE name = :name COLLATE NOCASE")
  fun getPlayerByName(name: String): Flow<LocalPlayerRoom>

  @Query("SELECT EXISTS(SELECT * FROM localplayers WHERE name = :name COLLATE NOCASE)")
  suspend fun playerExists(name: String): Boolean
}
