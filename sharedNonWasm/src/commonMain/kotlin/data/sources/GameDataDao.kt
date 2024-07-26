package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.GameDataRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDataDao {
  @Upsert suspend fun upsertGameData(gameDataRoom: GameDataRoom)

  @Delete suspend fun deleteGameData(gameDataRoom: GameDataRoom)

  @Query("SELECT * FROM gamedata") fun getAllGameData(): Flow<List<GameDataRoom>>
}
