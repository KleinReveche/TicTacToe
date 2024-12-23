package com.kleinreveche.tictactoe.presentation.localvscomputer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kleinreveche.tictactoe.data.game.ComputerDifficulty
import com.kleinreveche.tictactoe.domain.model.GameResult
import com.kleinreveche.tictactoe.domain.model.PLAYER_O
import com.kleinreveche.tictactoe.domain.model.PLAYER_X
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import com.kleinreveche.tictactoe.presentation.common.components.BackHistoryTopAppBar
import com.kleinreveche.tictactoe.presentation.common.components.GameHistoryBottomSheet
import com.kleinreveche.tictactoe.presentation.common.components.LocalPlayerDetailsBottomSheet
import com.kleinreveche.tictactoe.presentation.common.components.StatCounter
import com.kleinreveche.tictactoe.presentation.common.components.TicTacToeGrid
import com.kleinreveche.tictactoe.presentation.navigation.ScreenLocalVsComputer
import com.kleinreveche.tictactoe.resources.Res
import com.kleinreveche.tictactoe.resources.restart
import com.kleinreveche.tictactoe.resources.start
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocalVsComputer(
    screenData: ScreenLocalVsComputer,
    navController: NavController,
) {
    val sheetState = rememberModalBottomSheetState()
    val playerName = screenData.playerName
    val playerType = screenData.playerType[0]
    val computerDifficulty = ComputerDifficulty.entries[screenData.difficulty[0].digitToInt()]
    val player1Name = if (playerType == PLAYER_X) playerName else "AI: ${computerDifficulty.name}"
    val player2Name = if (playerType == PLAYER_O) playerName else "AI: ${computerDifficulty.name}"
    val vm =
        koinViewModel<ScreenLocalVsComputerViewModel> {
            parametersOf(player1Name, player2Name, playerType, computerDifficulty)
        }
    val player = vm.getPlayerByName(playerName)
    val gameData =
        vm.gameData
            .collectAsState(initial = emptyList())
            .value
            .filter { gd -> listOf(gd.player1Name, gd.player2Name).any { it.startsWith("AI") } }
            .sortedByDescending { it.date }

    LaunchedEffect(player) {
        if (vm.localPlayer == null) {
            vm.updatePlayer(player.first())
        }
    }

    LaunchedEffect(vm.delayComputerMove) {
        if (vm.roundCount == 0) return@LaunchedEffect
        vm.computerMoveStatus = true
        val randomMaxDelay =
            when (computerDifficulty) {
                ComputerDifficulty.Easy -> 2000L
                ComputerDifficulty.Normal -> 3000L
                ComputerDifficulty.Insane -> 4500L
            }
        delay(Random(Clock.System.now().epochSeconds).nextLong(1000, randomMaxDelay))
        vm.computerPlay(false)
    }

    Scaffold(
        topBar = {
            BackHistoryTopAppBar(text = "$playerName vs AI", navController = navController) {
                vm.showGameHistory = !vm.showGameHistory
            }
        },
        floatingActionButton = {
            val computerFirstMove = vm.computerFirstMove

            ExtendedFloatingActionButton(
                onClick = { if (computerFirstMove) vm.computerPlay(true) else vm.reset() },
                icon = {
                    Icon(
                        if (computerFirstMove) Icons.Filled.Start else Icons.Filled.RestartAlt,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(stringResource(if (computerFirstMove) Res.string.start else Res.string.restart))
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            StatCounter(
                player1Name = player1Name,
                player2Name = player2Name,
                currentPlayer = vm.currentPlayer,
                player1Score = vm.player1Score,
                player2Score = vm.player2Score,
                draw = vm.drawCount,
                player1Onclick = {
                    if (vm.localPlayer != null) vm.showPlayer1Details = !vm.showPlayer1Details
                },
            )
            TicTacToeGrid(
                modifier = Modifier,
                board = vm.board,
                onclick = {
                    if (vm.board.contentEquals(Array<Char?>(9) { null }) && vm.computerFirstMove) {
                        vm.computerPlay(true)
                    } else {
                        vm.play(it)
                        if (!vm.isGameOver) vm.delayComputerMove = !vm.delayComputerMove
                    }
                },
                winningMoves = vm.winningMoves,
                clickable = !vm.computerMoveStatus,
                color =
                    when (vm.winningResult) {
                        GameResult.PLAYER1 -> MaterialTheme.colorScheme.primaryContainer
                        GameResult.PLAYER2 -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.background
                    },
            )
        }

        if (vm.showGameHistory) {
            GameHistoryBottomSheet(
                header = "All Games vs AI",
                onDismissRequest = { vm.showGameHistory = false },
                sheetState = sheetState,
                gameData = gameData,
            )
        }

        if (vm.showPlayer1Details) {
            vm.localPlayer?.let {
                LocalPlayerDetailsBottomSheet(
                    onDismissRequest = { vm.showPlayer1Details = false },
                    sheetState = sheetState,
                    localPlayer = it,
                    isVsComputer = true,
                )
            }
        }
        // TODO: SHOW AI TOTAL STATS
    }
}
