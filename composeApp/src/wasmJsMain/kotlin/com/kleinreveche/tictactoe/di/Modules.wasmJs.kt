package com.kleinreveche.tictactoe.di

import com.kleinreveche.tictactoe.data.repository.AppSettingRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.GameDataRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.LocalMatchRepositoryImpl
import com.kleinreveche.tictactoe.data.repository.LocalPlayerRepositoryImpl
import com.kleinreveche.tictactoe.data.sources.localStorage
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
import com.kleinreveche.tictactoe.domain.repository.GameDataRepository
import com.kleinreveche.tictactoe.domain.repository.LocalMatchRepository
import com.kleinreveche.tictactoe.domain.repository.LocalPlayerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.w3c.dom.Storage

actual val ticTacToeDatabasePlatformModule = module {
    single { localStorage }.bind<Storage>()

    singleOf(::AppSettingRepositoryImpl).bind<AppSettingRepository>()
    singleOf(::GameDataRepositoryImpl).bind<GameDataRepository>()
    singleOf(::LocalMatchRepositoryImpl).bind<LocalMatchRepository>()
    singleOf(::LocalPlayerRepositoryImpl).bind<LocalPlayerRepository>()
}
