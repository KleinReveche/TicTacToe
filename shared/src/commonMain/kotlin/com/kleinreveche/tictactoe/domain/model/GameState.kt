package com.kleinreveche.tictactoe.domain.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class GameState(
    val gameId: String = Uuid.random().toString(),
    val playerAtTurn: Char? = PLAYER_X,
    val board: Array<Char?> = Array(9) { null },
    val winningPlayer: Char? = null,
    val isBoardFull: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GameState

        if (playerAtTurn != other.playerAtTurn) return false
        if (!board.contentEquals(other.board)) return false
        if (winningPlayer != other.winningPlayer) return false
        if (isBoardFull != other.isBoardFull) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerAtTurn?.hashCode() ?: 0
        result = 31 * result + board.contentHashCode()
        result = 31 * result + (winningPlayer?.hashCode() ?: 0)
        result = 31 * result + isBoardFull.hashCode()
        return result
    }
}