package data.sources

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import domain.model.AppSetting
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AppSettingDao {

    @Query("SELECT * FROM appsetting")
    abstract fun getAppSettings(): Flow<List<AppSetting>>

    @Query("SELECT * FROM appsetting WHERE setting = :key")
    abstract fun getAppSetting(key: String): Flow<AppSetting?>

    @Upsert
    abstract suspend fun upsertAppSetting(appSetting: AppSetting)
}
