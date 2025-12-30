package com.example.rapmix.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

private val DarkColorScheme = darkColorScheme(
    primary = NeonPurple,
    secondary = NeonPink,

    background = BgBottom,
    surface = CardBg,
    surfaceVariant = CardBgElevated,

    onPrimary = TextTitle,
    onSecondary = TextTitle,
    onBackground = TextBody,
    onSurface = TextBody,
)

@Composable
fun RapMixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = RapMixTypography,
        content = content
    )
}


@Composable
fun RapMixBackground(content: @Composable () -> Unit) {
    val brush = Brush.verticalGradient(listOf(BgTop, BgMid, BgBottom))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        content()
    }
}
