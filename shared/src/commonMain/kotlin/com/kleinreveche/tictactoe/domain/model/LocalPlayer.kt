package com.kleinreveche.tictactoe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalPlayer(
    val name: String,
    var playerVsComputerEasyWin: Int = 0,
    var playerVsComputerEasyLoss: Int = 0,
    var playerVsComputerEasyDraw: Int = 0,
    var playerVsComputerNormalWin: Int = 0,
    var playerVsComputerNormalLoss: Int = 0,
    var playerVsComputerNormalDraw: Int = 0,
    var playerVsComputerInsaneWin: Int = 0,
    var playerVsComputerInsaneLoss: Int = 0,
    var playerVsComputerInsaneDraw: Int = 0,
    var playerVsPlayerWin: Int = 0,
    var playerVsPlayerLoss: Int = 0,
    var playerVsPlayerDraw: Int = 0,
)
