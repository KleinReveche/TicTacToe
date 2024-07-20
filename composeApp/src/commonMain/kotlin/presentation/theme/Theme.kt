package presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import presentation.theme.uicolors.Default
import presentation.theme.uicolors.Green
import presentation.theme.uicolors.Yellow

@Composable
expect fun TicTacToeTheme(
  dynamicColorAndroid: Boolean = false,
  uiColorTypes: UIColorTypes = UIColorTypes.Default,
  darkTheme: Boolean = isSystemInDarkTheme(),
  oled: Boolean = true,
  content: @Composable () -> Unit,
)

fun lightColorScheme(uiColorTypes: UIColorTypes): ColorScheme {
  val colorType =
    when (uiColorTypes) {
      UIColorTypes.Default -> Default
      UIColorTypes.Yellow -> Yellow
      UIColorTypes.Green -> Green
    }

  return lightColorScheme(
    primary = colorType.primaryLight(),
    onPrimary = colorType.onPrimaryLight(),
    primaryContainer = colorType.primaryContainerLight(),
    onPrimaryContainer = colorType.onPrimaryContainerLight(),
    inversePrimary = colorType.inversePrimaryLight(),
    secondary = colorType.secondaryLight(),
    onSecondary = colorType.onSecondaryLight(),
    secondaryContainer = colorType.secondaryContainerLight(),
    onSecondaryContainer = colorType.onSecondaryContainerLight(),
    tertiary = colorType.tertiaryLight(),
    onTertiary = colorType.onTertiaryLight(),
    tertiaryContainer = colorType.tertiaryContainerLight(),
    onTertiaryContainer = colorType.onTertiaryContainerLight(),
    background = colorType.backgroundLight(),
    onBackground = colorType.onBackgroundLight(),
    surface = colorType.surfaceLight(),
    onSurface = colorType.onSurfaceLight(),
    surfaceVariant = colorType.surfaceVariantLight(),
    onSurfaceVariant = colorType.onSurfaceVariantLight(),
    surfaceTint = colorType.primaryLight(),
    inverseSurface = colorType.inverseSurfaceLight(),
    inverseOnSurface = colorType.inverseOnSurfaceLight(),
    error = colorType.errorLight(),
    onError = colorType.onErrorLight(),
    errorContainer = colorType.errorContainerLight(),
    onErrorContainer = colorType.onErrorContainerLight(),
    outline = colorType.outlineLight(),
    outlineVariant = colorType.outlineVariantLight(),
    scrim = colorType.scrimLight(),
    surfaceBright = colorType.surfaceBrightLight(),
    surfaceContainer = colorType.surfaceContainerLight(),
    surfaceContainerHigh = colorType.surfaceContainerHighLight(),
    surfaceContainerHighest = colorType.surfaceContainerHighestLight(),
    surfaceContainerLow = colorType.surfaceContainerLowLight(),
    surfaceContainerLowest = colorType.surfaceContainerLowestLight(),
    surfaceDim = colorType.surfaceDimLight(),
  )
}

fun darkColorScheme(uiColorTypes: UIColorTypes, oledMode: Boolean): ColorScheme {
  val colorType =
    when (uiColorTypes) {
      UIColorTypes.Default -> Default
      UIColorTypes.Yellow -> Yellow
      UIColorTypes.Green -> Green
    }

  return darkColorScheme(
    primary = colorType.primaryDark(),
    onPrimary = colorType.onPrimaryDark(),
    primaryContainer = colorType.primaryContainerDark(),
    onPrimaryContainer = colorType.onPrimaryContainerDark(),
    inversePrimary = colorType.inversePrimaryDark(),
    secondary = colorType.secondaryDark(),
    onSecondary = colorType.onSecondaryDark(),
    secondaryContainer = colorType.secondaryContainerDark(),
    onSecondaryContainer = colorType.onSecondaryContainerDark(),
    tertiary = colorType.tertiaryDark(),
    onTertiary = colorType.onTertiaryDark(),
    tertiaryContainer = colorType.tertiaryContainerDark(),
    onTertiaryContainer = colorType.onTertiaryContainerDark(),
    background = if (oledMode) colorType.backgroundDark() else colorType.scrimDark(),
    onBackground = colorType.onBackgroundDark(),
    surface = if (oledMode) colorType.surfaceDark() else colorType.scrimDark(),
    onSurface = colorType.onSurfaceDark(),
    surfaceVariant = colorType.surfaceVariantDark(),
    onSurfaceVariant = colorType.onSurfaceVariantDark(),
    surfaceTint = colorType.primaryDark(),
    inverseSurface = colorType.inverseSurfaceDark(),
    inverseOnSurface = colorType.inverseOnSurfaceDark(),
    error = colorType.errorDark(),
    onError = colorType.onErrorDark(),
    errorContainer = colorType.errorContainerDark(),
    onErrorContainer = colorType.onErrorContainerDark(),
    outline = colorType.outlineDark(),
    outlineVariant = colorType.outlineVariantDark(),
    scrim = colorType.scrimDark(),
    surfaceBright = colorType.surfaceBrightDark(),
    surfaceContainer = colorType.surfaceContainerDark(),
    surfaceContainerHigh = colorType.surfaceContainerHighDark(),
    surfaceContainerHighest = colorType.surfaceContainerHighestDark(),
    surfaceContainerLow = colorType.surfaceContainerLowDark(),
    surfaceContainerLowest = colorType.surfaceContainerLowestDark(),
    surfaceDim = colorType.surfaceDimDark(),
  )
}
