import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.icon

fun main() {
  initKoin()

  application {
    Window(
      onCloseRequest = ::exitApplication,
      title = "TicTacToe",
      icon = painterResource(Res.drawable.icon),
    ) { App() }
  }
}
