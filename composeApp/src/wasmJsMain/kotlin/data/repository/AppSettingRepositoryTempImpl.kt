package data.repository

import domain.model.AppSetting
import domain.repository.AppSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class AppSettingRepositoryTempImpl(
    private var tempAppSettings: List<AppSetting>
) : AppSettingRepository {
    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        tempAppSettings += appSetting
    }

    override fun getAppSettings(): Flow<List<AppSetting>> = listOf(tempAppSettings).asFlow()

    override fun getAppSetting(key: String): Flow<AppSetting?> =
        tempAppSettings.find { it.setting == key }.let {
            if (it != null) {
                listOf(it).asFlow()
            } else {
                emptyList<AppSetting>().asFlow()
            }
        }

}