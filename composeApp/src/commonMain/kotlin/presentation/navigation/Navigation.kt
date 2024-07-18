package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import domain.cases.GetAppSettings
import domain.cases.PlayerExists
import domain.cases.UpsertAppSetting
import domain.cases.UpsertPlayer
import domain.model.AppSetting
import domain.model.AppSettings
import domain.model.Player
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import presentation.localvscomputer.ScreenLocalVsComputer
import java.util.UUID

@Composable
fun Navigation() {
    KoinContext {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val upsertAppSetting = koinInject<UpsertAppSetting>()
        val getAppSettings = koinInject<GetAppSettings>()
        val playerExists = koinInject<PlayerExists>()
        val upsertPlayer = koinInject<UpsertPlayer>()

        scope.launch {
            if (getAppSettings().first()
                    .firstOrNull { it.setting == AppSettings.DEVICE_IDENTIFIER } == null
            ) {
                upsertAppSetting(
                    AppSetting(
                        AppSettings.DEVICE_IDENTIFIER,
                        UUID.randomUUID().toString()
                    )
                )
            }
        }

        NavHost(navController = navController, startDestination = ScreenMain) {
            composable<ScreenMain> { ScreenMain(navController) }
            composable<ScreenLocalVsComputer> {
                val data = it.toRoute<ScreenLocalVsComputer>()
                scope.launch {
                    runBlocking {
                        if (playerExists(data.playerName)) return@runBlocking
                        upsertPlayer(Player(name = data.playerName))
                    }
                }
                ScreenLocalVsComputer(data)
            }
            composable<ScreenLocalVsPlayer> {}
            composable<ScreenMultiplayer> {}
        }
    }
}
