package com.kleinreveche.tictactoe.features.local.engine

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kleinreveche.tictactoe.data.local.LocalDataStoreKeys
import com.kleinreveche.tictactoe.data.local.TicTacToeLocalDataStoreHelper.getPref
import com.kleinreveche.tictactoe.data.local.TicTacToeLocalDataStoreHelper.setPref
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_O
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.PLAYER_X
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.gameResult
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.isBoardFull
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.isGameWon
import com.kleinreveche.tictactoe.features.local.engine.GameEngine.saveGameResult

@SuppressLint("MutableCollectionMutableState")
class GameViewModel : ViewModel() {

    private var isGameOver by mutableStateOf(false)
    private var winner by mutableStateOf("")

    var board by mutableStateOf(arrayListOf("", "", "", "", "", "", "", "", ""))
        private set
    var winningMoves by mutableStateOf(emptyList<Int>())
        private set
    var computerDifficulty by mutableStateOf(getPref(LocalDataStoreKeys.COMPUTER_DIFFICULTY) as Int)
    var computerFirstMove by mutableStateOf(getPref(LocalDataStoreKeys.COMPUTER_FIRST_MOVE) as Boolean)
    var isSinglePlayer by mutableStateOf(getPref(LocalDataStoreKeys.COMPUTER_AS_OPPONENT) as Boolean)
    var playerWinCount by mutableStateOf(
        getPref(
            when (computerDifficulty) {
                0 -> LocalDataStoreKeys.WINS_EASY
                1 -> LocalDataStoreKeys.WINS_NORMAL
                else -> LocalDataStoreKeys.WINS_HARD
            }
        ) as Int
    )
    var aiWinCount by mutableStateOf(
        getPref(
            when (computerDifficulty) {
                0 -> LocalDataStoreKeys.LOSES_EASY
                1 -> LocalDataStoreKeys.LOSES_NORMAL
                else -> LocalDataStoreKeys.LOSES_HARD
            }
        ) as Int
    )
    var drawCount by mutableStateOf(
        getPref(
            when (computerDifficulty) {
                0 -> LocalDataStoreKeys.DRAWS_EASY
                1 -> LocalDataStoreKeys.DRAWS_NORMAL
                else -> LocalDataStoreKeys.DRAWS_HARD
            }
        ) as Int
    )
    var showDraw by mutableStateOf(getPref(LocalDataStoreKeys.SHOW_DRAWS) as Boolean)

    var playerXWinCount by mutableStateOf(0)
    var playerOWinCount by mutableStateOf(0)
    var multiplayerDrawCount by mutableStateOf(0)

    var currentPlayer by mutableStateOf(if (getPref(LocalDataStoreKeys.COMPUTER_FIRST_MOVE) as Boolean) PLAYER_O else PLAYER_X)
        private set

    fun play(move: Int) {
        if (isGameOver) return

        if (board[move] == "") {
            if (currentPlayer == PLAYER_X) {
                board = ArrayList(
                    board.toMutableList().also {
                        it[move] = PLAYER_X
                    }
                )
                currentPlayer = PLAYER_O

                if (isSinglePlayer) {

                    if (!isBoardFull(board) && !isGameWon(board, PLAYER_X).isGameWon) {
                        val nextMove = when (computerDifficulty) {
                            0 -> GameEngine.computerMoveEasy(board)
                            1 -> GameEngine.computerMoveNormal(board)
                            2 -> GameEngine.computerMoveHard(board)
                            else -> throw IllegalArgumentException("This Difficulty doesn't exist")
                        }

                        board = ArrayList(
                            board.toMutableList().also {
                                it[nextMove] = PLAYER_O
                            }
                        )
                    }
                    currentPlayer = PLAYER_X
                }
            } else { // Player O
                board = ArrayList(
                    board.toMutableList().also {
                        it[move] = PLAYER_O
                    }
                )
                currentPlayer = PLAYER_X

                if (isSinglePlayer) {

                    if (!isBoardFull(board) && !isGameWon(board, PLAYER_O).isGameWon) {
                        val nextMove = when (computerDifficulty) {
                            0 -> GameEngine.computerMoveEasy(board)
                            1 -> GameEngine.computerMoveNormal(board)
                            2 -> GameEngine.computerMoveHard(board)
                            else -> throw IllegalArgumentException("This Difficulty doesn't exist")
                        }

                        board = ArrayList(
                            board.toMutableList().also {
                                it[nextMove] = PLAYER_X
                            }
                        )
                    }
                    currentPlayer = PLAYER_O
                }
            }
        }
        isGameOver = isGameWon(board, PLAYER_X).isGameWon || isGameWon(
            board,
            PLAYER_O
        ).isGameWon || isBoardFull(board)
        winningMoves = isGameWon(board, PLAYER_X).winningMoves
        updateWinCounters(board, isSinglePlayer)
        winner = gameResult(board, isSinglePlayer)
    }


