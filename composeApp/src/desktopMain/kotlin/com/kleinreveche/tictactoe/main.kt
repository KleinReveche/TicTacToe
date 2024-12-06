package com.kleinreveche.tictactoe

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kleinreveche.tictactoe.di.initKoin
import com.kleinreveche.tictactoe.resources.Res
import com.kleinreveche.tictactoe.resources.icon
import org.jetbrains.compose.resources.painterResource

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(width = 800.dp, height = 800.dp),
            title = "TicTacToe",
            icon = painterResource(Res.drawable.icon),
        ) {
            TicTacToe()
        }
    }
}