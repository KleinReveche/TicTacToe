package di

import data.repository.GameDataRepositoryImpl
import data.repository.PlayerRepositoryImpl
import data.sources.PlayerDao
import data.sources.TicTacToeDatabase
import domain.cases.DeleteGameData
import domain.cases.DeletePlayer
import domain.cases.GetAllGameData
import domain.cases.GetPlayerByName
import domain.cases.GetPlayers
import domain.cases.PlayerExists
import domain.cases.UpsertGameData
import domain.cases.UpsertPlayer
import domain.repository.GameDataRepository
import domain.repository.PlayerRepository
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.navigation.NavigationViewModel

expect val ticTacToeDatabasePlatformModule: Module

val ticTacToeDatabaseSharedModule = module {
    single { get<TicTacToeDatabase>().playerDao() }.bind<PlayerDao>()

    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    singleOf(::UpsertPlayer)
    singleOf(::DeletePlayer)
    singleOf(::GetPlayers)
    singleOf(::GetPlayerByName)
    singleOf(::PlayerExists)

    singleOf(::GameDataRepositoryImpl).bind<GameDataRepository>()
    singleOf(::UpsertGameData)
    singleOf(::DeleteGameData)
    singleOf(::GetAllGameData)

    viewModelOf(::NavigationViewModel)
}