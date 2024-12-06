package com.kleinreveche.tictactoe.domain.repository

import com.kleinreveche.tictactoe.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow

interface AppSettingRepository {
    suspend fun upsertAppSetting(appSetting: AppSetting)

    fun getAppSettings(): Flow<List<AppSetting>>

    fun getAppSetting(key: String): Flow<AppSetting?>
}
