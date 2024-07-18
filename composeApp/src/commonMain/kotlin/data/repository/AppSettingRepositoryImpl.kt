package data.repository

import data.sources.AppSettingDao
import domain.model.AppSetting
import domain.repository.AppSettingRepository

class AppSettingRepositoryImpl(
    private val appSettingDao: AppSettingDao
) : AppSettingRepository {
    override suspend fun upsertAppSetting(appSetting: AppSetting) =
        appSettingDao.upsertAppSetting(appSetting)

    override fun getAppSettings() = appSettingDao.getAppSettings()
    override fun getAppSetting(key: String) = appSettingDao.getAppSetting(key)
}
