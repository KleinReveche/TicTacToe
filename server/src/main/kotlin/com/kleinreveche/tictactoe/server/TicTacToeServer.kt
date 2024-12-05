package com.kleinreveche.tictactoe.server

import com.kleinreveche.tictactoe.server.plugins.configureMonitoring
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
    configureMonitoring()
    configureRouting()
}