package com.kleinreveche.tictactoe.data.repository

import com.kleinreveche.tictactoe.data.sources.LocalPlayerDao
import com.kleinreveche.tictactoe.data.util.Converters.toLocalPlayer
import com.kleinreveche.tictactoe.data.util.Converters.toLocalPlayerRoom
import com.kleinreveche.tictactoe.domain.model.LocalPlayer
import com.kleinreveche.tictactoe.domain.repository.LocalPlayerRepository
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
