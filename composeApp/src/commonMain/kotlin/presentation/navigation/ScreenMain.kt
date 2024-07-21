package presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.navigation.components.LocalVsComputerDetails
import presentation.navigation.components.LocalVsPlayerDetails
import presentation.navigation.components.MultiplayerDetails
import presentation.navigation.components.SettingsBottomSheet
import presentation.navigation.components.SettingsTopAppBar
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.app_name
import tictactoe.composeapp.generated.resources.ic_branding
import tictactoe.composeapp.generated.resources.local_vs_computer
import tictactoe.composeapp.generated.resources.local_vs_player
import tictactoe.composeapp.generated.resources.multiplayer

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(navController: NavHostController) {
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  val sheetState = rememberModalBottomSheetState()
  val vm = koinViewModel<ScreenMainViewModel>()
  val defPlayer = vm.defaultPlayer.collectAsState(initial = null).value
  val lastPlayer1 = vm.lastPlayer1.collectAsState(initial = null).value
  val lastPlayer2 = vm.lastPlayer2.collectAsState(initial = null).value

  Scaffold(
    topBar = {
      SettingsTopAppBar(stringResource(Res.string.app_name)) {
        vm.showBottomSheet = !vm.showBottomSheet
      }
    },
    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    bottomBar = {
      Row(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
      ) {
        Image(painterResource(Res.drawable.ic_branding), null, Modifier.size(90.dp))
      }
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      OutlinedButton(
        onClick = {
          vm.showLocalVsComputerDetails = !vm.showLocalVsComputerDetails
          vm.player1 = defPlayer?.value ?: ""
          vm.multiplayerShowDetails = false
        }
      ) {
        Text(stringResource(Res.string.local_vs_computer))
      }

      OutlinedButton(
        onClick = {
          vm.showLocalVsPlayerDetails = !vm.showLocalVsPlayerDetails
          vm.player1 = lastPlayer1?.value ?: ""
          vm.player2 = lastPlayer2?.value ?: ""
          vm.multiplayerShowDetails = false
        }
      ) {
        Text(stringResource(Res.string.local_vs_player))
      }

      OutlinedButton(onClick = { vm.multiplayerShowDetails = !vm.multiplayerShowDetails }) {
        Text(stringResource(Res.string.multiplayer))
      }

      AnimatedVisibility(vm.multiplayerShowDetails) {
        MultiplayerDetails(
          modifier = Modifier.padding(10.dp),
          navController = navController,
          scope = scope,
          snackbarHostState = snackbarHostState,
          vm = vm,
        )
      }

      if (vm.showBottomSheet) {
        SettingsBottomSheet(
          sheetState = sheetState,
          onDismissRequest = { vm.showBottomSheet = !vm.showBottomSheet },
        )
      }

      if (vm.showLocalVsComputerDetails) {
        LocalVsComputerDetails(
          modifier = Modifier.padding(10.dp),
          sheetState = sheetState,
          onDismissRequest = { vm.showLocalVsComputerDetails = !vm.showLocalVsComputerDetails },
          navController = navController,
          scope = scope,
          snackbarHostState = snackbarHostState,
          vm = vm,
        )
      }

      if (vm.showLocalVsPlayerDetails) {
        LocalVsPlayerDetails(
          modifier = Modifier.padding(10.dp),
          sheetState = sheetState,
          onDismissRequest = { vm.showLocalVsPlayerDetails = !vm.showLocalVsPlayerDetails },
          navController = navController,
          scope = scope,
          snackbarHostState = snackbarHostState,
          vm = vm,
        )
      }
    }
  }
}
