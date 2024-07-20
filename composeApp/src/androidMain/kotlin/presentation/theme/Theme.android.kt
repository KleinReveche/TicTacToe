package presentation.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun TicTacToeTheme(
  dynamicColorAndroid: Boolean,
  uiColorTypes: UIColorTypes,
  darkTheme: Boolean,
  oled: Boolean,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && dynamicColorAndroid ->
        dynamicTheme(darkTheme, oled, LocalContext.current)
      darkTheme -> darkColorScheme(uiColorTypes, oled)
      else -> lightColorScheme(uiColorTypes)
    }

  MaterialTheme(colorScheme = colorScheme, content = content)
}

@RequiresApi(Build.VERSION_CODES.S)
private fun dynamicTheme(darkTheme: Boolean, oled: Boolean, context: Context) =
  if (darkTheme) {
    if (!oled) {
      dynamicDarkColorScheme(context).copy(surface = Color.Black, background = Color.Black)
    } else {
      dynamicDarkColorScheme(context)
    }
  } else {
    dynamicLightColorScheme(context)
  }
