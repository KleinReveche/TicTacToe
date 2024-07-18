package domain.cases

import domain.repository.PlayerRepository

class GetPlayerByName(private val repository: PlayerRepository) {
  operator fun invoke(name: String) = repository.getPlayerByName(name)
}
