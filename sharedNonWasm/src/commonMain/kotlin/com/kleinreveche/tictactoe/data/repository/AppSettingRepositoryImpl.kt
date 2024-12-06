package com.kleinreveche.tictactoe.data.repository

import com.kleinreveche.tictactoe.data.sources.AppSettingDao
import com.kleinreveche.tictactoe.data.util.Converters.toAppSetting
import com.kleinreveche.tictactoe.data.util.Converters.toAppSettingRoom
import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
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
