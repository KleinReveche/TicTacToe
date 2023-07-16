package com.kleinreveche.tictactoe.features.local.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kleinreveche.tictactoe.R
import com.kleinreveche.tictactoe.data.local.LocalComputerDifficulty
import com.kleinreveche.tictactoe.data.local.LocalDataStoreKeys
import com.kleinreveche.tictactoe.features.local.engine.GameViewModel
import com.kleinreveche.tictactoe.features.local.ui.navigation.TicTacToeLocalNavigationRail
import com.kleinreveche.tictactoe.features.local.ui.utils.TicTacToeContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalLayout(
    modifier: Modifier = Modifier,
    ticTacToeLocalViewModel: GameViewModel,
    windowSize: WindowSizeClass,
    contentType: TicTacToeContentType,
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onButtonSheetToggle: () -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        if (contentType != TicTacToeContentType.SINGLE_PANE) {
            TicTacToeLocalNavigationRail {
                onButtonSheetToggle()
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight(),
            visible = contentType != TicTacToeContentType.SINGLE_PANE
                    && windowSize.heightSizeClass != WindowHeightSizeClass.Expanded
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val singlePlayer = ticTacToeLocalViewModel.isSinglePlayer
                StatsCounter(
                    playerXWinCount = if (singlePlayer) ticTacToeLocalViewModel.playerWinCount else ticTacToeLocalViewModel.playerXWinCount,
                    playerOWinCount = if (singlePlayer) ticTacToeLocalViewModel.aiWinCount else ticTacToeLocalViewModel.playerOWinCount,
                    drawCount = if (singlePlayer) ticTacToeLocalViewModel.drawCount else ticTacToeLocalViewModel.multiplayerDrawCount,
                    singlePlayer = singlePlayer,
                    difficulty = ticTacToeLocalViewModel.computerDifficulty,
                    currentPlayer = ticTacToeLocalViewModel.currentPlayer,
                    showDraw = ticTacToeLocalViewModel.showDraw,
                    isLandscape = true
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(if (contentType == TicTacToeContentType.SINGLE_PANE) 1f else 0.8f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val singlePlayer = ticTacToeLocalViewModel.isSinglePlayer

            AnimatedVisibility(
                visible = contentType == TicTacToeContentType.SINGLE_PANE
                        || windowSize.heightSizeClass == WindowHeightSizeClass.Expanded
            ) {
                Spacer(modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth())
                StatsCounter(
                    playerXWinCount = if (singlePlayer) ticTacToeLocalViewModel.playerWinCount else ticTacToeLocalViewModel.playerXWinCount,
                    playerOWinCount = if (singlePlayer) ticTacToeLocalViewModel.aiWinCount else ticTacToeLocalViewModel.playerOWinCount,
                    drawCount = if (singlePlayer) ticTacToeLocalViewModel.drawCount else ticTacToeLocalViewModel.multiplayerDrawCount,
                    singlePlayer = singlePlayer,
                    difficulty = ticTacToeLocalViewModel.computerDifficulty,
                    currentPlayer = ticTacToeLocalViewModel.currentPlayer,
                    showDraw = ticTacToeLocalViewModel.showDraw
                )
            }
            ButtonGrid(
                board = ticTacToeLocalViewModel.board,
                onclick = {
                    if (
                        ticTacToeLocalViewModel.board == arrayListOf(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                        && ticTacToeLocalViewModel.computerFirstMove && ticTacToeLocalViewModel.isSinglePlayer
                    )
                        ticTacToeLocalViewModel.computerPlay() else ticTacToeLocalViewModel.play(it)
                },
                windowSize = windowSize,
                winningMoves = ticTacToeLocalViewModel.winningMoves,
            )
            if (showBottomSheet) {
                TicTacToeLocalSettingsBottomSheet(
                    sheetState = sheetState,
                    isSinglePlayer = ticTacToeLocalViewModel.isSinglePlayer,
                    difficulty = ticTacToeLocalViewModel.computerDifficulty,
                    showDraw = ticTacToeLocalViewModel.showDraw,
                    computerFirst = ticTacToeLocalViewModel.computerFirstMove,
                    onPlayerModeChange = {
                        ticTacToeLocalViewModel.updatePrefs(
                            LocalDataStoreKeys.COMPUTER_AS_OPPONENT,
                            it,
                        )
                        ticTacToeLocalViewModel.updatePlayerMode(it)
                    },
                    onDifficultyChange = {
                        ticTacToeLocalViewModel.updatePrefs(
                            LocalDataStoreKeys.COMPUTER_DIFFICULTY,
                            when (it) {
                                0 -> LocalComputerDifficulty.EASY
                                1 -> LocalComputerDifficulty.NORMAL
                                2 -> LocalComputerDifficulty.HARD
                                else -> {}
                            },
                        )
                        ticTacToeLocalViewModel.updateComputerDifficulty(it)
                    },
                    onShowDrawChange = {
                        ticTacToeLocalViewModel.updatePrefs(
                            LocalDataStoreKeys.SHOW_DRAWS,
                            it,
                        )
                        ticTacToeLocalViewModel.showDraw(it)
                    },
                    onFirstMoveChange = {
                        ticTacToeLocalViewModel.updatePrefs(
                            LocalDataStoreKeys.COMPUTER_FIRST_MOVE,
                            it,
                        )
                        ticTacToeLocalViewModel.computerFirstMove(it, false)
                    },
                    onDismiss = { onButtonSheetToggle() },
                    onReset = ticTacToeLocalViewModel::resetMultiplayerStats
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalSettingsBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    isSinglePlayer: Boolean,
    difficulty: Int,
    showDraw: Boolean,
    computerFirst: Boolean,
    onPlayerModeChange: (Boolean) -> Unit,
    onShowDrawChange: (Boolean) -> Unit,
    onDifficultyChange: (Int) -> Unit,
    onFirstMoveChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                modifier = modifier.fillMaxWidth(),
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            Divider(modifier = modifier.padding(5.dp))

            TicTacToeLocalSinglePlayerModeSelector(
                singlePlayer = isSinglePlayer,
                onClickedChange = {
                    onPlayerModeChange(it)
                }
            )

            TicTacToeLocalComputerDifficultySelector(
                difficulty = difficulty,
                onClickedChange = {
                    onDifficultyChange(it)
                }
            )

            TicTacToeLocalFirstMoveSelector(
                computerFirst = computerFirst,
                onClickedChange = {
                    onFirstMoveChange(it)
                },
                isSinglePlayer = isSinglePlayer
            )

            SettingItemSwitch(
                title = stringResource(id = R.string.s_show_draw),
                description = stringResource(id = R.string.s_show_draw_desc),
                value = showDraw,
                onCheckChange = { onShowDrawChange(it) }
            )

            if (!isSinglePlayer) {
                GameButton(
                    buttonName = stringResource(id = R.string.ttc_reset_multiplayer_stats)
                ) { onReset() }
            }
        }
    }
}