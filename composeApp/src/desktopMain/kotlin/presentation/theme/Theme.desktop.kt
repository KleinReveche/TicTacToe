package presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun TicTacToeTheme(
  dynamicColorAndroid: Boolean,
  uiColorTypes: UIColorTypes,
  darkTheme: Boolean,
  oled: Boolean,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    if (darkTheme) darkColorScheme(uiColorTypes, oled) else lightColorScheme(uiColorTypes)
  MaterialTheme(colorScheme = colorScheme, content = content)
}
