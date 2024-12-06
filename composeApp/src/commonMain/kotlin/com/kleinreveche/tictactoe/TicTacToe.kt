package com.kleinreveche.tictactoe

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.model.AppSettings
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
import com.kleinreveche.tictactoe.platform.getPlatform
import org.koin.compose.koinInject
import com.kleinreveche.tictactoe.presentation.navigation.Navigation
import com.kleinreveche.tictactoe.presentation.theme.TicTacToeTheme
import com.kleinreveche.tictactoe.presentation.theme.UIColorTypes

@Composable
fun TicTacToe() {
    val appSettingRepository = koinInject<AppSettingRepository>()
    val uiColorTypeFromDb =
        appSettingRepository
            .getAppSetting(AppSettings.UI_COLOR_TYPE)
            .collectAsState(AppSetting(AppSettings.UI_COLOR_TYPE, UIColorTypes.Default.name))
            .value
    val uiColorType =
        UIColorTypes.entries.find { it.name == uiColorTypeFromDb?.value } ?: UIColorTypes.Default
    val dynamicColorAndroid =
        (
            appSettingRepository
                .getAppSetting(AppSettings.DYNAMIC_COLOR_ANDROID)
                .collectAsState(AppSetting(AppSettings.DYNAMIC_COLOR_ANDROID, "true"))
                .value
                ?.value ?: "true"
        ).toBoolean()
    val oledMode =
        (
            appSettingRepository
                .getAppSetting(AppSettings.OLED)
                .collectAsState(AppSetting(AppSettings.OLED, "false"))
                .value
                ?.value ?: "false"
        ).toBoolean()
    val darkMode =
        (
            appSettingRepository
                .getAppSetting(AppSettings.DARK_MODE)
                .collectAsState(
                    AppSetting(
                        AppSettings.DARK_MODE,
                        isSystemInDarkTheme().toString(),
                    ),
                ).value
                ?.value ?: "true"
        ).toBoolean()

    TicTacToeTheme(
        dynamicColorAndroid = dynamicColorAndroid && getPlatform().name.contains("Android"),
        uiColorTypes = uiColorType,
        darkTheme = darkMode,
        oled = oledMode,
    ) {
        Navigation()
    }
}
