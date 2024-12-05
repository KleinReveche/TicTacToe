package presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.LocalPlayer
import domain.repository.AppSettingRepository
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class NavigationViewModel(
    private val appSettingRepository: AppSettingRepository,
    private val localPlayerRepository: LocalPlayerRepository,
) : ViewModel() {
    @OptIn(ExperimentalUuidApi::class)
    fun saveDeviceIdentifier() {
        viewModelScope.launch {
            if (
                appSettingRepository.getAppSettings().first().firstOrNull {
                    it.setting == AppSettings.DEVICE_IDENTIFIER
                } == null
            ) {
                appSettingRepository.upsertAppSetting(
                    AppSetting(AppSettings.DEVICE_IDENTIFIER, Uuid.random().toString()),
                )
            }
        }
    }

    fun addVsComputerPlayer(data: ScreenLocalVsComputer) {
        viewModelScope.launch {
            if (localPlayerRepository.playerExists(data.playerName)) return@launch
            localPlayerRepository.upsertPlayer(LocalPlayer(name = data.playerName))
        }
    }

    fun addVsPlayerPlayers(data: ScreenLocalVsPlayer) {
        viewModelScope.launch {
            appSettingRepository.upsertAppSetting(
                AppSetting(
                    AppSettings.LAST_PLAYER_1,
                    data.player1,
                ),
            )
            appSettingRepository.upsertAppSetting(
                AppSetting(
                    AppSettings.LAST_PLAYER_2,
                    data.player2,
                ),
            )
        }
    }
}
