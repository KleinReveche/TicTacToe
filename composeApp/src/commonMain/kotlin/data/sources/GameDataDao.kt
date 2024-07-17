package data.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import domain.model.GameData
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDataDao {
    @Upsert
    suspend fun upsertGameData(gameData: GameData)

    @Delete
    suspend fun deleteGameData(gameData: GameData)

    @Query("SELECT * FROM gamedata")
    fun getAllGameData(): Flow<List<GameData>>
}
