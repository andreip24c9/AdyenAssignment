package com.adyen.android.assignment.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors = lightColorScheme(
    primary = OffWhite,
    secondary = AccentLight,
    tertiary = AccentLight,
    surface = OffWhite,
    onSurface = ContentLight,
    onPrimary = ContentLight,
    onSecondary = OnSecondaryLight,
    background = OffWhite,
    onBackground = Black
)

private val DarkThemeColors = darkColorScheme(
    primary = OffBlack,
    secondary = AccentDark,
    tertiary = AccentDark,
    surface = OffBlack,
    onSurface = ContentDark,
    onPrimary = ContentDark,
    onSecondary = OnSecondaryDark,
    background = OffBlack,
    onBackground = White
)

@Composable
fun AdyenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(colorScheme =  if (darkTheme) DarkThemeColors else LightThemeColors) {
        // Remember a SystemUiController
        val systemUiController = rememberSystemUiController()
        val toolbarColor = MaterialTheme.colorScheme.surface
        val useDarkIcons = !isSystemInDarkTheme()

        SideEffect {
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setSystemBarsColor(
                color = toolbarColor,
                darkIcons = useDarkIcons
            )

            // setStatusBarsColor() and setNavigationBarColor() also exist
        }

        content()
    }
}