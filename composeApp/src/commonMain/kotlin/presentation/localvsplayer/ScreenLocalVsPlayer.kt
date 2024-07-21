package presentation.localvsplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import domain.model.GameResult
import domain.model.LocalPlayer
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import presentation.common.components.BackHistoryTopAppBar
import presentation.common.components.GameHistoryBottomSheet
import presentation.common.components.LocalPlayerDetailsBottomSheet
import presentation.common.components.StatCounter
import presentation.common.components.TicTacToeGrid
import presentation.navigation.ScreenLocalVsPlayer
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.restart

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocalVsPlayer(screenData: ScreenLocalVsPlayer, navController: NavController) {
  val player1 = screenData.player1
  val player2 = screenData.player2

  val vm = koinViewModel<ScreenLocalVsPlayerViewModel> { parametersOf(player1, player2) }
  val sheetState = rememberModalBottomSheetState()
  val gameData =
    vm
      .gameData()
      .collectAsState(emptyList())
      .value
      .filter { gd -> listOf(gd.player1Name, gd.player2Name).none { it.startsWith("AI") } }
      .sortedByDescending { it.date }

  Scaffold(
    topBar = {
      BackHistoryTopAppBar("$player1 vs $player2", navController) {
        vm.showGameHistory = !vm.showGameHistory
      }
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = { vm.reset() },
        icon = { Icon(Icons.Filled.RestartAlt, contentDescription = null) },
        text = { Text(stringResource(Res.string.restart)) },
      )
    },
  ) { paddingValues ->
    Column(
      modifier = Modifier.fillMaxSize().padding(paddingValues),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      StatCounter(
        player1Name = player1,
        player2Name = player2,
        currentPlayer = vm.currentPlayer,
        player1Score = vm.player1Score,
        player2Score = vm.player2Score,
        draw = vm.drawCount,
        player1Onclick = { vm.showPlayer1Details = !vm.showPlayer1Details },
        player2Onclick = { vm.showPlayer2Details = !vm.showPlayer2Details },
      )

      TicTacToeGrid(
        board = vm.board,
        onclick = { vm.play(it) },
        winningMoves = vm.winningMoves,
        clickable = !vm.isGameOver,
        color =
          when (vm.winningResult) {
            GameResult.PLAYER1 -> MaterialTheme.colorScheme.primaryContainer
            GameResult.PLAYER2 -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.background
          },
      )

      if (vm.showGameHistory) {
        GameHistoryBottomSheet(
          header = "All Pass 'n Play Games",
          onDismissRequest = { vm.showGameHistory = false },
          sheetState = sheetState,
          gameData = gameData,
        )
      }

      if (vm.showPlayer1Details) {
        LocalPlayerDetailsBottomSheet(
          onDismissRequest = { vm.showPlayer1Details = false },
          sheetState = sheetState,
          localPlayer = vm.player1.collectAsState(LocalPlayer(vm.player1Name)).value,
        )
      }

      if (vm.showPlayer2Details) {
        LocalPlayerDetailsBottomSheet(
          onDismissRequest = { vm.showPlayer2Details = false },
          sheetState = sheetState,
          localPlayer = vm.player2.collectAsState(LocalPlayer(vm.player2Name)).value,
        )
      }
    }
  }
}
