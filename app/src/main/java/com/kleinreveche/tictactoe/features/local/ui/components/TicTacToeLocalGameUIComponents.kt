package com.kleinreveche.tictactoe.features.local.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kleinreveche.tictactoe.R
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_O
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_X

@Composable
fun TicTacToeButton(
    text: String,
    onclick: () -> Unit,
    windowSize: WindowSizeClass,
    isWinningMove: Boolean = false
) {
    Box(
        Modifier
            .size(
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
                shape = CircleShape
            )
            .background(
                color = animateColorAsState(
                    targetValue =
                    if (isWinningMove) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background,
                    label = "Button Color"
                ).value,
                shape = CircleShape
            )
            .clickable(
                indication = if (text.isBlank()) LocalIndication.current else null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = text.isBlank()
            ) {
                if (text.isBlank())
                    onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = text, label = ""
        ) { targetCurrentPiece ->
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                painter = painterResource(
                    when (targetCurrentPiece) {
                        PLAYER_X -> R.drawable.ic_x
                        PLAYER_O -> R.drawable.ic_o
                        else -> R.drawable.ic_blank
                    }
                ),
                contentDescription = targetCurrentPiece,
                colorFilter = ColorFilter.tint(
                    animateColorAsState(
                        targetValue =
                        if (isWinningMove) MaterialTheme.colorScheme.background
                        else MaterialTheme.colorScheme.primary,
                        label = "Button Color"
                    ).value
                )
            )
        }
    }
}

@Composable
fun ButtonGrid(
    board: Array<String>,
    onclick: (Int) -> Unit,
    windowSize: WindowSizeClass,
    winningMoves: List<Int> = emptyList(),
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.padding(vertical = 1.dp)) {
            val rows = 3
            val columns = 3
            val buttonSize = when (windowSize.heightSizeClass) {
                WindowHeightSizeClass.Compact -> 125.dp
                WindowHeightSizeClass.Medium -> 150.dp
                else -> 175.dp
            }

            val gridModifier = if (windowSize.heightSizeClass == WindowHeightSizeClass.Compact) {
                Modifier
                    .width(buttonSize * columns)
                    .height(buttonSize * rows)
            } else {
                Modifier
                    .width(buttonSize * columns)
                    .height(buttonSize * rows)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = gridModifier
            ) {
                items(board.size) { index ->
                    TicTacToeButton(
                        text = board[index],
                        onclick = { onclick(index) },
                        windowSize = windowSize,
                        isWinningMove = winningMoves.contains(index)
                    )
                }
            }
        }
    }
}
