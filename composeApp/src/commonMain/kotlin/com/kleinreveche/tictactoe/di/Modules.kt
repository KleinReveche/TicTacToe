package com.kleinreveche.tictactoe.di

import com.kleinreveche.tictactoe.data.KtorRealtimeMessagingClient
import com.kleinreveche.tictactoe.data.RealtimeMessagingClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.kleinreveche.tictactoe.presentation.localvscomputer.ScreenLocalVsComputerViewModel
import com.kleinreveche.tictactoe.presentation.localvsplayer.ScreenLocalVsPlayerViewModel
import com.kleinreveche.tictactoe.presentation.multiplayer.ScreenMultiplayerViewModel
import com.kleinreveche.tictactoe.presentation.navigation.NavigationViewModel
import com.kleinreveche.tictactoe.presentation.navigation.ScreenMainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

expect val ticTacToeDatabasePlatformModule: Module

val repositorySharedModule =
    module {
        single { HttpClient { install(WebSockets) } }.bind<HttpClient>()

        singleOf(::KtorRealtimeMessagingClient).bind<RealtimeMessagingClient>()
    }

val viewModelSharedModule =
    module {
        viewModelOf(::NavigationViewModel)
        viewModelOf(::ScreenMainViewModel)
        viewModelOf(::ScreenLocalVsComputerViewModel)
        viewModelOf(::ScreenLocalVsPlayerViewModel)
        viewModelOf(::ScreenMultiplayerViewModel)
    }
