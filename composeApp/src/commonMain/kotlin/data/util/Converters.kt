package data.util

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.Date

object Converters {

  @TypeConverter fun fromTimestamp(value: Long): Date = Date(value)

  @TypeConverter fun dateToTimestamp(date: Date): Long = date.time

  @TypeConverter
  fun boardToString(board: Array<Char?>): String {
    val sb = StringBuilder()
    for (i in 0 until board.count()) {
      sb.append(board[i] ?: "_")
    }
    return sb.toString()
  }

  @TypeConverter
  fun boardToArray(board: String): Array<Char?> {
    val boardArray = Array<Char?>(9) { null }
    for (i in board.indices) {
      val move = board[i]
      boardArray[i] = if (move == '_') null else move
    }
    return boardArray
  }
}
