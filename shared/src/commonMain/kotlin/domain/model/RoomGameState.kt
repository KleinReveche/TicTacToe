package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomGameState (
    val currentPlayerConnectedChar: Char? = PLAYER_X,
    val currentPlayerAtTurn: Char? = PLAYER_X,
    val state: GameState = GameState(),
    val playerXWins: Int = 0,
    val playerOWins: Int = 0,
    val draws: Int = 0,
    val gameVersion: Int = 1,
    val connectedPlayers: List<Char> = emptyList()
)