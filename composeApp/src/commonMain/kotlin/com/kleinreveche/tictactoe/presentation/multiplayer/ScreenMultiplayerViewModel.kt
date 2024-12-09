package com.kleinreveche.tictactoe.presentation.multiplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kleinreveche.tictactoe.data.RealtimeMessagingClient
import com.kleinreveche.tictactoe.domain.model.PlayerMove
import com.kleinreveche.tictactoe.domain.model.ServerGameState
import io.ktor.client.network.sockets.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScreenMultiplayerViewModel(private val client: RealtimeMessagingClient) : ViewModel() {

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting = _isConnecting.asStateFlow()
    val state = client
        .getGameStateStream()
        .onStart { _isConnecting.value = true }
        .onEach { _isConnecting.value = false }
        .catch { t -> _showConnectionError.value = t is ConnectTimeoutException }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ServerGameState())

    private val _showConnectionError = MutableStateFlow(true)
    val showConnectionError = _showConnectionError.asStateFlow()

    fun finishTurn(move: Int) {
        if(state.value.gameState.board[move] != null || state.value.gameState.winningPlayer != null) return

        viewModelScope.launch {
            client.sendAction(PlayerMove(move, 1))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.close()
        }
    }
}