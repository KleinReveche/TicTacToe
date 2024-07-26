package data.repository

import data.sources.AppSettingDao
import data.util.Converters.toAppSetting
import data.util.Converters.toAppSettingRoom
import domain.model.AppSetting
import domain.repository.AppSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingRepositoryImpl(
    private val appSettingDao: AppSettingDao,
) : AppSettingRepository {
    override suspend fun upsertAppSetting(appSetting: AppSetting) = appSettingDao.upsertAppSetting(appSetting.toAppSettingRoom())

    override fun getAppSettings(): Flow<List<AppSetting>> =
        appSettingDao.getAppSettings().map { appSettings ->
            appSettings.map { it.toAppSetting() }
        }

    override fun getAppSetting(key: String) = appSettingDao.getAppSetting(key).map { it?.toAppSetting() }
}
