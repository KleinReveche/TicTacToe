package presentation.common.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.ic_blank
import tictactoe.composeapp.generated.resources.ic_o
import tictactoe.composeapp.generated.resources.ic_x

@Composable
fun TicTacToeButton(
  text: Char?,
  onclick: () -> Unit,
  windowSize: WindowSizeClass,
  isWinningMove: Boolean = false,
  connecting: Boolean = false,
  color: Color = MaterialTheme.colorScheme.background,
) {
  Box(
    Modifier.size(
        when (windowSize.heightSizeClass) {
          WindowHeightSizeClass.Compact -> 125.dp
          WindowHeightSizeClass.Medium -> 150.dp
          else -> 175.dp
        }
      )
      .padding(8.dp)
      .clip(CircleShape)
      .aspectRatio(1f)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(),
        shape = CircleShape,
      )
      .background(
        color =
          animateColorAsState(
              targetValue = if (isWinningMove) color else MaterialTheme.colorScheme.background,
              label = "Button Color",
            )
            .value,
        shape = CircleShape,
      )
      .clickable(
        indication = if (text == null) LocalIndication.current else null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = text == null && !connecting,
      ) {
        if (text == null) onclick()
      },
    contentAlignment = Alignment.Center,
  ) {
    AnimatedContent(targetState = text, label = "") { targetCurrentPiece ->
      Image(
        modifier = Modifier.padding(8.dp).fillMaxSize(),
        painter =
          painterResource(
            when (targetCurrentPiece) {
              PLAYER_X -> Res.drawable.ic_x
              PLAYER_O -> Res.drawable.ic_o
              else -> Res.drawable.ic_blank
            }
          ),
        contentDescription = targetCurrentPiece.toString(),
        colorFilter =
          ColorFilter.tint(
            animateColorAsState(
                targetValue =
                  if (color == MaterialTheme.colorScheme.primary)
                    MaterialTheme.colorScheme.background
                  else MaterialTheme.colorScheme.primary,
                label = "Button Color",
              )
              .value
          ),
      )
    }
  }
}
