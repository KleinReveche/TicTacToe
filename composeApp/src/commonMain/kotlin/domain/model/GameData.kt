package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class GameData(
  val board: Array<Char?>,
  val player1Name: String,
  val player2Name: String,
  val player1Won: Boolean,
  val player2Won: Boolean,
  val draw: Boolean,
  val date: Date,
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as GameData

    if (!board.contentEquals(other.board)) return false
    if (player1Name != other.player1Name) return false
    if (player2Name != other.player2Name) return false
    if (player1Won != other.player1Won) return false
    if (player2Won != other.player2Won) return false
    if (draw != other.draw) return false
    if (date != other.date) return false
    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    var result = board.contentHashCode()
    result = 31 * result + player1Name.hashCode()
    result = 31 * result + player2Name.hashCode()
    result = 31 * result + player1Won.hashCode()
    result = 31 * result + player2Won.hashCode()
    result = 31 * result + draw.hashCode()
    result = 31 * result + date.hashCode()
    result = 31 * result + id
    return result
  }
}
