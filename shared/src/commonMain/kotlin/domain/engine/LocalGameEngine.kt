package domain.engine

import domain.model.PLAYER_O
import domain.model.PLAYER_X

object LocalGameEngine {
    data class WinResult(
        val isGameWon: Boolean,
        val winningMoves: List<Int>,
    )

    fun isBoardFull(board: Array<Char?>): Boolean = board.all { it != null }

    private fun copyBoard(board: Array<Char?>): Array<Char?> {
        val newBoard = Array<Char?>(board.count()) { null }
        for (i in 0 until board.count()) {
            newBoard[i] = board[i]
        }
        return newBoard
    }

    /**
     * Chooses a normal move but with a 50% chance for a random move.
     */
    fun computerMoveEasy(board: Array<Char?>, computerPlayerType: Char): Int {
        val randomChance = (1..100).random()

        if (randomChance <= 50) {
            val emptyCells = board.indices.filter { board[it] == null }
            return if (emptyCells.isEmpty()) -1 else emptyCells.random()
        } else {
            return computerMoveNormal(board, computerPlayerType)
        }
    }

    /**
     * Chooses a random move for the computer based on the order of priority to make the computer win.
     */
    fun computerMoveNormal(board: Array<Char?>, computerPlayerType: Char): Int {
        // check if computer can win in this move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == null) copy[i] = computerPlayerType

            // check for win
            if (isGameWon(copy, computerPlayerType).isGameWon) return i
        }

        // check if player could win in the next move
        val opponentPlayerType = if (computerPlayerType == PLAYER_X) PLAYER_O else PLAYER_X
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == null) copy[i] = opponentPlayerType

            if (isGameWon(copy, opponentPlayerType).isGameWon) return i
        }

        // try to make corners if it is free
        val move = chooseRandomMove(board, listOf(0, 2, 6, 8))
        if (move != -1) return move

        // try to take center if it is free
        if (board[4] == null) return 4

        // finally try to make the sides
        return chooseRandomMove(board, listOf(1, 3, 5, 7))
    }

    private fun chooseRandomMove(
        board: Array<Char?>,
        moves: List<Int>,
    ): Int {
        val possibleMoves = moves.filter { board[it] == null }
        return if (possibleMoves.isEmpty()) -1 else possibleMoves.random()
    }

    /** Chooses a move for the computer based on the minimax algorithm to make the computer win. */
    fun computerMoveInsane(board: Array<Char?>, computerPlayerType: Char): Int {
        val opponentPlayerType = if (computerPlayerType == PLAYER_X) PLAYER_O else PLAYER_X
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in board.indices) {
            if (board[i] == null) {
                board[i] = computerPlayerType
                val score = minimaxAlgorithm(board, false, computerPlayerType, opponentPlayerType)
                board[i] = null
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }

        return bestMove
    }

    private fun minimaxAlgorithm(
        board: Array<Char?>,
        isMaximizing: Boolean,
        computerPlayerType: Char,
        opponentPlayerType: Char
    ): Int {
        val winResultX = isGameWon(board, PLAYER_X)
        val winResultO = isGameWon(board, PLAYER_O)

        if (winResultX.isGameWon) {
            return if (computerPlayerType == PLAYER_X) 1 else -1
        } else if (winResultO.isGameWon) {
            return if (computerPlayerType == PLAYER_O) 1 else -1
        } else if (isBoardFull(board)) {
            return 0
        }

        return if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i] == null) {
                    board[i] = computerPlayerType
                    val score = minimaxAlgorithm(board, false, computerPlayerType, opponentPlayerType)
                    board[i] = null
                    bestScore = maxOf(bestScore, score)
                }
            }
            bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i] == null) {
                    board[i] = opponentPlayerType
                    val score = minimaxAlgorithm(board, true, computerPlayerType, opponentPlayerType)
                    board[i] = null
                    bestScore = minOf(bestScore, score)
                }
            }
            bestScore
        }
    }

    fun isGameWon(board: Array<Char?>, player: Char): WinResult {
        val winningMoves = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        val winningIndices = mutableListOf<Int>()
        for (move in winningMoves) {
            val (a, b, c) = move
            if (board[a] == player && board[a] == board[b] && board[b] == board[c]) {
                winningIndices.addAll(move)
            }
        }

        return WinResult(winningIndices.isNotEmpty(), winningIndices)
    }
}
