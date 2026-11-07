// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.theme.MoleDimens
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/**
 * A white card for picking a game mode: coloured icon tile, title in Lilita One and a one-line description.
 *
 * @param accent Background of the icon tile. Pass a [MoleTheme.colors] token.
 */
@Composable
fun ModeCard(
    title: String,
    subtitle: String,
    icon: Painter,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MoleTheme.colors
    val shape = MoleTheme.shapes.card
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 68.dp)
                .clip(shape)
                .background(colors.surface)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(MoleDimens.MinTouchTarget)
                    .background(accent, MoleTheme.shapes.tile),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(colors.onSurface),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            MoleText(text = title, style = MoleTheme.typography.cardTitle)
            MoleText(text = subtitle, style = MoleTheme.typography.caption, color = colors.textSecondary)
        }
    }
}
