package engine

import domain.model.PLAYER_O
import domain.model.PLAYER_X

object LocalGameEngine {
    data class WinResult(val isGameWon: Boolean, val winningMoves: List<Int>)

    fun isBoardFull(board: Array<Char?>): Boolean = board.all { it != null }

    private fun copyBoard(board: Array<Char?>): Array<Char?> {
        val newBoard = Array<Char?>(board.count()) { null }
        for (i in 0 until board.count()) {
            newBoard[i] = board[i]
        }
        return newBoard
    }

    /** Chooses a random empty cell as an easy move for the computer */
    fun computerMoveEasy(board: Array<Char?>): Int {
        val emptyCells = board.indices.filter { board[it] == null }
        return if (emptyCells.isEmpty()) -1 else emptyCells.random()
    }

    /**
     * Chooses a random move for the computer based on the order of priority to make the computer win.
     */
    fun computerMoveNormal(board: Array<Char?>): Int {
        // check if computer can win in this move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == null) copy[i] = PLAYER_O

            // check for win
            if (isGameWon(copy, PLAYER_O).isGameWon) return i
        }

        // check if player could win in the next move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == null) copy[i] = PLAYER_X

            if (isGameWon(copy, PLAYER_X).isGameWon) return i
        }

        // try to make corners if it is free
        val move = chooseRandomMove(board, listOf(0, 2, 6, 8))
        if (move != -1) return move

        // try to take center if it is free
        if (board[4] == null) return 4

        // finally try to make the sides
        return chooseRandomMove(board, listOf(1, 3, 5, 7))
    }

    private fun chooseRandomMove(board: Array<Char?>, moves: List<Int>): Int {
        val possibleMoves = moves.filter { board[it] == null }
        return if (possibleMoves.isEmpty()) -1 else possibleMoves.random()
    }

    /** Chooses a move for the computer based on the minimax algorithm to make the computer win. */
    fun computerMoveHard(board: Array<Char?>): Int {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in 0 until board.count()) {
            if (board[i] == null) {
                board[i] = PLAYER_O

                val score = minimaxAlgorithm(board, false)
                board[i] = null

                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }

        return bestMove
    }

    private fun minimaxAlgorithm(board: Array<Char?>, isMaximizing: Boolean): Int {
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
                if (board[i] == null) {
                    board[i] = PLAYER_O
                    val score = minimaxAlgorithm(board, false)
                    board[i] = null
                    bestScore = maxOf(bestScore, score)
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in 0 until board.count()) {
                if (board[i] == null) {
                    board[i] = PLAYER_X
                    val score = minimaxAlgorithm(board, true)
                    board[i] = null
                    bestScore = minOf(bestScore, score)
                }
            }
            return bestScore
        }
    }

    fun isGameWon(board: Array<Char?>, player: Char): WinResult {
        val winningIndices = mutableListOf<Int>()
        val winningMoves =
            listOf(
                listOf(0, 1, 2),
                listOf(3, 4, 5),
                listOf(6, 7, 8),
                listOf(0, 3, 6),
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(2, 4, 6),
                listOf(0, 4, 8),
            )

        for (move in winningMoves) {
            val (a, b, c) = move
            if (board[a] == board[b] && board[b] == board[c] && board[a] != null) {
                winningIndices.addAll(move)
            }
        }

        return WinResult(
            winningMoves.any { move -> move.all { board[it] == player } },
            winningIndices
        )
    }
}
