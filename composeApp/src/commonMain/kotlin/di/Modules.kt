package di

import data.repository.AppSettingRepositoryImpl
import data.repository.GameDataRepositoryImpl
import data.repository.LocalMatchRepositoryImpl
import data.repository.LocalPlayerRepositoryImpl
import data.sources.AppSettingDao
import data.sources.GameDataDao
import data.sources.LocalMatchDao
import data.sources.PlayerVsComputerDao
import data.sources.TicTacToeDatabase
import domain.repository.AppSettingRepository
import domain.repository.GameDataRepository
import domain.repository.LocalMatchRepository
import domain.repository.LocalPlayerRepository
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.localvscomputer.ScreenLocalVsComputerViewModel
import presentation.localvsplayer.ScreenLocalVsPlayerViewModel
import presentation.navigation.NavigationViewModel
import presentation.navigation.ScreenMainViewModel

expect val ticTacToeDatabasePlatformModule: Module

val repositorySharedModule = module {
  single { get<TicTacToeDatabase>().playerVsComputerDao() }.bind<PlayerVsComputerDao>()
  single { get<TicTacToeDatabase>().gameDataDao() }.bind<GameDataDao>()
  single { get<TicTacToeDatabase>().appSettingDao() }.bind<AppSettingDao>()
  single { get<TicTacToeDatabase>().localMatchDao() }.bind<LocalMatchDao>()

  singleOf(::LocalPlayerRepositoryImpl).bind<LocalPlayerRepository>()
  singleOf(::GameDataRepositoryImpl).bind<GameDataRepository>()
  singleOf(::AppSettingRepositoryImpl).bind<AppSettingRepository>()
  singleOf(::LocalMatchRepositoryImpl).bind<LocalMatchRepository>()
}

val viewModelSharedModule = module {
  viewModelOf(::NavigationViewModel)
  viewModelOf(::ScreenMainViewModel)
  viewModelOf(::ScreenLocalVsComputerViewModel)
  viewModelOf(::ScreenLocalVsPlayerViewModel)
}
