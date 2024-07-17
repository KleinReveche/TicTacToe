package domain.cases

data class PlayerUseCases(
    val upsertPlayer: UpsertPlayer,
    val deletePlayer: DeletePlayer,
    val getPlayerByName: GetPlayerByName,
    val getPlayers: GetPlayers,
    val playerExists: PlayerExists
)
