package com.kleinreveche.tictactoe.di

import com.kleinreveche.tictactoe.data.repository.AppSettingRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.GameDataRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.LocalMatchRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.LocalPlayerRepositoryImpl
import com.kleinreveche.tictactoe.data.sources.AppSettingDao
import com.kleinreveche.tictactoe.data.sources.GameDataDao
import com.kleinreveche.tictactoe.data.sources.LocalMatchDao
import com.kleinreveche.tictactoe.data.sources.LocalPlayerDao
import com.kleinreveche.tictactoe.data.sources.TicTacToeDatabase
import com.kleinreveche.tictactoe.data.sources.buildTicTacToeDb
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
import com.kleinreveche.tictactoe.domain.repository.GameDataRepository
import com.kleinreveche.tictactoe.domain.repository.LocalMatchRepository
import com.kleinreveche.tictactoe.domain.repository.LocalPlayerRepository
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
