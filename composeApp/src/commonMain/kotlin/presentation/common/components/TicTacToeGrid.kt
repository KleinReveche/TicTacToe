package presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TicTacToeGrid(
  modifier: Modifier = Modifier,
  board: Array<Char?>,
  onclick: (Int) -> Unit,
  windowSize: WindowSizeClass = calculateWindowSizeClass(),
  clickable: Boolean = true,
  winningMoves: List<Int>,
  color: Color = MaterialTheme.colorScheme.background,
) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    Column(Modifier.padding(vertical = 1.dp)) {
      val rows = 3
      val columns = 3
      val buttonSize =
        when (windowSize.heightSizeClass) {
          WindowHeightSizeClass.Compact -> 125.dp
          WindowHeightSizeClass.Medium -> 150.dp
          else -> 175.dp
        }

      val gridModifier =
        if (windowSize.heightSizeClass == WindowHeightSizeClass.Compact) {
          Modifier.width(buttonSize * columns).height(buttonSize * rows)
        } else {
          Modifier.width(buttonSize * columns).height(buttonSize * rows)
        }

      LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = gridModifier) {
        items(board.size) { index ->
          TicTacToeButton(
            text = board[index],
            onclick = { onclick(index) },
            windowSize = windowSize,
            isWinningMove = winningMoves.contains(index),
            clickable = clickable,
            color = color,
          )
        }
      }
    }
  }
}
