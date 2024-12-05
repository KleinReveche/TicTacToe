package com.kleinreveche.tictactoe.server.room

object GameEngine {
    fun getWinningPlayer(board: Array<Char?>): Pair<Char?, List<Int>> {
        var winningPlayer: Char? = null
        val winningIndices = mutableListOf<Int>()
        val winningMoves = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
            arrayOf(2, 4, 6), arrayOf(0, 4, 8)
        )

        for (move in winningMoves) {
            val (a, b, c) = move
            if (board[a] == board[b] && board[b] == board[c] && board[a] != null) {
                winningIndices.addAll(move)
            }
        }

        if (winningIndices.isNotEmpty()) {
            winningPlayer = board[winningIndices[0]]
        }

        return Pair(winningPlayer, winningIndices)
    }
}