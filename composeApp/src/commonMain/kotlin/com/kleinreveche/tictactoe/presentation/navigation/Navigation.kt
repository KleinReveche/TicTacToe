package com.kleinreveche.tictactoe.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import com.kleinreveche.tictactoe.presentation.localvscomputer.ScreenLocalVsComputer
import com.kleinreveche.tictactoe.presentation.localvsplayer.ScreenLocalVsPlayer
import com.kleinreveche.tictactoe.presentation.multiplayer.ScreenMultiplayer

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val vm = koinViewModel<NavigationViewModel>()
    vm.saveDeviceIdentifier()

    NavHost(navController = navController, startDestination = ScreenMain) {
        composable<ScreenMain>(
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) { ScreenMain(navController) }
        composable<ScreenLocalVsComputer>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
        ) {
            val data = it.toRoute<ScreenLocalVsComputer>()
            vm.addVsComputerPlayer(data)
            ScreenLocalVsComputer(data, navController)
        }
        composable<ScreenLocalVsPlayer>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
        ) {
            val data = it.toRoute<ScreenLocalVsPlayer>()
            vm.addVsPlayerPlayers(data)
            ScreenLocalVsPlayer(data, navController)
        }
        composable<ScreenMultiplayer>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
        ) {
            ScreenMultiplayer(navController)
        }
    }
}
