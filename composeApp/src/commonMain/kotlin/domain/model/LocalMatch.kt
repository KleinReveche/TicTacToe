package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalMatch(
  val player1: String,
  val player2: String,
  val player1Wins: Int,
  val player2Wins: Int,
  val draws: Int,
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
