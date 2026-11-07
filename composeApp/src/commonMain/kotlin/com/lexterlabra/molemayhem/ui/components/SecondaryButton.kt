// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/** A transparent button with a 2 dp outline, for secondary actions (Mallets, How to swing, Join game). */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = MoleTheme.shapes.secondaryButton
    Box(
        modifier =
            modifier
                .height(56.dp)
                .clip(shape)
                .border(width = 2.dp, color = MoleTheme.colors.outline, shape = shape)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        MoleText(text = text, style = MoleTheme.typography.label, textAlign = TextAlign.Center, maxLines = 2)
    }
}
