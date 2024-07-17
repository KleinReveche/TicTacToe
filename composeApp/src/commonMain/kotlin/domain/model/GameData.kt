package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder
import kotlin.collections.count
import kotlin.ranges.until
import kotlin.text.indices

@Entity
data class GameData(
    val board: String,
    val player1Name: String,
    val player2Name: String,
    val player1Won: Boolean,
    val player2Won: Boolean,
    val draw: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    companion object {
        fun boardToString(board: Array<Char?>): String {
            val sb = StringBuilder()
            for (i in 0 until board.count()) {
                sb.append(board[i] ?: " ")
            }
            return sb.toString()
        }
    }

    fun boardToArray(board: String): Array<Char?> {
        val boardArray = Array<Char?>(9) { null }
        for (i in board.indices) {
            val move = board[i]
            boardArray[i] = if (move == ' ') null else move
        }
        return boardArray
    }
}
