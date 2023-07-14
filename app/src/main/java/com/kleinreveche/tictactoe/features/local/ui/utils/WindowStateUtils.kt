package com.kleinreveche.tictactoe.features.local.ui.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return (foldFeature?.state == FoldingFeature.State.HALF_OPENED) &&
            (foldFeature.orientation == FoldingFeature.Orientation.VERTICAL)
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return (foldFeature?.state == FoldingFeature.State.FLAT) && foldFeature.isSeparating
}

fun getFoldingDevicePosture(foldingFeature: FoldingFeature): DevicePosture {
    return when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }
}

enum class TicTacToeNavigationType {
    TOP_APP_BAR, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

enum class TicTacToeNavigationContentPosition {
    TOP, CENTER
}

enum class TicTacToeContentType {
    SINGLE_PANE, DUAL_PANE
}