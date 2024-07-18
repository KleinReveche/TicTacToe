package presentation.navigation

import kotlinx.serialization.Serializable

@Serializable object ScreenMain

@Serializable
data class ScreenLocalVsComputer(
  val playerName: String,
  val playerType: String,
  val difficulty: String,
)

@Serializable data class ScreenLocalVsPlayer(val player1: String, val player2: String)

@Serializable object ScreenMultiplayer
