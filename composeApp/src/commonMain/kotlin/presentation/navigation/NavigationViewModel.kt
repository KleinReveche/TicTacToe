package presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.LocalPlayer
import domain.repository.AppSettingRepository
import domain.repository.LocalPlayerRepository
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NavigationViewModel(
  private val appSettingRepository: AppSettingRepository,
  private val localPlayerRepository: LocalPlayerRepository,
) : ViewModel() {
  fun saveDeviceIdentifier() {
    viewModelScope.launch {
      if (
        appSettingRepository.getAppSettings().first().firstOrNull {
          it.setting == AppSettings.DEVICE_IDENTIFIER
        } == null
      ) {
        appSettingRepository.upsertAppSetting(
          AppSetting(AppSettings.DEVICE_IDENTIFIER, UUID.randomUUID().toString())
        )
      }
    }
  }

  fun addVsComputerPlayer(data: ScreenLocalVsComputer) {
    viewModelScope.launch {
      runBlocking {
        if (localPlayerRepository.playerExists(data.playerName)) return@runBlocking
        localPlayerRepository.upsertPlayer(LocalPlayer(name = data.playerName))
      }
    }
  }

  fun addVsPlayerPlayers(data: ScreenLocalVsPlayer) {
    viewModelScope.launch {
      runBlocking {
        appSettingRepository.upsertAppSetting(AppSetting(AppSettings.LAST_PLAYER_1, data.player1))
        appSettingRepository.upsertAppSetting(AppSetting(AppSettings.LAST_PLAYER_2, data.player2))
      }
    }
  }
}
