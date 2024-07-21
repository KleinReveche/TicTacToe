package domain.repository

import domain.model.LocalPlayer
import kotlinx.coroutines.flow.Flow

interface LocalPlayerRepository {
  suspend fun upsertPlayer(localPlayer: LocalPlayer)

  suspend fun deletePlayer(localPlayer: LocalPlayer)

  fun getPlayers(): Flow<List<LocalPlayer>>

  fun getPlayerByName(name: String): Flow<LocalPlayer>

  suspend fun playerExists(name: String): Boolean
}
