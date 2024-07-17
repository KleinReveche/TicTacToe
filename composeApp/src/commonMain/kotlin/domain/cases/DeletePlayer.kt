package domain.cases

import domain.model.Player
import domain.repository.PlayerRepository

class DeletePlayer(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(player: Player) = repository.deletePlayer(player)
}
