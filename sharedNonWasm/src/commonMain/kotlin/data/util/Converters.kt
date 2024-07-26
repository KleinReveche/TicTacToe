package data.util

import androidx.room.TypeConverter
import domain.model.AppSetting
import domain.model.AppSettingRoom
import domain.model.GameData
import domain.model.GameDataRoom
import domain.model.LocalMatch
import domain.model.LocalMatchRoom
import domain.model.LocalPlayer
import domain.model.LocalPlayerRoom
import kotlinx.datetime.Instant

object Converters {

  @TypeConverter fun fromTimestamp(value: Long) = Instant.fromEpochMilliseconds(value)

  @TypeConverter fun dateToTimestamp(date: Instant) = date.toEpochMilliseconds()

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

  fun AppSetting.toAppSettingRoom() = AppSettingRoom(setting, value)

  fun AppSettingRoom.toAppSetting() = AppSetting(setting, value)

  fun GameData.toGameDataRoom() = GameDataRoom(board, player1Name, player2Name, player1Won, player2Won, draw, date)

  fun GameDataRoom.toGameData() = GameData(board, player1Name, player2Name, player1Won, player2Won, draw, date)

  fun LocalMatch.toLocalMatchRoom() = LocalMatchRoom(player1, player2, player1Wins, player2Wins, draws, id)

  fun LocalMatchRoom.toLocalMatch() = LocalMatch(player1, player2, player1Wins, player2Wins, draws, id)

  fun LocalPlayer.toLocalPlayerRoom() =
    LocalPlayerRoom(
      name,
      playerVsComputerEasyWin,
      playerVsComputerEasyLoss,
      playerVsComputerEasyDraw,
      playerVsComputerNormalWin,
      playerVsComputerNormalLoss,
      playerVsComputerNormalDraw,
      playerVsComputerInsaneWin,
      playerVsComputerInsaneLoss,
      playerVsComputerInsaneDraw,
      playerVsPlayerWin,
    )

  fun LocalPlayerRoom.toLocalPlayer() =
    LocalPlayer(
      name,
      playerVsComputerEasyWin,
      playerVsComputerEasyLoss,
      playerVsComputerEasyDraw,
      playerVsComputerNormalWin,
      playerVsComputerNormalLoss,
      playerVsComputerNormalDraw,
      playerVsComputerInsaneWin,
      playerVsComputerInsaneLoss,
      playerVsComputerInsaneDraw,
      playerVsPlayerWin,
    )
}
