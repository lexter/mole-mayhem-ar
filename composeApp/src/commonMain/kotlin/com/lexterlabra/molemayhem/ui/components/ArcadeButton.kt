// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.theme.MoleDimens
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/**
 * The big arcade button: a solid face sitting on a solid drop edge (never a gradient). Pressing sinks the face
 * onto the edge.
 *
 * @param faceHeight Height of the coloured face; the drop edge adds [MoleDimens.ArcadeEdge] below it.
 */
@Composable
fun ArcadeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ArcadeButtonStyle = ArcadeButtonStyle.Primary,
    leadingIcon: Painter? = null,
    faceHeight: Dp = 76.dp,
    enabled: Boolean = true,
) {
    val colors = MoleTheme.colors
    val shape = MoleTheme.shapes.button
    val (face, edge) =
        when (style) {
            ArcadeButtonStyle.Primary -> colors.primary to colors.primaryEdge
            ArcadeButtonStyle.Go -> colors.go to colors.goEdge
        }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val sink by animateDpAsState(
        targetValue = if (pressed) MoleDimens.ArcadeEdge - 2.dp else 0.dp,
        animationSpec = tween(durationMillis = 60),
        label = "arcadeSink",
    )

    Box(
        modifier =
            modifier
                .height(faceHeight + MoleDimens.ArcadeEdge)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
    ) {
        Box(
            Modifier
                .matchParentSize()
                .padding(top = MoleDimens.ArcadeEdge)
                .background(edge, shape),
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(faceHeight)
                    .offset(y = sink)
                    .background(face, shape),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                Image(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(colors.onSurface),
                )
            }
            MoleText(text = text, style = MoleTheme.typography.button, maxLines = 1)
        }
    }
}
