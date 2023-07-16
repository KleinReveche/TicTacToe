package com.kleinreveche.tictactoe.features.local.engine

import com.kleinreveche.tictactoe.data.local.LocalDataStoreKeys
import com.kleinreveche.tictactoe.data.local.TicTacToeLocalDataStoreHelper.setPref
import kotlinx.coroutines.CoroutineScope

object GameEngine {
    const val PLAYER_X = "X" // player 1
    const val PLAYER_O = "O" // player 2 or computer

    data class WinResult(
        val isGameWon: Boolean,
        val winningMoves: List<Int>
    )

    fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board) {
            if (i != PLAYER_X && i != PLAYER_O) return false
        }
        return true
    }

    private fun copyBoard(board: ArrayList<String>): ArrayList<String> {
        val newBoard = arrayListOf("", "", "", "", "", "", "", "", "")
        for (i in 0 until board.count()) {
            newBoard[i] = board[i]
        }
        return newBoard
    }

    /**
     * Chooses a random empty cell as an easy move for the computer
     */
    fun computerMoveEasy(board: ArrayList<String>): Int {
        val emptyCells = board.indices.filter { board[it] == "" }
        return if (emptyCells.isEmpty()) -1
        else emptyCells.random()
    }

    /**
     * Chooses a random move for the computer based on the order of
     * priority to make the computer win.
     * */
    fun computerMoveNormal(board: ArrayList<String>): Int {
        // check if computer can win in this move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == "") copy[i] = PLAYER_O

            // check for win
            if (isGameWon(copy, PLAYER_O).isGameWon) return i
        }

        // check if player could win in the next move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == "") copy[i] = PLAYER_X

            if (isGameWon(copy, PLAYER_X).isGameWon) return i
        }

        // try to make corners if it is free
        val move = chooseRandomMove(board, arrayListOf(0, 2, 6, 8))
        if (move != -1) return move

        // try to take center if it is free
        if (board[4] == "") return 4

        // finally try to make the sides
        return chooseRandomMove(board, arrayListOf(1, 3, 5, 7))
    }

    private fun chooseRandomMove(board: ArrayList<String>, moves: List<Int>): Int {
        val possibleMoves = moves.filter { board[it] == "" }
        return if (possibleMoves.isEmpty()) -1
        else possibleMoves.random()
    }

    /**
     * Chooses a move for the computer based on the
     *  minimax algorithm to make the computer win.
     * */
    fun computerMoveHard(board: ArrayList<String>): Int {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in 0 until board.count()) {
            if (board[i] == "") {
                board[i] = PLAYER_O

                val score = minimaxAlgorithm(board, false)
                board[i] = ""

                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }

        return bestMove
    }

    private fun minimaxAlgorithm(board: ArrayList<String>, isMaximizing: Boolean): Int {
        if (isGameWon(board, PLAYER_X).isGameWon) {
            return -1 // Player X wins
        } else if (isGameWon(board, PLAYER_O).isGameWon) {
            return 1 // Player O wins
        } else if (isBoardFull(board)) {
            return 0 // It's a tie
        }

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in 0 until board.count()) {
                if (board[i] == "") {
                    board[i] = PLAYER_O
                    val score = minimaxAlgorithm(board, false)
                    board[i] = ""
                    bestScore = maxOf(bestScore, score)
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in 0 until board.count()) {
                if (board[i] == "") {
                    board[i] = PLAYER_X
                    val score = minimaxAlgorithm(board, true)
                    board[i] = ""
                    bestScore = minOf(bestScore, score)
                }
            }
            return bestScore
        }
    }

    fun isGameWon(board: ArrayList<String>, player: String): WinResult {
        val winningIndices = mutableListOf<Int>()
        val winningMoves = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(2, 4, 6), listOf(0, 4, 8)
        )
        for (move in winningMoves) {
            val (a, b, c) = move
            if (board[a] == board[b] && board[b] == board[c] && board[a] != "") {
                winningIndices.addAll(move)
            }
        }

        return WinResult(
            winningMoves.any { move -> move.all { board[it] == player } },
            winningIndices
        )
    }

    fun gameResult(board: ArrayList<String>, singleMode: Boolean): String {
        return when {
            isGameWon(board, PLAYER_X).isGameWon -> "${if (singleMode) "You" else "Player X"} Won!"
            isGameWon(board, PLAYER_O).isGameWon -> "${if (singleMode) "AI" else "Player O"} Won!"
            isBoardFull(board) -> "It's a tie!"
            else -> "Tie"
        }
    }

    fun CoroutineScope.saveGameResult(
        board: ArrayList<String>,
        singleMode: Boolean,
        computerDifficulty: Int
    ) {
        if (singleMode) {
            when {
                isGameWon(board, PLAYER_X).isGameWon -> setPref(
                    when (computerDifficulty) {
                        0 -> LocalDataStoreKeys.WINS_EASY
                        1 -> LocalDataStoreKeys.WINS_NORMAL
                        else -> LocalDataStoreKeys.WINS_HARD
                    }
                )

                isGameWon(board, PLAYER_O).isGameWon -> setPref(
                    when (computerDifficulty) {
                        0 -> LocalDataStoreKeys.LOSES_EASY
                        1 -> LocalDataStoreKeys.LOSES_NORMAL
                        else -> LocalDataStoreKeys.LOSES_HARD
                    }
                )

                isBoardFull(board) -> setPref(
                    when (computerDifficulty) {
                        0 -> LocalDataStoreKeys.DRAWS_EASY
                        1 -> LocalDataStoreKeys.DRAWS_NORMAL
                        else -> LocalDataStoreKeys.DRAWS_HARD
                    }
                )
            }
        }
    }
}
