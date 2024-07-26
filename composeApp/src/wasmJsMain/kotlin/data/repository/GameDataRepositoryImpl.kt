package data.repository

import domain.model.GameData
import domain.repository.GameDataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.w3c.dom.Storage

class GameDataRepositoryImpl(
    private val localStorage: Storage
) : GameDataRepository {
    private val delay: Long = 5000L

    override suspend fun upsertGameData(gameData: GameData) {
        localStorage.setItem("GameData: ${gameData.id}", Json.encodeToString(GameData.serializer(), gameData))
    }

    override suspend fun deleteGameData(gameData: GameData) {
        localStorage.removeItem("GameData: ${gameData.id}")
    }

    override fun getAllGameData(): Flow<List<GameData>> = flow {
        while (true) {
            val gameData = mutableListOf<GameData>()
            for (i in 0 until localStorage.length) {
                val key = localStorage.key(i) ?: continue
                if (key.startsWith("GameData: ")) {
                    val gameDataJson = localStorage.getItem(key) ?: continue
                    val gameDatum = Json.decodeFromString(GameData.serializer(), gameDataJson)
                    gameData.add(gameDatum)
                }
            }
            emit(gameData)
            delay(delay)
        }
    }
}