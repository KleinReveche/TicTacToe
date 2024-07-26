package di

import data.repository.AppSettingRepositoryImpl
import data.repository.GameDataRepositoryImpl
import data.repository.LocalMatchRepositoryImpl
import data.repository.LocalPlayerRepositoryImpl
import data.sources.localStorage
import domain.repository.AppSettingRepository
import domain.repository.GameDataRepository
import domain.repository.LocalMatchRepository
import domain.repository.LocalPlayerRepository
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
