package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlueLight,
    onPrimary = Navy900,
    primaryContainer = Navy700,
    onPrimaryContainer = SkyBlue,
    secondary = GoldAccent,
    onSecondary = Navy900,
    secondaryContainer = Navy800,
    onSecondaryContainer = GoldLight,
    tertiary = EmeraldSuccess,
    background = DarkBg,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = Navy800,
    onPrimary = Color.White,
    primaryContainer = SkyBlue,
    onPrimaryContainer = Navy900,
    secondary = ElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = SlateLightBg,
    onSecondaryContainer = Navy800,
    tertiary = GoldAccent,
    background = SlateLightBg,
    onBackground = SlateTextPrimary,
    surface = SlateCardBg,
    onSurface = SlateTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SlateTextSecondary,
    outline = SlateBorder
)

@Composable
fun DailyDriveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    DailyDriveTheme(darkTheme = darkTheme, content = content)
}

