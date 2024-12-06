package com.kleinreveche.tictactoe.data.repository

import com.kleinreveche.tictactoe.domain.model.LocalPlayer
import com.kleinreveche.tictactoe.domain.repository.LocalPlayerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.w3c.dom.Storage

class LocalPlayerRepositoryImpl(
    private val localStorage: Storage
) : LocalPlayerRepository {
    private val delay: Long = 5000L

    override suspend fun upsertPlayer(localPlayer: LocalPlayer) {
        localStorage.setItem("LocalPlayer: ${localPlayer.name}", Json.encodeToString(LocalPlayer.serializer(), localPlayer))
    }

    override suspend fun deletePlayer(localPlayer: LocalPlayer) {
        localStorage.removeItem("LocalPlayer: ${localPlayer.name}")
    }

    override fun getPlayers(): Flow<List<LocalPlayer>> = flow {
        while (true) {
            val localPlayers = mutableListOf<LocalPlayer>()
            for (i in 0 until localStorage.length) {
                val key = localStorage.key(i) ?: continue
                if (key.startsWith("LocalPlayer: ")) {
                    val localPlayerJson = localStorage.getItem(key) ?: continue
                    val localPlayer = Json.decodeFromString(LocalPlayer.serializer(), localPlayerJson)
                    localPlayers.add(localPlayer)
                }
            }
            emit(localPlayers)
            delay(delay)
        }
    }

    override fun getPlayerByName(name: String): Flow<LocalPlayer> = flow {
        while (true) {
            val key = "LocalPlayer: $name"
            val localPlayerName = localStorage.getItem(key)
            val localPlayer = Json.decodeFromString(LocalPlayer.serializer(), localPlayerName!!)
            emit(localPlayer)
            delay(delay)
        }
    }

    override suspend fun playerExists(name: String): Boolean {
        return localStorage.getItem("LocalPlayer: $name") != null
    }
}