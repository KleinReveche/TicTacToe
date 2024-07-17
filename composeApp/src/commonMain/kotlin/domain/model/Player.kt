package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerVsComputerEasyWin: Int = 0,
    val playerVsComputerEasyLoss: Int = 0,
    val playerVsComputerEasyDraw: Int = 0,
    val playerVsComputerNormalWin: Int = 0,
    val playerVsComputerNormalLoss: Int = 0,
    val playerVsComputerNormalDraw: Int = 0,
    val playerVsComputerInsaneWin: Int = 0,
    val playerVsComputerInsaneLoss: Int = 0,
    val playerVsComputerInsaneDraw: Int = 0,
)
