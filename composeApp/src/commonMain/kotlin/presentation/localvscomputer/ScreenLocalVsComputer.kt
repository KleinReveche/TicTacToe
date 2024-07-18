package presentation.localvscomputer

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import data.game.ComputerDifficulty
import domain.cases.GetPlayerByName
import domain.model.GameResult
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import presentation.common.components.BackHistoryTopAppBar
import presentation.common.components.StatCounter
import presentation.common.components.TicTacToeGrid
import presentation.localvscomputer.components.ScreenLocalVsComputerBottomSheet
import presentation.navigation.ScreenLocalVsComputer
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.restart
import tictactoe.composeapp.generated.resources.start

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocalVsComputer(screenData: ScreenLocalVsComputer, navController: NavController) {
  val sheetState = rememberModalBottomSheetState()
  var showBottomSheet by remember { mutableStateOf(false) }
  val playerName = screenData.playerName
  val playerType = screenData.playerType[0]
  val computerDifficulty = ComputerDifficulty.entries[screenData.difficulty[0].digitToInt()]
  val getPlayerByName = koinInject<GetPlayerByName>()
  val player = getPlayerByName(playerName)
  val player1Name = if (playerType == PLAYER_X) playerName else "AI: ${computerDifficulty.name}"
  val player2Name = if (playerType == PLAYER_O) playerName else "AI: ${computerDifficulty.name}"
  val vm =
    koinViewModel<ScreenLocalVsComputerViewModel> {
      parametersOf(player1Name, player2Name, playerType, computerDifficulty)
    }
  var delayComputerMove by remember { mutableStateOf(true) }
  val gameData =
    vm.gameData.collectAsState(initial = emptyList()).value.filter { gd ->
      gd.player1Name.contains("AI") || gd.player2Name.contains("AI")
    }

  LaunchedEffect(player) {
    if (vm.player == null) {
      vm.updatePlayer(player.first())
    }
  }

  LaunchedEffect(delayComputerMove) {
    if (vm.roundCount == 0) return@LaunchedEffect
    vm.computerMoveStatus = true
    val randomMaxDelay =
      when (computerDifficulty) {
        ComputerDifficulty.Easy -> 2000L
        ComputerDifficulty.Normal -> 3000L
        ComputerDifficulty.Insane -> 4500L
      }
    delay(Random(System.currentTimeMillis()).nextLong(1000, randomMaxDelay))
    vm.computerPlay(false)
  }

  Scaffold(
    topBar = {
      BackHistoryTopAppBar("$playerName vs AI", navController) {
        showBottomSheet = !showBottomSheet
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
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
        player1Name = player1Name,
        player2Name = player2Name,
        currentPlayer = vm.currentPlayer,
        player1Score = vm.player1Score,
        player2Score = vm.player2Score,
        draw = vm.drawCount,
      )
      TicTacToeGrid(
        modifier = Modifier,
        board = vm.board,
        onclick = {
          if (vm.board.contentEquals(Array<Char?>(9) { null }) && vm.computerFirstMove) {
            vm.computerPlay(true)
          } else {
            vm.play(it)
            if (!vm.isGameOver) delayComputerMove = !delayComputerMove
          }
        },
        winningMoves = vm.winningMoves,
        clickable = vm.computerMoveStatus,
        color =
          when (vm.winningResult) {
            GameResult.PLAYER1 -> MaterialTheme.colorScheme.primaryContainer
            GameResult.PLAYER2 -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.background
          },
      )
    }

    if (showBottomSheet) {
      ScreenLocalVsComputerBottomSheet(
        onDismissRequest = { showBottomSheet = false },
        sheetState = sheetState,
        gameData = gameData,
      )
    }
  }
}
