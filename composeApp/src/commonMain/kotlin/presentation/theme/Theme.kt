package presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun TicTacToeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(colorScheme = colorScheme, content = content)
}

private val lightColorScheme =
    lightColorScheme(
        primary = lightPrimary,
        onPrimary = lightOnPrimary,
        primaryContainer = lightPrimaryContainer,
        onPrimaryContainer = lightOnPrimaryContainer,
        secondary = lightSecondary,
        onSecondary = lightOnSecondary,
        secondaryContainer = lightSecondaryContainer,
        onSecondaryContainer = lightOnSecondaryContainer,
        tertiary = lightTertiary,
        onTertiary = lightOnTertiary,
        tertiaryContainer = lightTertiaryContainer,
        onTertiaryContainer = lightOnTertiaryContainer,
        error = lightError,
        errorContainer = lightErrorContainer,
        onError = lightOnError,
        onErrorContainer = lightOnErrorContainer,
        background = lightBackground,
        onBackground = lightOnBackground,
        surface = lightSurface,
        onSurface = lightOnSurface,
        surfaceVariant = lightSurfaceVariant,
        onSurfaceVariant = lightOnSurfaceVariant,
        outline = lightOutline,
        inverseOnSurface = lightInverseOnSurface,
        inverseSurface = lightInverseSurface,
        inversePrimary = lightInversePrimary,
        surfaceTint = lightSurfaceTint,
        outlineVariant = lightOutlineVariant,
        scrim = lightScrim,
    )

private val darkColorScheme =
    darkColorScheme(
        primary = darkPrimary,
        onPrimary = darkOnPrimary,
        primaryContainer = darkPrimaryContainer,
        onPrimaryContainer = darkOnPrimaryContainer,
        secondary = darkSecondary,
        onSecondary = darkOnSecondary,
        secondaryContainer = darkSecondaryContainer,
        onSecondaryContainer = darkOnSecondaryContainer,
        tertiary = darkTertiary,
        onTertiary = darkOnTertiary,
        tertiaryContainer = darkTertiaryContainer,
        onTertiaryContainer = darkOnTertiaryContainer,
        error = darkError,
        errorContainer = darkErrorContainer,
        onError = darkOnError,
        onErrorContainer = darkOnErrorContainer,
        background = darkScrim,
        onBackground = darkOnBackground,
        surface = darkScrim,
        onSurface = darkOnSurface,
        surfaceVariant = darkSurfaceVariant,
        onSurfaceVariant = darkOnSurfaceVariant,
        outline = darkOutline,
        inverseOnSurface = darkInverseOnSurface,
        inverseSurface = darkInverseSurface,
        inversePrimary = darkInversePrimary,
        surfaceTint = darkSurfaceTint,
        outlineVariant = darkOutlineVariant,
        scrim = darkBackground,
    )
