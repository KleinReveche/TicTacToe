package com.kleinreveche.tictactoe.presentation.multiplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kleinreveche.tictactoe.domain.model.PLAYER_O
import com.kleinreveche.tictactoe.domain.model.PLAYER_X
import com.kleinreveche.tictactoe.presentation.common.components.BackHistoryTopAppBar
import com.kleinreveche.tictactoe.presentation.common.components.StatCounter
import com.kleinreveche.tictactoe.presentation.common.components.TicTacToeGrid
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScreenMultiplayer(navController: NavController) {
    val vm = koinViewModel<ScreenMultiplayerViewModel>()

    Scaffold(
        topBar = {
            BackHistoryTopAppBar(
                text = if (vm.isConnecting.value) "Connecting..." else "Multiplayer",
                navController = navController
            ) {
                //vm.showGameHistory = !vm.showGameHistory
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val serverState by vm.state.collectAsState()
            StatCounter(
                player1Name = "Player X",
                player2Name = "Player O",
                currentPlayer = serverState.gameState.playerAtTurn ?: PLAYER_X,
                player1Score = serverState.playerXWins,
                player2Score = serverState.playerOWins,
                draw = serverState.draws
            )

            val playerXWinColor =
                if (serverState.currentPlayerConnectedChar == PLAYER_X) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
            val playerOWinColor =
                if (serverState.currentPlayerConnectedChar == PLAYER_O) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.background

            TicTacToeGrid(
                modifier = Modifier,
                board = serverState.gameState.board,
                onclick = { vm.finishTurn(it) },
                winningMoves = serverState.gameState.winningMoves,
                clickable = !vm.isConnecting.value && serverState.gameState.winningPlayer == null && serverState.currentPlayerAtTurn == serverState.currentPlayerConnectedChar,
                color =
                    when (serverState.gameState.winningPlayer) {
                        PLAYER_X -> playerXWinColor
                        PLAYER_O -> playerOWinColor
                        else -> MaterialTheme.colorScheme.background
                    },
            )
        }
    }
}