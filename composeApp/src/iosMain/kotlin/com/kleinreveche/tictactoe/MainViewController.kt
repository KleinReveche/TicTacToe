package com.kleinreveche.tictactoe

import androidx.compose.ui.window.ComposeUIViewController
import com.kleinreveche.tictactoe.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    TicTacToe()
}