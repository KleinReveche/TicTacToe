package com.kleinreveche.tictactoe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerGameState (
    val currentPlayerConnectedChar: Char? = PLAYER_X,
    val currentPlayerAtTurn: Char? = PLAYER_X,
    val gameState: GameState = GameState(),
    val playerXWins: Int = 0,
    val playerOWins: Int = 0,
    val draws: Int = 0,
    val gameVersion: Int = 1,
    val connectedPlayers: List<Char> = emptyList()
)