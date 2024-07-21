import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.icon

fun main() {
  initKoin()

  application {
    Window(
      onCloseRequest = ::exitApplication,
      state = rememberWindowState(width = 800.dp, height = 800.dp),
      title = "TicTacToe",
      icon = painterResource(Res.drawable.icon),
    ) {
      TicTacToeApp()
    }
  }
}
