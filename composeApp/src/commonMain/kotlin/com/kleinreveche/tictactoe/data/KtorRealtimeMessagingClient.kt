package com.kleinreveche.tictactoe.data

import com.kleinreveche.tictactoe.domain.model.PlayerMove
import com.kleinreveche.tictactoe.domain.model.ServerGameState
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRealtimeMessagingClient(private val client: HttpClient): RealtimeMessagingClient {
    private var session: WebSocketSession? = null

    override fun getGameStateStream(): Flow<ServerGameState> {
        return flow {
            session = client.webSocketSession {
                url {
                    protocol = URLProtocol.WS
                    host = "127.0.0.1"
                    port = 8080
                    encodedPath = "/play/v1/"
                }
            }
            val gameStates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull { Json.decodeFromString<ServerGameState>(it.readText()) }

            emitAll(gameStates)
        }
    }

    override suspend fun sendAction(action: PlayerMove) {
        session?.outgoing?.send(
            Frame.Text("player_move#${Json.encodeToString(action)}")
        )
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}