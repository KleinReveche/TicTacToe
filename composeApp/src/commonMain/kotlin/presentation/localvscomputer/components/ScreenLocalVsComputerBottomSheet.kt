package presentation.localvscomputer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.engine.LocalGameEngine
import domain.model.GameData
import domain.model.PLAYER_X
import presentation.common.components.TicTacToeGrid
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLocalVsComputerBottomSheet(
  modifier: Modifier = Modifier,
  onDismissRequest: () -> Unit,
  sheetState: SheetState,
  gameData: List<GameData>,
) {
  var selectedIndex by remember { mutableIntStateOf(-1) }
  var showGameDetails by remember { mutableStateOf(false) }
  val formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm")

  ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
    AnimatedVisibility(!showGameDetails) {
      Column {
        Text(
          "All Games vs AI",
          fontSize = 24.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp, 10.dp, 0.dp),
        )
        LazyColumn(modifier = Modifier.padding(10.dp)) {
          itemsIndexed(gameData) { index, item ->
            Box(
              modifier.fillMaxWidth().clickable {
                selectedIndex = index
                showGameDetails = !showGameDetails
              }
            ) {
              val dateFormatted =
                LocalDateTime.ofInstant(item.date.toInstant(), ZoneId.systemDefault())
                  .format(formatter)
              Text(
                text = "$dateFormatted - ${item.player1Name} vs ${item.player2Name}",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 8.dp),
              )
            }
          }
        }
      }
    }
    AnimatedVisibility(showGameDetails) {
      val selectedItem = gameData[selectedIndex]
      val winningMoves = LocalGameEngine.isGameWon(selectedItem.board, PLAYER_X).winningMoves
      val formattedDate =
        LocalDateTime.ofInstant(selectedItem.date.toInstant(), ZoneId.systemDefault())
          .format(formatter)
      val winner =
        when {
          selectedItem.player1Won -> selectedItem.player1Name + " won!"
          selectedItem.player2Won -> selectedItem.player2Name + " won!"
          else -> "Draw"
        }

      IconButton(
        onClick = { showGameDetails = !showGameDetails },
        modifier = Modifier.align(Alignment.Start),
      ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }

      Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
      ) {
        Box(
          Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(5.dp),
          )
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text("Game Details:", textAlign = TextAlign.Center, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(formattedDate, fontSize = 15.sp)
            Text("Player 1 - ${selectedItem.player1Name}", fontSize = 16.sp)
            Text("Player 2 - ${selectedItem.player2Name}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text("Result: $winner", fontSize = 16.sp)
          }
        }
        TicTacToeGrid(
          modifier = Modifier.fillMaxWidth(),
          board = selectedItem.board,
          onclick = {},
          winningMoves = winningMoves,
          clickable = false,
          color =
            when {
              winner.contains("AI") -> MaterialTheme.colorScheme.errorContainer
              !winner.contains("AI") -> MaterialTheme.colorScheme.primaryContainer
              else -> MaterialTheme.colorScheme.background
            },
        )
      }
    }
  }
}
