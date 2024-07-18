package presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import domain.cases.GetAppSetting
import domain.cases.UpsertAppSetting
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ScreenMainViewModel(
  private val upsertAppSetting: UpsertAppSetting,
  private val getAppSetting: GetAppSetting,
) : ViewModel() {
  val defaultPlayer = getAppSetting(AppSettings.DEFAULT_PLAYER_VS_COMPUTER)
  var localVsComputerShowDetails by mutableStateOf(false)
  var localVsPlayerShowDetails by mutableStateOf(false)
  var multiplayerShowDetails by mutableStateOf(false)
  var player1 by mutableStateOf("")
  var player2 by mutableStateOf("")
  var singleplayerType by mutableIntStateOf(0)
  val playerTypes = arrayOf(PLAYER_X, PLAYER_O)
  var computerDifficulty by mutableIntStateOf(0)

  fun upsertSetting(scope: CoroutineScope, appSetting: AppSetting) {
    scope.launch { upsertAppSetting(appSetting) }
  }
}
