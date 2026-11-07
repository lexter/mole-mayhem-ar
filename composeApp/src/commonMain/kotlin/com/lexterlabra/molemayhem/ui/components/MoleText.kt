// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.lexterlabra.molemayhem.ui.theme.MoleTheme

/**
 * Text drawn with a [MoleTheme] style. [color] defaults to the forest-green `onSurface` token.
 */
@Composable
fun MoleText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = MoleTheme.colors.onSurface,
    textAlign: TextAlign = TextAlign.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = color, textAlign = textAlign),
        maxLines = maxLines,
        overflow = if (maxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis,
    )
}
