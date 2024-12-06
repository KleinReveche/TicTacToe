package com.kleinreveche.tictactoe.data.repository

import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.model.AppSettings
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.Storage

class AppSettingRepositoryImpl(
    private val localStorage: Storage
) : AppSettingRepository {
    private val delay: Long = 1000L

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        localStorage.setItem(appSetting.setting, appSetting.value)
    }

    override fun getAppSettings(): Flow<List<AppSetting>> = flow {
        while (true) {
            val settings = ALL_APP_SETTINGS_KEYS.mapNotNull { key ->
                localStorage.getItem(key)?.let { value ->
                    AppSetting(key, value)
                }
            }
            emit(settings)
            delay(delay)
        }
    }

    override fun getAppSetting(key: String): Flow<AppSetting?> = flow {
        while (true) {
            val value = localStorage.getItem(key)
            if (value != null) {
                emit(AppSetting(key, value))
            } else {
                emit(null)
            }
            delay(delay)
        }
    }

    companion object {
        private val ALL_APP_SETTINGS_KEYS = arrayOf(
            AppSettings.DEVICE_IDENTIFIER,
            AppSettings.LAST_PLAYER_VS_COMPUTER,
            AppSettings.DARK_MODE,
            AppSettings.DYNAMIC_COLOR_ANDROID,
            AppSettings.UI_COLOR_TYPE,
            AppSettings.OLED,
            AppSettings.LAST_PLAYER_1,
            AppSettings.LAST_PLAYER_2
        )
    }
}