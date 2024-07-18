package domain.cases

import domain.model.GameData
import domain.repository.GameDataRepository

class DeleteGameData(private val repository: GameDataRepository) {
  suspend operator fun invoke(gameData: GameData) = repository.deleteGameData(gameData)
}
