package domain.cases

data class GameDataUseCases(
    val upsertGameData: UpsertGameData,
    val deleteGameData: DeleteGameData,
    val getAllGameData: GetAllGameData
)
