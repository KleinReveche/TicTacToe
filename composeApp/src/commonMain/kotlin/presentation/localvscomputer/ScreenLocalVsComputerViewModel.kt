package presentation.localvscomputer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import data.game.ComputerDifficulty
import domain.engine.LocalGameEngine
import domain.model.GameResult
import domain.model.LocalPlayer
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import domain.repository.GameDataRepository
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.launch
import presentation.common.LocalViewModel

class ScreenLocalVsComputerViewModel(
  public override val player1Name: String,
  public override val player2Name: String,
  private val playerType: Char,
  private val difficulty: ComputerDifficulty,
  private val localPlayerRepository: LocalPlayerRepository,
  override val gameDataRepository: GameDataRepository,
) :
  LocalViewModel(
    gameDataRepository,
    player1Name = player1Name,
    player2Name = player2Name,
    player1Type = playerType,
    player2Type = if (playerType == PLAYER_X) PLAYER_O else PLAYER_X,
  ) {
  private val computerPlayerType = if (playerType == PLAYER_X) PLAYER_O else PLAYER_X
  var localPlayer: LocalPlayer? by mutableStateOf(null)
  var delayComputerMove by mutableStateOf(true)
  val computerFirstMove = computerPlayerType == PLAYER_X
  var computerMoveStatus by mutableStateOf(computerFirstMove)
  val gameData = gameDataRepository.getAllGameData()
  var showGameHistory by mutableStateOf(false)
  var showPlayer1Details by mutableStateOf(false)
  var showPlayer2Details by mutableStateOf(false)

  fun computerPlay(reset: Boolean) {
    if (reset) reset()
    currentPlayer = computerPlayerType

    val nextMove =
      when (difficulty) {
        ComputerDifficulty.Easy -> LocalGameEngine.computerMoveEasy(board)
        ComputerDifficulty.Normal -> LocalGameEngine.computerMoveNormal(board)
        ComputerDifficulty.Insane ->
          if (board.contentEquals(Array<Char?>(9) { null }) || roundCount == 1)
            LocalGameEngine.computerMoveEasy(board)
          else LocalGameEngine.computerMoveHard(board)
      }

    board = board.copyOf()
    board[nextMove] = computerPlayerType
    currentPlayer = if (computerPlayerType == PLAYER_X) PLAYER_O else PLAYER_X
    computerMoveStatus = false

    play(nextMove)
  }

  private fun updateWinCounters(result: GameResult?) {
    when (result) {
      GameResult.PLAYER1 -> {
        player1Score++
        updateScore(PLAYER_X == playerType)
      }
      GameResult.PLAYER2 -> {
        player2Score++
        updateScore(PLAYER_O == playerType)
      }
      GameResult.DRAW -> {
        drawCount++
        updateScore(true)
      }
      null -> return
    }
  }

  override fun play(move: Int) {
    super.play(move)
    updateWinCounters(winningResult)
  }

  override fun reset() {
    super.reset()
    computerMoveStatus = computerFirstMove
  }

  private fun updateScore(isPlayer1: Boolean) {
    localPlayer?.let { player ->
      when (difficulty) {
        ComputerDifficulty.Easy -> {
          if (isPlayer1) {
            player.playerVsComputerEasyWin = player1Score
            player.playerVsComputerEasyLoss = player2Score
            player.playerVsComputerEasyDraw = drawCount
          } else {
            player.playerVsComputerEasyLoss = player1Score
            player.playerVsComputerEasyWin = player2Score
            player.playerVsComputerEasyDraw = drawCount
          }
        }
        ComputerDifficulty.Normal -> {
          if (isPlayer1) {
            player.playerVsComputerNormalWin = player1Score
            player.playerVsComputerNormalLoss = player2Score
            player.playerVsComputerNormalDraw = drawCount
          } else {
            player.playerVsComputerNormalLoss = player1Score
            player.playerVsComputerNormalWin = player2Score
            player.playerVsComputerNormalDraw = drawCount
          }
        }
        ComputerDifficulty.Insane -> {
          if (isPlayer1) {
            player.playerVsComputerInsaneWin = player1Score
            player.playerVsComputerInsaneLoss = player2Score
            player.playerVsComputerInsaneDraw = drawCount
          } else {
            player.playerVsComputerInsaneLoss = player1Score
            player.playerVsComputerInsaneWin = player2Score
            player.playerVsComputerInsaneDraw = drawCount
          }
        }
      }

      viewModelScope.launch { localPlayerRepository.upsertPlayer(player) }
    }
  }

  fun updatePlayer(localPlayer: LocalPlayer) {
    player1Score = getScore(localPlayer, playerType == PLAYER_X)
    player2Score = getScore(localPlayer, playerType == PLAYER_O)
    drawCount = getDrawScore(localPlayer)
    this.localPlayer = localPlayer
  }

  private fun getScore(localPlayer: LocalPlayer, isPlayer1: Boolean): Int {
    return when (difficulty) {
      ComputerDifficulty.Easy -> {
        if (isPlayer1) {
          localPlayer.playerVsComputerEasyWin
        } else {
          localPlayer.playerVsComputerEasyLoss
        }
      }
      ComputerDifficulty.Normal -> {
        if (isPlayer1) {
          localPlayer.playerVsComputerNormalWin
        } else {
          localPlayer.playerVsComputerNormalLoss
        }
      }
      ComputerDifficulty.Insane -> {
        if (isPlayer1) {
          localPlayer.playerVsComputerInsaneWin
        } else {
          localPlayer.playerVsComputerInsaneLoss
        }
      }
    }
  }

  private fun getDrawScore(localPlayer: LocalPlayer): Int {
    return when (difficulty) {
      ComputerDifficulty.Easy -> localPlayer.playerVsComputerEasyDraw
      ComputerDifficulty.Normal -> localPlayer.playerVsComputerNormalDraw
      ComputerDifficulty.Insane -> localPlayer.playerVsComputerInsaneDraw
    }
  }

  fun getPlayerByName(name: String) = localPlayerRepository.getPlayerByName(name)
}
