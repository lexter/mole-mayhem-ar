// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalMoleColors = staticCompositionLocalOf { SunnyParkColors }
private val LocalMoleShapes = staticCompositionLocalOf { MoleShapes() }
private val LocalMoleTypography =
    staticCompositionLocalOf<MoleTypography> { error("MoleTypography not provided: wrap content in MoleTheme {}") }

/**
 * The app's design system: a custom theme on Compose Foundation. It doesn't use Material 3 because the Compose
 * Multiplatform 1.12.2 build of Material 3 is still alpha. Read tokens through [MoleTheme.colors],
 * [MoleTheme.typography] and [MoleTheme.shapes].
 */
@Composable
fun MoleTheme(
    colors: MoleColors = SunnyParkColors,
    shapes: MoleShapes = MoleShapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMoleColors provides colors,
        LocalMoleShapes provides shapes,
        LocalMoleTypography provides rememberMoleTypography(),
        content = content,
    )
}

/** Accessors for the current [MoleTheme] tokens. */
object MoleTheme {
    val colors: MoleColors
        @Composable @ReadOnlyComposable
        get() = LocalMoleColors.current

    val typography: MoleTypography
        @Composable @ReadOnlyComposable
        get() = LocalMoleTypography.current

    val shapes: MoleShapes
        @Composable @ReadOnlyComposable
        get() = LocalMoleShapes.current
}
