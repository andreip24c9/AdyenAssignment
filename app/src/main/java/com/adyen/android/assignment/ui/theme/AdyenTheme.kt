package com.adyen.android.assignment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColorScheme(
    primary = OffWhite,
    secondary = AccentLight,
    tertiary = AccentLight,
    surface = OffWhite,
    onSurface = ContentLight,
    onPrimary = ContentLight,
    onSecondary = ContentLight,
    background = OffWhite,
    onBackground = ContentLight
)

private val DarkThemeColors = darkColorScheme(
    primary = OffBlack,
    secondary = AccentDark,
    tertiary = AccentDark,
    surface = OffBlack,
    onSurface = ContentDark,
    onPrimary = ContentDark,
    onSecondary = ContentDark,
    background = OffBlack,
    onBackground = ContentDark
)

@Composable
fun AdyenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(colorScheme =  if (darkTheme) DarkThemeColors else LightThemeColors) {
        content()
    }
}