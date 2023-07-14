package com.kleinreveche.tictactoe

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.kleinreveche.tictactoe.features.local.ui.TicTacToeLocal
import com.kleinreveche.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tictactoe")

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        ticTacToeDataStore = dataStore

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeLocal(windowSizeClass, displayFeatures)
                }
            }
        }
    }

    companion object {
        fun getDataStore(): DataStore<Preferences> {
            return ticTacToeDataStore
        }

        private lateinit var ticTacToeDataStore: DataStore<Preferences>
    }
}