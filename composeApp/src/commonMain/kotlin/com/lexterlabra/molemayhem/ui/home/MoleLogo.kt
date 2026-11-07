// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.components.MoleText
import com.lexterlabra.molemayhem.ui.resources.Res
import com.lexterlabra.molemayhem.ui.resources.home_title_a11y
import com.lexterlabra.molemayhem.ui.resources.home_title_ar
import com.lexterlabra.molemayhem.ui.resources.home_title_mayhem
import com.lexterlabra.molemayhem.ui.resources.home_title_mole
import com.lexterlabra.molemayhem.ui.theme.MoleTheme
import org.jetbrains.compose.resources.stringResource

/** "MOLE MAYHEM AR" wordmark with the mole popping out of its hole. Read by screen readers as one heading. */
@Composable
fun MoleLogo(modifier: Modifier = Modifier) {
    val colors = MoleTheme.colors
    val type = MoleTheme.typography
    val title = stringResource(Res.string.home_title_a11y)
    val shadowOffsetPx = with(LocalDensity.current) { 4.dp.toPx() }

    Column(
        modifier =
            modifier.clearAndSetSemantics {
                contentDescription = title
                heading()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        MoleInHole(Modifier.size(width = 150.dp, height = 128.dp))
        MoleText(
            text = stringResource(Res.string.home_title_mole),
            style =
                type.logoLarge.copy(
                    shadow = Shadow(color = colors.onSurface, offset = Offset(0f, shadowOffsetPx), blurRadius = 0f),
                ),
            color = colors.primary,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MoleText(text = stringResource(Res.string.home_title_mayhem), style = type.logoMedium)
            MoleText(
                text = stringResource(Res.string.home_title_ar),
                style = type.logoBadge,
                modifier =
                    Modifier
                        .rotate(-8f)
                        .background(colors.go, MoleTheme.shapes.badge)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
            )
        }
    }
}

/**
 * The mole illustration, drawn in a 150 × 128 design box (coordinates from docs/design/preview/01-home.html) and
 * scaled to whatever size it is given.
 */
@Composable
private fun MoleInHole(modifier: Modifier = Modifier) {
    val colors = MoleTheme.colors
    Canvas(modifier) {
        val unit = size.width / DESIGN_WIDTH

        fun u(value: Float) = value * unit

        // Body: 80 × 92, rounded 40 on top and 18 at the bottom.
        drawTopBottomRoundRect(
            bounds = Rect(Offset(u(35f), u(6f)), Size(u(80f), u(92f))),
            topRadius = u(40f),
            bottomRadius = u(18f),
            color = colors.mole,
        )
        // Eyes, nose, teeth.
        drawOval(colors.onSurface, topLeft = Offset(u(54f), u(34f)), size = Size(u(12f), u(14f)))
        drawOval(colors.onSurface, topLeft = Offset(u(84f), u(34f)), size = Size(u(12f), u(14f)))
        drawOval(colors.moleNose, topLeft = Offset(u(67f), u(52f)), size = Size(u(16f), u(11f)))
        drawTopBottomRoundRect(
            bounds = Rect(Offset(u(63f), u(66f)), Size(u(24f), u(10f))),
            topRadius = 0f,
            bottomRadius = u(12f),
            color = colors.surface,
        )
        // Hole with a 4-unit rim, drawn over the bottom of the body.
        drawOval(colors.holeRim, topLeft = Offset(0f, u(84f)), size = Size(u(150f), u(40f)))
        drawOval(colors.hole, topLeft = Offset(u(4f), u(88f)), size = Size(u(142f), u(32f)))
    }
}

private const val DESIGN_WIDTH = 150f

/** Fills [bounds] with one corner radius for the top corners and another for the bottom corners. */
private fun DrawScope.drawTopBottomRoundRect(
    bounds: Rect,
    topRadius: Float,
    bottomRadius: Float,
    color: Color,
) {
    val path =
        Path().apply {
            addRoundRect(
                RoundRect(
                    rect = bounds,
                    topLeft = CornerRadius(topRadius),
                    topRight = CornerRadius(topRadius),
                    bottomRight = CornerRadius(bottomRadius),
                    bottomLeft = CornerRadius(bottomRadius),
                ),
            )
        }
    drawPath(path, color)
}
