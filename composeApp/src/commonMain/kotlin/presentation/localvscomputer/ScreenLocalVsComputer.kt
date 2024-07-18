package presentation.localvscomputer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.common.components.SimpleTopAppBar
import presentation.navigation.ScreenLocalVsComputer

@Composable
fun ScreenLocalVsComputer(screenData: ScreenLocalVsComputer) {
    val (playerName, playerType, computerDifficulty) = screenData
    Scaffold(
        topBar = { SimpleTopAppBar("$playerName vs Computer") },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(playerName)
            Text(playerType)
            Text(computerDifficulty)
        }
    }
}