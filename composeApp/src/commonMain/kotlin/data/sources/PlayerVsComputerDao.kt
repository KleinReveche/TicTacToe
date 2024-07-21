package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.LocalPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerVsComputerDao {
  @Upsert suspend fun upsertPlayer(localPlayer: LocalPlayer)

  @Delete suspend fun deletePlayer(localPlayer: LocalPlayer)

  @Query("SELECT * FROM localplayer") fun getPlayers(): Flow<List<LocalPlayer>>

  @Query("SELECT * FROM localplayer WHERE name = :name COLLATE NOCASE")
  fun getPlayerByName(name: String): Flow<LocalPlayer>

  @Query("SELECT EXISTS(SELECT * FROM localplayer WHERE name = :name COLLATE NOCASE)")
  suspend fun playerExists(name: String): Boolean
}
