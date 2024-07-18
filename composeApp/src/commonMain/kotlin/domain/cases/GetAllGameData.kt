package domain.cases

import domain.repository.GameDataRepository
import kotlinx.coroutines.flow.map

class GetAllGameData(private val repository: GameDataRepository) {
  operator fun invoke() = repository.getAllGameData()

  operator fun invoke(name: String) =
    repository.getAllGameData().map { it ->
      it.filter { it.player1Name == name || it.player2Name == name }
    }
}
