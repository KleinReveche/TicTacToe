package domain.repository

import domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun upsertPlayer(player: Player)
    suspend fun deletePlayer(player: Player)
    fun getPlayers(): Flow<List<Player>>
    fun getPlayerByName(name: String): Flow<Player>
    suspend fun playerExists(name: String): Boolean
}
