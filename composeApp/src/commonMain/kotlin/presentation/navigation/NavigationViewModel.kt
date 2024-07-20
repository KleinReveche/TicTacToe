package presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.Player
import domain.repository.AppSettingRepository
import domain.repository.PlayerRepository
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NavigationViewModel(
  private val appSettingRepository: AppSettingRepository,
  private val playerRepository: PlayerRepository,
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

  fun addPlayer(data: ScreenLocalVsComputer) {
    viewModelScope.launch {
      runBlocking {
        if (playerRepository.playerExists(data.playerName)) return@runBlocking
        playerRepository.upsertPlayer(Player(name = data.playerName))
      }
    }
  }
}
