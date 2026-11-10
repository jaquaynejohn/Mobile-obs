
package com.mobileobs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MobileOBSColors = darkColorScheme(
    primary = Color(0xFF7657FF),
    secondary = Color(0xFF9B85FF),
    background = Color(0xFF101116),
    surface = Color(0xFF1B1D25),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MobileOBSTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MobileOBSColors,
        content = content
    )
}
