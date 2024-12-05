package com.kleinreveche.tictactoe.server.room

import domain.model.PLAYER_O
import domain.model.PLAYER_X
import domain.model.RoomGameState
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class GameRoom(private val gameRoomManager: GameRoomManager, val roomId: String) {
    private val state = MutableStateFlow(RoomGameState())
    private val playerSockets = ConcurrentHashMap<Char, WebSocketSession>()
    private val gameScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var delayGameJob: Job? = null

    init { state.onEach(::broadcast).launchIn(gameScope) }

    fun connectPlayer(session: WebSocketSession): Char? {
        println("Connecting player to Room $roomId...")
        val isPlayerX = state.value.connectedPlayers.any { it == PLAYER_X }
        val player = if (isPlayerX) PLAYER_O else PLAYER_X

        state.update {
            if (state.value.connectedPlayers.contains(player)) {
                println("Player already connected to Room $roomId!")
                return null
            }

            if (!playerSockets.containsKey(player)) { playerSockets[player] = session }
            it.copy(connectedPlayers = it.connectedPlayers + player)
        }
        println("Player $player connected to Room $roomId!")
        return player
    }

    fun disconnectPlayer(player: Char) {
        playerSockets.remove(player)
        state.update { it.copy(connectedPlayers = it.connectedPlayers - player) }

        if (playerSockets.isEmpty()) {
            gameRoomManager.deleteRoom(roomId)
        }
    }

    private suspend fun broadcast(state: RoomGameState) {
        playerSockets.values.forEach { socket ->
            socket.send(
                Json.encodeToString(
                    state.copy(currentPlayerConnectedChar = playerSockets.filter { it.value == socket }.keys.firstOrNull())
                ))
        }
    }

    fun handleMove(player: Char, move: Int, gameVersion: Int) {
        val currentPlayer = state.value.currentPlayerAtTurn

        if (gameVersion != 1) return

        if (state.value.board[move] != null || state.value.winningPlayer != null || currentPlayer != player) return

        state.update { s ->
            val newField = s.board.also { field ->
                field[move] = currentPlayer
            }

            val isBoardFull = newField.all { it != null }
            if (isBoardFull) startNewRound()
            val winningPlayerResult = GameEngine.getWinningPlayer(state.value.board)

            s.copy(
                currentPlayerAtTurn = if (currentPlayer == PLAYER_X) PLAYER_O else PLAYER_X,
                board = newField,
                winningPlayer = winningPlayerResult.first?.also { startNewRound() },
                winningMoves = winningPlayerResult.second,
                playerXWins = if (winningPlayerResult.first == PLAYER_X) s.playerXWins + 1 else s.playerXWins,
                playerOWins = if (winningPlayerResult.first == PLAYER_O) s.playerOWins + 1 else s.playerOWins,
                draws = if (isBoardFull && winningPlayerResult.first == null) s.draws + 1 else s.draws,
                isBoardFull = isBoardFull
            )
        }
    }

    fun getNumberOfPlayers(): Int = state.value.connectedPlayers.size

    private fun startNewRound() {
        delayGameJob?.cancel()
        delayGameJob = gameScope.launch {
            delay(5000L)
            state.update {
                it.copy(
                    currentPlayerAtTurn = PLAYER_X,
                    board = RoomGameState.emptyField(),
                    winningPlayer = null,
                    winningMoves = emptyList(),
                    isBoardFull = false
                )
            }
        }
    }
}