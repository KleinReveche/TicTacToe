import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.navigation.Navigation
import presentation.theme.TicTacToeTheme

@Composable
@Preview
fun App() {
    TicTacToeTheme { Navigation() }
}
