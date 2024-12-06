package com.kleinreveche.tictactoe.presentation.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kleinreveche.tictactoe.data.game.ComputerDifficulty
import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.model.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import com.kleinreveche.tictactoe.presentation.common.components.Header
import com.kleinreveche.tictactoe.presentation.common.components.TitleAndDescription
import com.kleinreveche.tictactoe.presentation.navigation.ScreenLocalVsComputer
import com.kleinreveche.tictactoe.presentation.navigation.ScreenMainViewModel
import com.kleinreveche.tictactoe.resources.Res
import com.kleinreveche.tictactoe.resources.choose_difficulty
import com.kleinreveche.tictactoe.resources.choose_symbol
import com.kleinreveche.tictactoe.resources.easy
import com.kleinreveche.tictactoe.resources.insane
import com.kleinreveche.tictactoe.resources.local_vs_computer
import com.kleinreveche.tictactoe.resources.normal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalVsComputerDetails(
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
        val computerDifficultyTypes =
            arrayOf(
                stringResource(Res.string.easy),
                stringResource(Res.string.normal),
                stringResource(Res.string.insane),
            )

        Column(
            modifier = modifier.padding(0.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Header(text = stringResource(Res.string.local_vs_computer))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = modifier.weight(0.65f),
                    value = vm.player1,
                    onValueChange = { vm.player1 = it },
                    label = { Text("Player Name") },
                    singleLine = true,
                )

                Button(
                    modifier = modifier.weight(0.35f),
                    onClick = {
                        if (vm.player1 == "") {
                            scope.launch { snackbarHostState.showSnackbar("Please enter a player name.") }
                            return@Button
                        }

                        onClick()

                        vm.upsertSetting(
                            scope,
                            AppSetting(AppSettings.LAST_PLAYER_VS_COMPUTER, vm.player1),
                        )
                        vm.upsertPlayer(scope, vm.player1)

                        navController.navigate(
                            ScreenLocalVsComputer(
                                vm.player1,
                                vm.playerTypes[vm.singleplayerType].toString(),
                                ComputerDifficulty.entries[vm.computerDifficulty].ordinal.toString(),
                            ),
                        )
                    },
                ) {
                    Text("Play")
                }
            }

            TitleAndDescription(stringResource(Res.string.choose_symbol))

            SingleChoiceSegmentedButtonRow {
                vm.playerTypes.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape =
                            SegmentedButtonDefaults.itemShape(
                                count = vm.playerTypes.size,
                                index = index,
                            ),
                        onClick = { vm.singleplayerType = index },
                        selected = index == vm.singleplayerType,
                    ) {
                        Text(label.toString())
                    }
                }
            }

            TitleAndDescription(stringResource(Res.string.choose_difficulty))

            SingleChoiceSegmentedButtonRow {
                computerDifficultyTypes.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape =
                            SegmentedButtonDefaults.itemShape(
                                count = computerDifficultyTypes.size,
                                index = index,
                            ),
                        onClick = { vm.computerDifficulty = index },
                        selected = index == vm.computerDifficulty,
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}
