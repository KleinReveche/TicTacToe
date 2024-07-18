package presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.cases.GetAppSettings
import domain.cases.PlayerExists
import domain.cases.UpsertAppSetting
import domain.cases.UpsertPlayer
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.Player
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NavigationViewModel(
  private val upsertAppSetting: UpsertAppSetting,
  private val getAppSettings: GetAppSettings,
  private val playerExists: PlayerExists,
  private val upsertPlayer: UpsertPlayer,
) : ViewModel() {
  fun saveDeviceIdentifier() {
    viewModelScope.launch {
      if (
        getAppSettings().first().firstOrNull { it.setting == AppSettings.DEVICE_IDENTIFIER } == null
      ) {
        upsertAppSetting(AppSetting(AppSettings.DEVICE_IDENTIFIER, UUID.randomUUID().toString()))
      }
    }
  }

  fun addPlayer(data: ScreenLocalVsComputer) {
    viewModelScope.launch {
      runBlocking {
        if (playerExists(data.playerName)) return@runBlocking
        upsertPlayer(Player(name = data.playerName))
      }
    }
  }
}
