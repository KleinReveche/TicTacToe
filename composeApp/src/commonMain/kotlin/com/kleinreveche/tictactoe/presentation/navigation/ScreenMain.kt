package com.kleinreveche.tictactoe.presentation.navigation

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.kleinreveche.tictactoe.platform.getPlatform
import com.kleinreveche.tictactoe.presentation.navigation.components.LocalVsComputerDetails
import com.kleinreveche.tictactoe.presentation.navigation.components.LocalVsPlayerDetails
import com.kleinreveche.tictactoe.presentation.navigation.components.MultiplayerDetails
import com.kleinreveche.tictactoe.presentation.navigation.components.SettingsBottomSheet
import com.kleinreveche.tictactoe.presentation.navigation.components.SettingsTopAppBar
import com.kleinreveche.tictactoe.resources.Res
import com.kleinreveche.tictactoe.resources.app_name
import com.kleinreveche.tictactoe.resources.ic_branding
import com.kleinreveche.tictactoe.resources.local_vs_computer
import com.kleinreveche.tictactoe.resources.local_vs_player
import com.kleinreveche.tictactoe.resources.multiplayer

@OptIn(ExperimentalMaterial3Api::class)
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
                },
            ) {
                Text(stringResource(Res.string.local_vs_computer))
            }

            OutlinedButton(
                onClick = {
                    vm.showLocalVsPlayerDetails = !vm.showLocalVsPlayerDetails
                    vm.player1 = lastPlayer1?.value ?: ""
                    vm.player2 = lastPlayer2?.value ?: ""
                    vm.multiplayerShowDetails = false
                },
            ) {
                Text(stringResource(Res.string.local_vs_player))
            }

            OutlinedButton(onClick = { navController.navigate(ScreenMultiplayer) }) {
                Text(stringResource(Res.string.multiplayer))
            }

            // TODO: REMOVE THIS
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
                    onDismissRequest = {
                        vm.showLocalVsComputerDetails = !vm.showLocalVsComputerDetails
                    },
                    onClick = {
                        vm.showLocalVsComputerDetails = !vm.showLocalVsComputerDetails
                    },
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
                    onDismissRequest = {
                        vm.showLocalVsPlayerDetails = !vm.showLocalVsPlayerDetails
                    },
                    onClick = {
                        vm.showLocalVsPlayerDetails = !vm.showLocalVsPlayerDetails
                    },
                    navController = navController,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    vm = vm,
                )
            }

            if (getPlatform().name.contains("Web") && vm.showWebDisclaimerSnackbar) {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Web version is still in development. Features may not work as expected.",
                        actionLabel = "I UNDERSTAND",
                        duration = SnackbarDuration.Indefinite
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            vm.showWebDisclaimerSnackbar = false
                        }

                        SnackbarResult.Dismissed -> {
                            vm.showWebDisclaimerSnackbar = false
                        }
                    }
                }
            }
        }
    }
}
