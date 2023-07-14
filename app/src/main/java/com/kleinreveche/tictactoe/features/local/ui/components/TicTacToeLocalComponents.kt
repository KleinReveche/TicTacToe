package com.kleinreveche.tictactoe.features.local.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kleinreveche.tictactoe.R
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_O
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_X
import com.kleinreveche.tictactoe.features.local.ui.components.material3.Material3SegmentedButton
import com.kleinreveche.tictactoe.features.local.ui.components.material3.SegmentedButtonDefaults
import com.kleinreveche.tictactoe.features.local.ui.components.material3.SegmentedButtonRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalSinglePlayerModeSelector(
    singlePlayer: Boolean, onClickedChange: (Boolean) -> Unit
) {
    val singlePlayerMode = remember { mutableStateOf(singlePlayer) }
    val items = stringArrayResource(id = R.array.singlePlayerMode).asList()
    Column(
        modifier = Modifier
            .padding(12.dp, 12.dp, 12.dp, 2.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.s_playerCount),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.s_playerCount_desc),
            style = MaterialTheme.typography.bodySmall
        )
    }
    SegmentedButtonRow {
        items.forEachIndexed { index, label ->
            Material3SegmentedButton(
                shape = SegmentedButtonDefaults.shape(position = index, count = items.size),
                onCheckedChange = {
                    onClickedChange(index == 0)
                    singlePlayerMode.value = index == 0
                },
                checked = index == if (singlePlayer) 0 else 1
            ) {
                Row {
                    Spacer(Modifier.size(width = 30.dp, height = 0.dp))
                    Text(label)
                    Spacer(Modifier.size(width = 30.dp, height = 0.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalComputerDifficultySelector(
    difficulty: Int, onClickedChange: (Int) -> Unit
) {
    val computerDifficulty = remember { mutableStateOf(difficulty) }
    val items = stringArrayResource(id = R.array.computerDifficulty).asList()
    Column(
        modifier = Modifier
            .padding(12.dp, 12.dp, 12.dp, 2.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.s_computerDifficulty),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.s_computerDifficulty_desc),
            style = MaterialTheme.typography.bodySmall
        )
    }
    SegmentedButtonRow {
        items.forEachIndexed { index, label ->
            Material3SegmentedButton(
                shape = SegmentedButtonDefaults.shape(position = index, count = items.size),
                onCheckedChange = {
                    onClickedChange(index)
                    computerDifficulty.value = index
                },
                checked = index == computerDifficulty.value
            ) {
                Row {
                    Spacer(Modifier.size(width = 23.dp, height = 0.dp))
                    Text(label)
                    Spacer(Modifier.size(width = 20.dp, height = 0.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalFirstMoveSelector(
    computerFirst: Boolean, onClickedChange: (Boolean) -> Unit, isSinglePlayer: Boolean
) {
    val isComputerFirst = remember { mutableStateOf(computerFirst) }
    val items = if (isSinglePlayer) stringArrayResource(id = R.array.firstMoveSinglePlayer).asList()
    else stringArrayResource(id = R.array.firstMoveTwoPlayer).asList()

    Column(
        modifier = Modifier
            .padding(12.dp, 12.dp, 12.dp, 2.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.s_firstMove),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.s_firstMove_desc),
            style = MaterialTheme.typography.bodySmall
        )
    }
    SegmentedButtonRow {
        items.forEachIndexed { index, label ->
            Material3SegmentedButton(
                shape = SegmentedButtonDefaults.shape(position = index, count = items.size),
                onCheckedChange = {
                    onClickedChange(index == 1)
                    isComputerFirst.value = index == 1
                },
                checked = index == if (computerFirst) 1 else 0
            ) {
                Row {
                    Spacer(Modifier.size(width = 30.dp, height = 0.dp))
                    Text(label)
                    Spacer(Modifier.size(width = 30.dp, height = 0.dp))
                }
            }
        }
    }
}

@Composable
fun SettingItemSwitch(
    title: String,
    description: String?,
    value: Boolean,
    onCheckChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Switch(
            checked = value,
            onCheckedChange = {
                onCheckChange(it)
            },
            modifier = Modifier.weight(0.2f)
        )
    }
}

@Composable
fun GameButton(buttonName: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .height(50.dp),
    ) {
        Text(
            text = buttonName,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StatsCounter(
    modifier: Modifier = Modifier,
    playerXWinCount: Int,
    playerOWinCount: Int,
    drawCount: Int,
    currentPlayer: String,
    singlePlayer: Boolean,
    showDraw: Boolean,
    difficulty: Int,
    isLandscape: Boolean = false
) {
    val isSinglePlayer = remember { mutableStateOf(singlePlayer) }
    val landscape = remember { mutableStateOf(isLandscape) }

    AnimatedContent(targetState = drawCount, label = "") {
        when (landscape.value) {
            true ->
                Column(modifier.padding(start = 16.dp, end = 16.dp)) {
                    Column(
                        modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isSinglePlayer.value)
                                stringResource(id = R.string.you)
                            else
                                stringResource(id = R.string.player_x),
                            fontSize = 16.sp,
                            color = animateColorAsState(
                                targetValue =
                                if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.onPrimary
                                else Color.Unspecified,
                                label = "Current Player Color 1"
                            ).value,
                            modifier = modifier
                                .background(
                                    animateColorAsState(
                                        targetValue =
                                        if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.primary
                                        else Color.Unspecified,
                                        label = "Current Player Color 1"
                                    ).value,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                        )
                        Text(
                            text = "$playerXWinCount",
                            fontSize = 32.sp
                        )
                    }
                    if (isLandscape) {
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                    AnimatedVisibility(
                        visible = showDraw,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Draw",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "$drawCount",
                                fontSize = 32.sp
                            )
                        }
                    }

                    if (isLandscape && showDraw) {
                        Spacer(modifier = Modifier.size(20.dp))
                    }

                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isSinglePlayer.value) {
                                when (difficulty) {
                                    0 -> stringResource(id = R.string.ai_easy)
                                    1 -> stringResource(id = R.string.ai_normal)
                                    else -> stringResource(id = R.string.ai_insane)
                                }
                            } else stringResource(id = R.string.player_o),
                            fontSize = 16.sp,
                            color = animateColorAsState(
                                targetValue =
                                if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.onPrimary
                                else Color.Unspecified,
                                label = "Current Player Color 2"
                            ).value,
                            textAlign = TextAlign.Center,
                            modifier = modifier
                                .background(
                                    animateColorAsState(
                                        targetValue =
                                        if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.primary
                                        else Color.Unspecified,
                                        label = "Current Player Color 2"
                                    ).value,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                        )
                        Text(
                            text = "$playerOWinCount",
                            fontSize = 32.sp
                        )
                    }
                }

            false -> {
                Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Column(
                        modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isSinglePlayer.value)
                                stringResource(id = R.string.you)
                            else
                                stringResource(id = R.string.player_x),
                            fontSize = 16.sp,
                            color = animateColorAsState(
                                targetValue =
                                if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.onPrimary
                                else Color.Unspecified,
                                label = "Current Player Color 1"
                            ).value,
                            modifier = modifier
                                .background(
                                    animateColorAsState(
                                        targetValue =
                                        if (currentPlayer == PLAYER_X) MaterialTheme.colorScheme.primary
                                        else Color.Unspecified,
                                        label = "Current Player Color 1"
                                    ).value,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                        )
                        Text(
                            text = "$playerXWinCount",
                            fontSize = 32.sp
                        )
                    }
                    AnimatedVisibility(
                        visible = showDraw,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        Column(
                            Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Draw",
                                fontSize = 16.sp,
                                modifier = modifier.padding(vertical = 1.dp)
                            )
                            Text(
                                text = "$drawCount",
                                fontSize = 32.sp
                            )
                        }
                    }
                    Column(
                        Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isSinglePlayer.value)
                                when (difficulty) {
                                    0 -> stringResource(id = R.string.ai_easy)
                                    1 -> stringResource(id = R.string.ai_normal)
                                    else -> stringResource(id = R.string.ai_insane)
                                } else stringResource(id = R.string.player_o),
                            fontSize = 16.sp,
                            color = animateColorAsState(
                                targetValue =
                                if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.onPrimary
                                else Color.Unspecified,
                                label = "Current Player Color 2"
                            ).value,
                            modifier = modifier
                                .background(
                                    animateColorAsState(
                                        targetValue =
                                        if (currentPlayer == PLAYER_O) MaterialTheme.colorScheme.primary
                                        else Color.Unspecified,
                                        label = "Current Player Color 2"
                                    ).value,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                        )
                        Text(
                            text = "$playerOWinCount",
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StatCounterPreview() {
    StatsCounter(
        playerXWinCount = 5,
        playerOWinCount = 2,
        drawCount = 3,
        currentPlayer = PLAYER_X,
        singlePlayer = true,
        showDraw = true,
        difficulty = 2,
        isLandscape = true
    )
}