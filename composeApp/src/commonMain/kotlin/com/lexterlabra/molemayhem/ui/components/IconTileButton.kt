// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.theme.MoleDimens
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/** A 44 dp white rounded tile holding a single icon (for example Settings or Back). */
@Composable
fun IconTileButton(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = MoleTheme.shapes.tile
    Box(
        modifier =
            modifier
                .size(MoleDimens.MinTouchTarget)
                .clip(shape)
                .background(MoleTheme.colors.surface)
                .clickable(onClickLabel = contentDescription, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(MoleTheme.colors.onSurface),
        )
    }
}
