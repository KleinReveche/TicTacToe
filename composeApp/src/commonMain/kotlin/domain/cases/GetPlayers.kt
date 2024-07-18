package domain.cases

import domain.repository.PlayerRepository

class GetPlayers(private val repository: PlayerRepository) {
  operator fun invoke() = repository.getPlayers()
}
