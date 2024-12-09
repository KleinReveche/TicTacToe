package com.kleinreveche.tictactoe.server

import com.kleinreveche.tictactoe.server.domain.TicTacToeGameServer
import com.kleinreveche.tictactoe.server.room.GameRoomManager
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

val gameRoomManager = GameRoomManager()
val legacyGameRoom = TicTacToeGameServer()

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
    configurePlugins()
    configureRouting(gameRoomManager)
}