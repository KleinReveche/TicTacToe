package com.kleinreveche.tictactoe.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.kleinreveche.tictactoe.presentation.localvscomputer.ScreenLocalVsComputerViewModel
import com.kleinreveche.tictactoe.presentation.localvsplayer.ScreenLocalVsPlayerViewModel
import com.kleinreveche.tictactoe.presentation.navigation.NavigationViewModel
import com.kleinreveche.tictactoe.presentation.navigation.ScreenMainViewModel

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
