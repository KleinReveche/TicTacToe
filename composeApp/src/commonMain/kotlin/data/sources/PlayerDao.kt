package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
  @Upsert suspend fun upsertPlayer(player: Player)

  @Delete suspend fun deletePlayer(player: Player)

  @Query("SELECT * FROM player") fun getPlayers(): Flow<List<Player>>

  @Query("SELECT * FROM player WHERE name = :name") fun getPlayerByName(name: String): Flow<Player>

  @Query("SELECT EXISTS(SELECT * FROM player WHERE name = :name)")
  suspend fun playerExists(name: String): Boolean
}
