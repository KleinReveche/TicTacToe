package di

import data.repository.AppSettingRepositoryImpl
import data.repository.GameDataRepositoryImpl
import data.repository.PlayerRepositoryImpl
import data.sources.AppSettingDao
import data.sources.GameDataDao
import data.sources.PlayerDao
import data.sources.TicTacToeDatabase
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
  singleOf(::GameDataRepositoryImpl).bind<GameDataRepository>()
  singleOf(::AppSettingRepositoryImpl).bind<AppSettingRepository>()

  viewModelOf(::NavigationViewModel)
  viewModelOf(::ScreenMainViewModel)
  viewModelOf(::ScreenLocalVsComputerViewModel)
}
