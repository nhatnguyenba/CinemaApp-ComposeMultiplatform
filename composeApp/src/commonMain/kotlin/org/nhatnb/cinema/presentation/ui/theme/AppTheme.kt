package org.nhatnb.cinema.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dark Color Scheme cho Cinema App
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryRed,
    onPrimary = Color.White,
    primaryContainer = PrimaryRed.copy(alpha = 0.2f),
    onPrimaryContainer = PrimaryRed,

    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF03DAC6).copy(alpha = 0.2f),
    onSecondaryContainer = Color(0xFF03DAC6),

    background = DarkBackground,
    onBackground = TextPrimary,

    surface = CardBackground,
    onSurface = TextPrimary,

    surfaceVariant = CategoryChipColor,
    onSurfaceVariant = TextSecondary,

    error = Color(0xFFCF6679),
    onError = Color.White
)

// Light Color Scheme (tùy chọn, có thể chỉ dùng dark theme)
private val LightColorScheme = lightColorScheme(
    primary = PrimaryRed,
    onPrimary = Color.White,
    primaryContainer = PrimaryRed.copy(alpha = 0.1f),
    onPrimaryContainer = PrimaryRed,

    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,

    background = Color.White,
    onBackground = Color.Black,

    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF666666),

    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // Mặc định dùng dark theme cho cinema app
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CinemaTypography,
        shapes = AppShapes,
        content = content
    )
}