package com.kleinreveche.tictactoe.presentation.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.model.AppSettings
import com.kleinreveche.tictactoe.platform.getPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import com.kleinreveche.tictactoe.presentation.common.components.Header
import com.kleinreveche.tictactoe.presentation.navigation.ScreenLocalVsPlayer
import com.kleinreveche.tictactoe.presentation.navigation.ScreenMainViewModel
import com.kleinreveche.tictactoe.resources.Res
import com.kleinreveche.tictactoe.resources.local_vs_player

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalVsPlayerDetails(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    vm: ScreenMainViewModel,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets.ime }
    ) {
        Column(
            modifier = modifier.padding(0.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Header(text = stringResource(Res.string.local_vs_player))

            OutlinedTextField(
                modifier = modifier,
                value = vm.player1,
                onValueChange = { vm.player1 = it },
                label = { Text("X Player Name") },
                singleLine = true,
            )

            OutlinedTextField(
                modifier = modifier,
                value = vm.player2,
                onValueChange = { vm.player2 = it },
                label = { Text("O Player Name") },
                singleLine = true,
            )

            Button(
                modifier = modifier,
                onClick = {
                    if (vm.player1 == "" || vm.player2 == "") {
                        scope.launch { snackbarHostState.showSnackbar("Please enter a player name.") }
                        return@Button
                    }

                    if (vm.player1 == vm.player2) {
                        scope.launch { snackbarHostState.showSnackbar("Player names must be different.") }
                        return@Button
                    }

                    onClick()

                    vm.player1 = vm.player1.trim()
                    vm.player2 = vm.player2.trim()

                    vm.upsertSetting(scope, AppSetting(AppSettings.LAST_PLAYER_1, vm.player1))
                    vm.upsertSetting(scope, AppSetting(AppSettings.LAST_PLAYER_2, vm.player2))
                    vm.upsertPlayer(scope, vm.player1)
                    vm.upsertPlayer(scope, vm.player2)

                    navController.navigate(
                        ScreenLocalVsPlayer(
                            player1 = vm.player1,
                            player2 = vm.player2,
                        ),
                    )
                },
            ) {
                Text("Play")
            }

            if (getPlatform().name.contains("Android")) {
                Spacer(Modifier.fillMaxWidth().height(100.dp))
            }
        }
    }
}
