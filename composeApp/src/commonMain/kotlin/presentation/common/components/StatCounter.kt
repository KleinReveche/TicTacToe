package presentation.common.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun StatCounter(
  modifier: Modifier = Modifier,
  player1Name: String = "Player",
  player2Name: String = "Computer: Insane",
  currentPlayer: Char = PLAYER_X,
  player1Score: Int = 0,
  player2Score: Int = 0,
  draw: Int = 0,
) {
  val player1Computer = player1Name.contains("AI")
  val player2Computer = player2Name.contains("AI")

  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(Modifier.weight(0.3f), horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier
          .border(
            width = 1.dp,
            color = if (!player1Computer) MaterialTheme.colorScheme.tertiary else Color.Unspecified,
            shape = RoundedCornerShape(4.dp),
          )
          .background(
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.primary
                  else Color.Unspecified,
                label = "Current Player Color 1",
              )
              .value,
            shape = RoundedCornerShape(4.dp),
          )
      ) {
        Text(
          text = player1Name,
          fontSize = 16.sp,
          color =
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.onPrimary
                  else Color.Unspecified,
                label = "Current Player Color 1",
              )
              .value,
          modifier = Modifier.padding(16.dp, 8.dp),
        )
      }
      Text(text = "$player1Score", fontSize = 32.sp)
    }

    Column(Modifier.weight(0.3f), horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = "Draw", fontSize = 16.sp, modifier = modifier.padding(16.dp, 8.dp))
      Text(text = "$draw", fontSize = 32.sp)
    }

    Column(Modifier.weight(0.3f), horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier
          .border(
            width = 1.dp,
            color = if (!player2Computer) MaterialTheme.colorScheme.tertiary else Color.Unspecified,
            shape = RoundedCornerShape(4.dp),
          )
          .background(
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.onSecondary
                  else Color.Unspecified,
                label = "Current Player Color 2",
              )
              .value,
            shape = RoundedCornerShape(4.dp),
          )
      ) {
        Text(
          text = player2Name,
          fontSize = 16.sp,
          color =
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.onSecondaryContainer
                  else Color.Unspecified,
                label = "Current Player Color 2",
              )
              .value,
          modifier = Modifier.padding(16.dp, 8.dp),
        )
      }
      Text(text = "$player2Score", fontSize = 32.sp)
    }
  }
}
