package di

import data.repository.AppSettingRepositoryImpl
import data.repository.GameDataRepositoryImpl
import data.repository.PlayerRepositoryImpl
import data.sources.AppSettingDao
import data.sources.GameDataDao
import data.sources.PlayerDao
import data.sources.TicTacToeDatabase
import domain.cases.DeleteGameData
import domain.cases.DeletePlayer
import domain.cases.GetAllGameData
import domain.cases.GetAppSetting
import domain.cases.GetAppSettings
import domain.cases.GetPlayerByName
import domain.cases.GetPlayers
import domain.cases.PlayerExists
import domain.cases.UpsertAppSetting
import domain.cases.UpsertGameData
import domain.cases.UpsertPlayer
import domain.repository.AppSettingRepository
import domain.repository.GameDataRepository
import domain.repository.PlayerRepository
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.localvscomputer.ScreenLocalVsComputerViewModel
import presentation.navigation.NavigationViewModel
import presentation.navigation.ScreenMainViewModel

expect val ticTacToePlatformModule: Module

val ticTacToeDatabaseSharedModule = module {
  single { get<TicTacToeDatabase>().playerDao() }.bind<PlayerDao>()
  single { get<TicTacToeDatabase>().gameDataDao() }.bind<GameDataDao>()
  single { get<TicTacToeDatabase>().appSettingDao() }.bind<AppSettingDao>()

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

  singleOf(::AppSettingRepositoryImpl).bind<AppSettingRepository>()
  singleOf(::UpsertAppSetting)
  singleOf(::GetAppSettings)
  singleOf(::GetAppSetting)

  viewModelOf(::NavigationViewModel)
  viewModelOf(::ScreenMainViewModel)
  viewModelOf(::ScreenLocalVsComputerViewModel)
}
