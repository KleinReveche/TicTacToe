package di

import data.buildTicTacToeDb
import org.koin.core.module.Module
import org.koin.dsl.module

actual val ticTacToeDatabasePlatformModule: Module = module {
    single { buildTicTacToeDb() }
}