package presentation.localvsplayer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import domain.model.GameData
import domain.model.GameResult
import domain.model.LocalMatch
import domain.model.PLAYER_O
import domain.model.PLAYER_X
import domain.repository.GameDataRepository
import domain.repository.LocalMatchRepository
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import presentation.common.LocalViewModel

class ScreenLocalVsPlayerViewModel(
  public override val player1Name: String,
  public override val player2Name: String,
  override val gameDataRepository: GameDataRepository,
  private val localMatchRepository: LocalMatchRepository,
  private val localPlayerRepository: LocalPlayerRepository,
) :
  LocalViewModel(
    player1Name = player1Name,
    player2Name = player2Name,
    player1Type = PLAYER_X,
    player2Type = PLAYER_O,
    gameDataRepository = gameDataRepository,
  ) {
  private var match: LocalMatch? = null
  private var reverseScore = false
  var showGameHistory by mutableStateOf(false)
  var showPlayer1Details by mutableStateOf(false)
  var showPlayer2Details by mutableStateOf(false)
  val player1 = localPlayerRepository.getPlayerByName(player1Name)
  val player2 = localPlayerRepository.getPlayerByName(player2Name)

  init {
    viewModelScope.launch {
      match = localMatchRepository.getMatchBetweenPlayers(player1Name, player2Name).first()
      match?.let {
        if (player1Name == it.player2 && player2Name == it.player1) reverseScore = true
        player1Score = if (reverseScore) it.player2Wins else it.player1Wins
        player2Score = if (reverseScore) it.player1Wins else it.player2Wins
        drawCount = it.draws
      }
    }
  }

  fun gameData(): Flow<List<GameData>> = gameDataRepository.getAllGameData()

  override fun play(move: Int) {
    super.play(move)
    updateWinCounters(winningResult)
    updateLocalMatch()
    updateLocalPlayerStats(winningResult)
  }

  private fun updateWinCounters(result: GameResult?) {
    when (result) {
      GameResult.PLAYER1 -> player1Score++
      GameResult.PLAYER2 -> player2Score++
      GameResult.DRAW -> drawCount++
      null -> return
    }
  }

  private fun updateLocalPlayerStats(result: GameResult?) {
    viewModelScope.launch {
      val localPlayer1 = localPlayerRepository.getPlayerByName(player1Name).first()
      val localPlayer2 = localPlayerRepository.getPlayerByName(player2Name).first()

      when (result) {
        GameResult.PLAYER1 -> {
          localPlayer1.playerVsPlayerWin++
          localPlayer2.playerVsPlayerLoss++
        }
        GameResult.PLAYER2 -> {
          localPlayer1.playerVsPlayerLoss++
          localPlayer2.playerVsPlayerWin++
        }
        GameResult.DRAW -> {
          localPlayer1.playerVsPlayerDraw++
          localPlayer2.playerVsPlayerDraw++
        }
        null -> return@launch
      }
      localPlayerRepository.upsertPlayer(localPlayer1)
      localPlayerRepository.upsertPlayer(localPlayer2)
    }
  }

  private fun updateLocalMatch() {
    if (winningResult == null) return

    viewModelScope.launch {
      if (match != null) {
        match =
          match?.copy(
            player1Wins = if (reverseScore) player2Score else player1Score,
            player2Wins = if (reverseScore) player1Score else player2Score,
            draws = drawCount,
          )

        match?.let { localMatchRepository.upsertLocalMatch(it) }
      } else {
        localMatchRepository.upsertLocalMatch(
          LocalMatch(
            player1 = player1Name,
            player2 = player2Name,
            player1Wins = player1Score,
            player2Wins = player2Score,
            draws = drawCount,
          )
        )
      }
    }
  }
}
