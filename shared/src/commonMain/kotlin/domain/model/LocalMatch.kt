package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalMatch(
    val player1: String,
    val player2: String,
    val player1Wins: Int,
    val player2Wins: Int,
    val draws: Int,
    val id: Int = 0,
)
