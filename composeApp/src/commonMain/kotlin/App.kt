import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import domain.model.AppSetting
import domain.model.AppSettings
import domain.repository.AppSettingRepository
import org.koin.compose.koinInject
import presentation.navigation.Navigation
import presentation.theme.TicTacToeTheme
import presentation.theme.UIColorTypes

@Composable
fun TicTacToeApp() {
  val appSettingRepository = koinInject<AppSettingRepository>()
  val uiColorTypeFromDb =
    appSettingRepository
      .getAppSetting(AppSettings.UI_COLOR_TYPE)
      .collectAsState(AppSetting(AppSettings.UI_COLOR_TYPE, UIColorTypes.Default.name))
      .value
  val uiColorType =
    UIColorTypes.entries.find { it.name == uiColorTypeFromDb?.value } ?: UIColorTypes.Default
  val dynamicColorAndroid =
    (appSettingRepository
        .getAppSetting(AppSettings.DYNAMIC_COLOR_ANDROID)
        .collectAsState(AppSetting(AppSettings.DYNAMIC_COLOR_ANDROID, "true"))
        .value
        ?.value ?: "true")
      .toBoolean()
  val oledMode =
    (appSettingRepository
        .getAppSetting(AppSettings.OLED)
        .collectAsState(AppSetting(AppSettings.OLED, "false"))
        .value
        ?.value ?: "false")
      .toBoolean()
  val darkMode =
    (appSettingRepository
        .getAppSetting(AppSettings.DARK_MODE)
        .collectAsState(AppSetting(AppSettings.DARK_MODE, isSystemInDarkTheme().toString()))
        .value
        ?.value ?: "true")
      .toBoolean()

  TicTacToeTheme(
    dynamicColorAndroid = dynamicColorAndroid && getPlatform().name.contains("Android"),
    uiColorTypes = uiColorType,
    darkTheme = darkMode,
    oled = oledMode,
  ) {
    Navigation()
  }
}
