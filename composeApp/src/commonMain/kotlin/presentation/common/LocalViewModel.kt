package presentation.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.engine.LocalGameEngine.isBoardFull
import domain.engine.LocalGameEngine.isGameWon
import domain.model.GameData
import domain.model.GameResult
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import domain.repository.GameDataRepository
import java.util.Date
import kotlinx.coroutines.launch

abstract class LocalViewModel(
  protected open val gameDataRepository: GameDataRepository,
  protected open val player1Name: String,
  protected open val player2Name: String,
  protected val player1Type: Char,
  protected val player2Type: Char,
) : ViewModel() {
  var player1Score by mutableStateOf(0)
  var player2Score by mutableStateOf(0)
  var drawCount by mutableStateOf(0)

  var roundCount by mutableIntStateOf(0)
    protected set

  var winningResult: GameResult? by mutableStateOf(null)

  var isGameOver by mutableStateOf(false)
    protected set

  var currentPlayer by mutableStateOf(PLAYER_X)
    protected set

  var board by mutableStateOf(Array<Char?>(9) { null })
    protected set

  var winningMoves by mutableStateOf(emptyList<Int>())
    protected set

  open fun play(move: Int) {
    if (isGameOver) return

    if (board[move] == null) {
      board = board.copyOf()
      board[move] = currentPlayer
      currentPlayer = if (currentPlayer == PLAYER_X) PLAYER_O else PLAYER_X
    }

    winningResult = getGameResult(board)
    isGameOver = winningResult != null
    winningMoves = isGameWon(board, PLAYER_X).winningMoves

    if (isGameOver) {
      currentPlayer = if (currentPlayer == PLAYER_X) PLAYER_O else PLAYER_X
      saveGameData()
    } else {
      roundCount++
    }
  }

  private fun getGameResult(board: Array<Char?>): GameResult? {
    val playerXWon = isGameWon(board, PLAYER_X).isGameWon
    val playerOWon = isGameWon(board, PLAYER_O).isGameWon

    return when {
      playerXWon -> if (PLAYER_X == player1Type) GameResult.PLAYER1 else GameResult.PLAYER2
      playerOWon -> if (PLAYER_O == player2Type) GameResult.PLAYER2 else GameResult.PLAYER1
      isBoardFull(board) -> GameResult.DRAW
      else -> null
    }
  }

  open fun reset() {
    isGameOver = false
    board = Array(9) { null }
    winningMoves = emptyList()
    currentPlayer = PLAYER_X
    roundCount = 0
    winningResult = null
  }

  private fun saveGameData() {
    viewModelScope.launch {
      gameDataRepository.upsertGameData(
        GameData(
          board = board,
          player1Name = player1Name,
          player2Name = player2Name,
          player1Won = winningResult == GameResult.PLAYER1,
          player2Won = winningResult == GameResult.PLAYER2,
          draw = winningResult == GameResult.DRAW,
          date = Date(System.currentTimeMillis()),
        )
      )
    }
  }
}
