package presentation.common.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
  player1Onclick: (() -> Unit)? = null,
  player2Onclick: (() -> Unit)? = null,
) {
  val player1Computer = player1Name.contains("AI")
  val player2Computer = player2Name.contains("AI")
  val textVerticalPadding = 12.dp
  val textHorizontalPadding = 5.dp

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      val commonBoxModifier =
        Modifier.weight(0.3f).width(IntrinsicSize.Max).fillMaxHeight().padding(10.dp, 0.dp)

      Box(
        commonBoxModifier
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
          .clickable(
            indication = if (player1Onclick == null) LocalIndication.current else null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = player1Onclick != null,
          ) {
            if (player1Onclick != null) player1Onclick()
          },
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = player1Name,
          textAlign = TextAlign.Center,
          fontSize = 16.sp,
          color =
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.onPrimary
                  else MaterialTheme.colorScheme.primary,
                label = "Current Player Color 1",
              )
              .value,
          modifier =
            Modifier.padding(textHorizontalPadding, textVerticalPadding).width(IntrinsicSize.Max),
        )
      }

      Box(modifier = commonBoxModifier, contentAlignment = Alignment.Center) {
        Text(
          text = "Draw",
          textAlign = TextAlign.Center,
          fontSize = 16.sp,
          modifier =
            modifier.padding(textHorizontalPadding, textVerticalPadding).width(IntrinsicSize.Max),
        )
      }

      Box(
        commonBoxModifier
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
          .clickable(
            indication = if (player2Onclick == null) LocalIndication.current else null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = player2Onclick != null,
          ) {
            if (player2Onclick != null) player2Onclick()
          },
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = player2Name,
          textAlign = TextAlign.Center,
          fontSize = 16.sp,
          color =
            animateColorAsState(
                targetValue =
                  if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.secondary
                  else MaterialTheme.colorScheme.secondary,
                label = "Current Player Color 2",
              )
              .value,
          modifier =
            Modifier.padding(textHorizontalPadding, textVerticalPadding).width(IntrinsicSize.Max),
        )
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = "$player1Score",
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        modifier = Modifier.weight(0.3f).width(IntrinsicSize.Max),
      )
      Text(
        text = "$draw",
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        modifier = Modifier.weight(0.3f).width(IntrinsicSize.Max),
      )
      Text(
        text = "$player2Score",
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        modifier = Modifier.weight(0.3f).width(IntrinsicSize.Max),
      )
    }
  }
}
