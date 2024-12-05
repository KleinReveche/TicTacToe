package di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.localvscomputer.ScreenLocalVsComputerViewModel
import presentation.localvsplayer.ScreenLocalVsPlayerViewModel
import presentation.navigation.NavigationViewModel
import presentation.navigation.ScreenMainViewModel

expect val ticTacToeDatabasePlatformModule: Module

val repositorySharedModule =
    module {
    }

val viewModelSharedModule =
    module {
        viewModelOf(::NavigationViewModel)
        viewModelOf(::ScreenMainViewModel)
        viewModelOf(::ScreenLocalVsComputerViewModel)
        viewModelOf(::ScreenLocalVsPlayerViewModel)
    }
