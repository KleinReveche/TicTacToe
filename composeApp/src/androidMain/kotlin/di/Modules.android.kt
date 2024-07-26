package di

import data.repository.AppSettingRepositoryImpl
import data.repository.GameDataRepositoryImpl
import data.repository.LocalMatchRepositoryImpl
import data.repository.LocalPlayerRepositoryImpl
import data.sources.AppSettingDao
import data.sources.GameDataDao
import data.sources.LocalMatchDao
import data.sources.LocalPlayerDao
import data.sources.TicTacToeDatabase
import data.sources.buildTicTacToeDb
import domain.repository.AppSettingRepository
import domain.repository.GameDataRepository
import domain.repository.LocalMatchRepository
import domain.repository.LocalPlayerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val ticTacToeDatabasePlatformModule =
    module {
        single { buildTicTacToeDb(get()) }
        single { get<TicTacToeDatabase>().localPlayerDao() }.bind<LocalPlayerDao>()
        single { get<TicTacToeDatabase>().gameDataDao() }.bind<GameDataDao>()
        single { get<TicTacToeDatabase>().appSettingDao() }.bind<AppSettingDao>()
        single { get<TicTacToeDatabase>().localMatchDao() }.bind<LocalMatchDao>()

        singleOf(::LocalPlayerRepositoryImpl).bind<LocalPlayerRepository>()
        singleOf(::GameDataRepositoryImpl).bind<GameDataRepository>()
        singleOf(::AppSettingRepositoryImpl).bind<AppSettingRepository>()
        singleOf(::LocalMatchRepositoryImpl).bind<LocalMatchRepository>()
    }
