import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.icon

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "TicTacToe",
    icon = painterResource(Res.drawable.icon),
  ) { App() }
}
