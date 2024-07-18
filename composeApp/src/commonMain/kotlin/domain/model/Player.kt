package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
  @PrimaryKey val name: String,
  var playerVsComputerEasyWin: Int = 0,
  var playerVsComputerEasyLoss: Int = 0,
  var playerVsComputerEasyDraw: Int = 0,
  var playerVsComputerNormalWin: Int = 0,
  var playerVsComputerNormalLoss: Int = 0,
  var playerVsComputerNormalDraw: Int = 0,
  var playerVsComputerInsaneWin: Int = 0,
  var playerVsComputerInsaneLoss: Int = 0,
  var playerVsComputerInsaneDraw: Int = 0,
)
