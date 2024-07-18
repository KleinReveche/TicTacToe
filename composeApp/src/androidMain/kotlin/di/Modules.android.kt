package di

import data.buildTicTacToeDb
import org.koin.core.module.Module
import org.koin.dsl.module

actual val ticTacToePlatformModule: Module = module { single { buildTicTacToeDb(get()) } }
