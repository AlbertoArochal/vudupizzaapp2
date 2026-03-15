package com.example.pizzeria2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BuonGreen,
    onPrimary = BuonWhite,
    secondary = BuonRed,
    onSecondary = BuonWhite,
    background = BuonDarkBackground,
    onBackground = BuonWhite,
    surface = BuonDarkSurface,
    onSurface = BuonWhite,
    primaryContainer = BuonDarkPrimaryContainer,
    onPrimaryContainer = BuonWhite,
    error = BuonRed,
    onError = BuonWhite
)

private val LightColorScheme = lightColorScheme(
    primary = BuonGreen,
    onPrimary = BuonWhite,
    secondary = BuonRed,
    onSecondary = BuonWhite,
    background = BuonCream,
    onBackground = BuonTextDark,
    surface = BuonCream,
    onSurface = BuonTextDark,
    primaryContainer = BuonGreenSoft,
    onPrimaryContainer = BuonTextDark,
    error = BuonRed,
    onError = BuonWhite
)

@Composable
fun PizzeriaPanucciTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}