    fun computerPlay() {
        reset()
        currentPlayer = PLAYER_O

        val nextMove = when (computerDifficulty) {
            0 -> GameEngine.computerMoveEasy(board)
            1 -> GameEngine.computerMoveNormal(board)
            // Minimax Algorithm takes too long when the board is empty, so a random move will be first made.
            2 -> if (board == arrayListOf(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            ) GameEngine.computerMoveEasy(board) else GameEngine.computerMoveHard(board)

            else -> throw IllegalArgumentException("This Difficulty doesn't exist")
        }

        board = ArrayList(
            board.toMutableList().also {
                it[nextMove] = PLAYER_O
            }
        )

        currentPlayer = PLAYER_X
        play(nextMove)
    }

    fun reset(currentPlayerO: Boolean = false) {
        isGameOver = false
        board = arrayListOf("", "", "", "", "", "", "", "", "")
        winningMoves = emptyList()
        currentPlayer = if (currentPlayerO && !isSinglePlayer) PLAYER_O else PLAYER_X
    }

    fun resetMultiplayerStats() {
        reset()
        playerXWinCount = 0
        playerOWinCount = 0
        multiplayerDrawCount = 0
    }

    fun updatePlayerMode(singlePlayer: Boolean) {
        reset()
        this.isSinglePlayer = singlePlayer
        this.currentPlayer = if (computerFirstMove && !isSinglePlayer) PLAYER_O else PLAYER_X
    }

    fun updateComputerDifficulty(difficulty: Int) {
        reset()
        this.computerDifficulty = difficulty
        changeScores()
    }

    fun showDraw(showDraw: Boolean) {
        this.showDraw = showDraw
    }

    fun computerFirstMove(computerFirstMove: Boolean = false, isComputer: Boolean = false) {
        this.computerFirstMove = computerFirstMove
        if (isComputer) computerPlay() else reset(computerFirstMove)

    }

    private fun updateWinCounters(board: ArrayList<String>, singlePlayer: Boolean) {
        viewModelScope.saveGameResult(board, singlePlayer, computerDifficulty)
        if (singlePlayer) {
            when {
                isGameWon(board, PLAYER_X).isGameWon -> this.playerWinCount++
                isGameWon(board, PLAYER_O).isGameWon -> this.aiWinCount++
                isBoardFull(board) -> this.drawCount++
            }
        }
        if (!singlePlayer) {
            when {
                isGameWon(board, PLAYER_X).isGameWon -> this.playerXWinCount++
                isGameWon(board, PLAYER_O).isGameWon -> this.playerOWinCount++
                isBoardFull(board) -> this.multiplayerDrawCount++
            }
        }
    }

    fun updatePrefs(
        key: LocalDataStoreKeys,
        value: Any = Any()
    ) {
        viewModelScope.setPref(key, value)
    }

    private fun changeScores() {
        playerWinCount = when (computerDifficulty) {
            0 -> getPref(LocalDataStoreKeys.WINS_EASY)
            1 -> getPref(LocalDataStoreKeys.WINS_NORMAL)
            else -> getPref(LocalDataStoreKeys.WINS_HARD)
        } as Int
        aiWinCount = when (computerDifficulty) {
            0 -> getPref(LocalDataStoreKeys.LOSES_EASY)
            1 -> getPref(LocalDataStoreKeys.LOSES_NORMAL)
            else -> getPref(LocalDataStoreKeys.LOSES_HARD)
        } as Int
        drawCount = when (computerDifficulty) {
            0 -> getPref(LocalDataStoreKeys.DRAWS_EASY)
            1 -> getPref(LocalDataStoreKeys.DRAWS_NORMAL)
            else -> getPref(LocalDataStoreKeys.DRAWS_HARD)
        } as Int
    }

}
