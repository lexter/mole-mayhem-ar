// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.ui.theme.MoleDimens
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/**
 * White pill with the player's initial in an orange avatar and their nickname.
 *
 * @param accessibilityLabel Spoken label for the whole chip.
 */
@Composable
fun ProfileChip(
    nickname: String,
    accessibilityLabel: String,
    modifier: Modifier = Modifier,
) {
    val colors = MoleTheme.colors
    Row(
        modifier =
            modifier
                .heightIn(min = MoleDimens.MinTouchTarget)
                .background(colors.surface, MoleTheme.shapes.pill)
                .padding(start = 6.dp, top = 6.dp, end = 14.dp, bottom = 6.dp)
                .clearAndSetSemantics { contentDescription = accessibilityLabel },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(32.dp).background(colors.primary, MoleTheme.shapes.pill),
            contentAlignment = Alignment.Center,
        ) {
            MoleText(text = nickname.take(1).uppercase(), style = MoleTheme.typography.avatar)
        }
        MoleText(
            text = nickname,
            style = MoleTheme.typography.body.copy(fontWeight = MoleTheme.typography.label.fontWeight),
            maxLines = 1,
        )
    }
}
