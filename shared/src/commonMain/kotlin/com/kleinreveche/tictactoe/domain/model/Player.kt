package com.kleinreveche.tictactoe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val username: String,
    val playerType: Char?
)