import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import di.initKoin
import kotlinx.browser.document

external fun onLoadFinished()

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onLoadFinished()
    ComposeViewport(document.body!!) {
        TicTacToeApp()
    }
}
