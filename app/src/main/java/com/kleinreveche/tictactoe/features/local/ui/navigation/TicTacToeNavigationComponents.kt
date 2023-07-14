package com.kleinreveche.tictactoe.features.local.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kleinreveche.tictactoe.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocalTopAppBar(
    onClick: () -> Unit
) {
    var rotationAngle by remember { mutableStateOf(0f) }
    val animatedRotationAngle: Float by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "TicTacToe",
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        actions = {
            IconButton(
                onClick = {
                    rotationAngle += 45f
                    onClick()
                },
                modifier = Modifier
                    .rotate(animatedRotationAngle)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.settings),
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
fun TicTacToeLocalNavigationRail(
    modifier: Modifier = Modifier,
    onClickHeader: () -> Unit
) {

    var rotationAngle by remember { mutableStateOf(0f) }
    val animatedRotationAngle: Float by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    var selectedItem by remember { mutableStateOf(0) }
    val items = stringArrayResource(id = R.array.navRailItems).asList()
    val icons = listOf(Icons.Filled.Settings)

    NavigationRail(
        header = {
            IconButton(
                onClick = {
                    onClickHeader()
                },
                modifier = modifier.size(96.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(id = R.string.app_name),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier.size(96.dp)
                )
            }
        }
    ) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItem == index,
                onClick = {
                    rotationAngle += 45f
                    selectedItem = index
                    onClickHeader()
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = if (index == 0) {
                            Modifier.rotate(animatedRotationAngle)
                        } else Modifier
                    )
                },
                label = { Text(item) }
            )
        }
    }
}