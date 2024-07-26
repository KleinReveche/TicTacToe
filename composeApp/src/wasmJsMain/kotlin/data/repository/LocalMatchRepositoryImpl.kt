package data.repository

import domain.model.LocalMatch
import domain.repository.LocalMatchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.w3c.dom.Storage

class LocalMatchRepositoryImpl(
    private val localStorage: Storage
) : LocalMatchRepository {
    private val delay: Long = 5000L

    override fun getLocalMatches(): Flow<List<LocalMatch>> = flow {
        while (true) {
            val localMatches = mutableListOf<LocalMatch>()
            for (i in 0 until localStorage.length) {
                val key = localStorage.key(i) ?: continue
                if (key.startsWith("LocalMatch: ")) {
                    val localMatchJson = localStorage.getItem(key) ?: continue
                    val localMatch = Json.decodeFromString(LocalMatch.serializer(), localMatchJson)
                    localMatches.add(localMatch)
                }
            }
            emit(localMatches)
            delay(delay)
        }
    }

    override fun getLocalMatch(id: Int): Flow<LocalMatch?> = flow {
        while (true) {
            val key = "LocalMatch: $id"
            val localMatchJson = localStorage.getItem(key)
            if (localMatchJson != null) {
                val localMatch = Json.decodeFromString(LocalMatch.serializer(), localMatchJson)
                emit(localMatch)
            } else {
                emit(null)
            }
            delay(delay)
        }
    }

    override fun getLocalMatchByPlayer(player: String): Flow<List<LocalMatch>> = flow {
        while (true) {
            val localMatches = mutableListOf<LocalMatch>()
            for (i in 0 until localStorage.length) {
                val key = localStorage.key(i) ?: continue
                if (key.startsWith("LocalMatch: ")) {
                    val localMatchJson = localStorage.getItem(key) ?: continue
                    val localMatch = Json.decodeFromString(LocalMatch.serializer(), localMatchJson)
                    if (localMatch.player1 == player || localMatch.player2 == player) {
                        localMatches.add(localMatch)
                    }
                }
            }
            emit(localMatches)
            delay(delay)
        }
    }

    override fun getMatchBetweenPlayers(player1: String, player2: String): Flow<LocalMatch?> = flow {
        while (true) {
            val localMatch = (0 until localStorage.length)
                .mapNotNull { localStorage.key(it) }
                .mapNotNull { key ->
                    if (key.startsWith("LocalMatch: ")) {
                        val localMatchJson = localStorage.getItem(key) ?: return@mapNotNull null
                        Json.decodeFromString(LocalMatch.serializer(), localMatchJson)
                    } else {
                        null
                    }
                }
                .find { (it.player1 == player1 && it.player2 == player2) || (it.player1 == player2 && it.player2 == player1) }
            emit(localMatch)
            delay(delay)
        }
    }

    override suspend fun upsertLocalMatch(localMatch: LocalMatch) {
        localStorage.setItem("LocalMatch: ${localMatch.id}", Json.encodeToString(LocalMatch.serializer(), localMatch))
    }

    override suspend fun deleteLocalMatch(localMatch: LocalMatch) {
        localStorage.removeItem("LocalMatch: ${localMatch.id}")
    }
}