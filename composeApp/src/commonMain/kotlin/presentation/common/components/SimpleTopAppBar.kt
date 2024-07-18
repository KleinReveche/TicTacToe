package presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(text: String) {
    CenterAlignedTopAppBar(
        title = { Text(text) },
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceTint),
    )
}