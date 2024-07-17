package domain.cases

import domain.repository.PlayerRepository

class PlayerExists(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(name: String) = repository.playerExists(name)
}
