package data.repository

import domain.model.LocalPlayer
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class LocalPlayerRepositoryTempImpl(
    private var tempLocalPlayers: List<LocalPlayer>
) : LocalPlayerRepository {
    override suspend fun upsertPlayer(localPlayer: LocalPlayer) {
        tempLocalPlayers += localPlayer
    }

    override suspend fun deletePlayer(localPlayer: LocalPlayer) {
        tempLocalPlayers -= localPlayer
    }

    override fun getPlayers(): Flow<List<LocalPlayer>> {
        return listOf(tempLocalPlayers).asFlow()
    }

    override fun getPlayerByName(name: String): Flow<LocalPlayer> {
        return tempLocalPlayers.find { it.name == name }.let {
            if (it != null) {
                listOf(it).asFlow()
            } else {
                emptyList<LocalPlayer>().asFlow()
            }
        }
    }

    override suspend fun playerExists(name: String): Boolean {
        return tempLocalPlayers.any { it.name == name }
    }
}