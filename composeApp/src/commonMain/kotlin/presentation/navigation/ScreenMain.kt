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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
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
  KoinContext {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val vm = koinViewModel<ScreenMainViewModel>()
    val defPlayer = vm.defaultPlayer.collectAsState(initial = null)
    vm.player1 = defPlayer.value?.value ?: ""

    Scaffold(
      topBar = {
        SettingsTopAppBar(stringResource(Res.string.app_name)) {
          showBottomSheet = !showBottomSheet
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
            vm.localVsComputerShowDetails = !vm.localVsComputerShowDetails
            vm.localVsPlayerShowDetails = false
            vm.multiplayerShowDetails = false
          }
        ) {
          Text(stringResource(Res.string.local_vs_computer))
        }

        AnimatedVisibility(vm.localVsComputerShowDetails) {
          LocalVsComputerDetails(
            modifier = Modifier.padding(10.dp),
            navController = navController,
            scope = scope,
            snackbarHostState = snackbarHostState,
            vm = vm,
          )
        }

        OutlinedButton(
          onClick = {
            vm.localVsPlayerShowDetails = !vm.localVsPlayerShowDetails
            vm.localVsComputerShowDetails = false
            vm.multiplayerShowDetails = false
          }
        ) {
          Text(stringResource(Res.string.local_vs_player))
        }

        AnimatedVisibility(vm.localVsPlayerShowDetails) {
          LocalVsPlayerDetails(
            modifier = Modifier.padding(10.dp),
            navController = navController,
            scope = scope,
            snackbarHostState = snackbarHostState,
            vm = vm,
          )
        }

        OutlinedButton(
          onClick = {
            vm.multiplayerShowDetails = !vm.multiplayerShowDetails
            vm.localVsPlayerShowDetails = false
            vm.localVsComputerShowDetails = false
          }
        ) {
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

        if (showBottomSheet) {
          SettingsBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = !showBottomSheet },
          )
        }
      }
    }
  }
}
