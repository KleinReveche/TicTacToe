package com.kleinreveche.tictactoe.server

import com.kleinreveche.tictactoe.server.room.GameRoomManager
import com.kleinreveche.tictactoe.server.room.gameSocket
import domain.model.PlayerMove
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlinx.serialization.json.Json
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Application.configureRouting() {
    install(WebSockets) {
        pingPeriod = 15.toDuration(DurationUnit.SECONDS)
        timeout = 15.toDuration(DurationUnit.SECONDS)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val gameRoomManager = GameRoomManager()

    routing {
        get("/status") {
            call.respondText("Hello World!")
        }

        route("/play/{roomId}") {
            gameSocket(gameRoomManager)
        }

        post("/get_room") {
            call.respondText(gameRoomManager.getOrCreateRoom())
        }
    }
}

fun extractAction(message: String): Pair<String, Any> {
    // deviceToken$action_type#{...}
    val deviceToken = message.substringBefore("|")
    val type = message.substringBefore("#").substringAfter("|")
    val payload = message.substringAfter("#")

    return when (type) {
        "player_move" -> { Pair(deviceToken, Json.decodeFromString<PlayerMove>(payload)) }
        else -> throw IllegalArgumentException("Unknown message type")
    }
}
