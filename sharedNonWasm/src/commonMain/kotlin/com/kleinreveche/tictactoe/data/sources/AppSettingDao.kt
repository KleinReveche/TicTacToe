package com.kleinreveche.tictactoe.data.sources

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kleinreveche.tictactoe.domain.model.AppSettingRoom
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AppSettingDao {

  @Query("SELECT * FROM appsettings") abstract fun getAppSettings(): Flow<List<AppSettingRoom>>

  @Query("SELECT * FROM appsettings WHERE setting = :key")
  abstract fun getAppSetting(key: String): Flow<AppSettingRoom?>

  @Upsert abstract suspend fun upsertAppSetting(appSettingRoom: AppSettingRoom)
}
