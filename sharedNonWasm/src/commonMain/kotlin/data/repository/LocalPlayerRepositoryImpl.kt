package data.repository

import data.sources.LocalPlayerDao
import data.util.Converters.toLocalPlayer
import data.util.Converters.toLocalPlayerRoom
import domain.model.LocalPlayer
import domain.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPlayerRepositoryImpl(
    private val localPlayerDao: LocalPlayerDao,
) : LocalPlayerRepository {
    override suspend fun upsertPlayer(localPlayer: LocalPlayer) = localPlayerDao.upsertPlayer(localPlayer.toLocalPlayerRoom())

    override suspend fun deletePlayer(localPlayer: LocalPlayer) = localPlayerDao.deletePlayer(localPlayer.toLocalPlayerRoom())

    override fun getPlayers(): Flow<List<LocalPlayer>> =
        localPlayerDao.getPlayers().map { localPlayers ->
            localPlayers.map { it.toLocalPlayer() }
        }

    override fun getPlayerByName(name: String): Flow<LocalPlayer> = localPlayerDao.getPlayerByName(name).map { it.toLocalPlayer() }

    override suspend fun playerExists(name: String): Boolean = localPlayerDao.playerExists(name)
}
