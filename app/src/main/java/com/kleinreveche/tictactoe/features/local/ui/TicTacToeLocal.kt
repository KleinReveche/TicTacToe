package com.kleinreveche.tictactoe.features.local.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.kleinreveche.tictactoe.R
import com.kleinreveche.tictactoe.features.local.engine.GameViewModel
import com.kleinreveche.tictactoe.features.local.ui.components.TicTacToeLocalLayout
import com.kleinreveche.tictactoe.features.local.ui.navigation.TicTacToeLocalTopAppBar
import com.kleinreveche.tictactoe.features.local.ui.utils.DevicePosture
import com.kleinreveche.tictactoe.features.local.ui.utils.TicTacToeContentType
import com.kleinreveche.tictactoe.features.local.ui.utils.TicTacToeNavigationType
import com.kleinreveche.tictactoe.features.local.ui.utils.getFoldingDevicePosture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeLocal(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {

    val navigationType: TicTacToeNavigationType
    val contentType: TicTacToeContentType
    val ticTacToeLocalViewModel = viewModel<GameViewModel>()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = foldingFeature?.let { getFoldingDevicePosture(it) }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (windowSize.heightSizeClass == WindowHeightSizeClass.Compact) {
                navigationType = TicTacToeNavigationType.TOP_APP_BAR
                contentType = TicTacToeContentType.DUAL_PANE
            } else {
                navigationType = TicTacToeNavigationType.TOP_APP_BAR
                contentType = TicTacToeContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = TicTacToeNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                if (windowSize.heightSizeClass == WindowHeightSizeClass.Medium)
                    TicTacToeContentType.SINGLE_PANE
                else TicTacToeContentType.DUAL_PANE
            } else {
                TicTacToeContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                TicTacToeNavigationType.NAVIGATION_RAIL
            } else {
                TicTacToeNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = TicTacToeContentType.DUAL_PANE
        }

        else -> {
            navigationType = TicTacToeNavigationType.TOP_APP_BAR
            contentType = TicTacToeContentType.SINGLE_PANE
        }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = navigationType == TicTacToeNavigationType.TOP_APP_BAR
                        || windowSize.widthSizeClass == WindowWidthSizeClass.Medium
                        && windowSize.heightSizeClass == WindowHeightSizeClass.Medium
            ) {
                TicTacToeLocalTopAppBar {
                    showBottomSheet = !showBottomSheet
                }
            }
        },
        floatingActionButton = {
            val computerFirstMove =
                ticTacToeLocalViewModel.computerFirstMove && ticTacToeLocalViewModel.isSinglePlayer
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(id = if (computerFirstMove) R.string.start_game else R.string.restart)) },
                icon = {
                    Icon(
                        if (computerFirstMove) Icons.Filled.Start else Icons.Filled.RestartAlt,
                        stringResource(id = if (computerFirstMove) R.string.start_game else R.string.restart)
                    )
                },
                onClick = { if (computerFirstMove) ticTacToeLocalViewModel.computerPlay() else ticTacToeLocalViewModel.reset() },
            )
        }
    ) {
        it.calculateBottomPadding()
        TicTacToeLocalLayout(
            modifier = Modifier,
            ticTacToeLocalViewModel = ticTacToeLocalViewModel,
            windowSize = windowSize,
            contentType = contentType,
            sheetState = sheetState,
            showBottomSheet = showBottomSheet
        ) {
            showBottomSheet = !showBottomSheet
        }
    }
}