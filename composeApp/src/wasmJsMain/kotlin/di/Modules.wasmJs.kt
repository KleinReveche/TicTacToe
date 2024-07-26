package di

import data.repository.AppSettingRepositoryTempImpl
import data.repository.GameDataRepositoryTempImpl
import data.repository.LocalMatchRepositoryTempImpl
import data.repository.LocalPlayerRepositoryTempImpl
import data.sources.tempAppSettings
import data.sources.tempGameData
import data.sources.tempLocalMatches
import data.sources.tempLocalPlayers
import domain.model.AppSetting
import domain.model.GameData
import domain.model.LocalMatch
import domain.model.LocalPlayer
import domain.repository.AppSettingRepository
import domain.repository.GameDataRepository
import domain.repository.LocalMatchRepository
import domain.repository.LocalPlayerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val ticTacToeDatabasePlatformModule = module {
    single { tempAppSettings }.bind<List<AppSetting>>()
    single { tempGameData }.bind<List<GameData>>()
    single { tempLocalMatches }.bind<List<LocalMatch>>()
    single { tempLocalPlayers }.bind<List<LocalPlayer>>()

    singleOf(::LocalPlayerRepositoryTempImpl).bind<LocalPlayerRepository>()
    singleOf(::GameDataRepositoryTempImpl).bind<GameDataRepository>()
    singleOf(::AppSettingRepositoryTempImpl).bind<AppSettingRepository>()
    singleOf(::LocalMatchRepositoryTempImpl).bind<LocalMatchRepository>()
}
