package com.kleinreveche.tictactoe

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.kleinreveche.tictactoe.di.initKoin
import kotlinx.browser.document

external fun onLoadFinished()

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onLoadFinished()
    ComposeViewport(document.body!!) {
        TicTacToe()
    }
}
