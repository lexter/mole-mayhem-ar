// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Corner shapes from docs/design/UI_CONCEPT.md, "Shape". */
@Immutable
data class MoleShapes(
    /** Primary arcade buttons (22–24). */
    val button: Shape = RoundedCornerShape(24.dp),
    /** Cards (18–20). */
    val card: Shape = RoundedCornerShape(20.dp),
    /** Secondary outline buttons. */
    val secondaryButton: Shape = RoundedCornerShape(16.dp),
    /** Square icon tiles (settings button, mode icons). */
    val tile: Shape = RoundedCornerShape(14.dp),
    /** Small badges. */
    val badge: Shape = RoundedCornerShape(10.dp),
    /** Chips and pills: fully rounded. */
    val pill: Shape = CircleShape,
)

/** Fixed sizes shared by components. */
object MoleDimens {
    /** Minimum touch target: 44 pt (Apple HIG). Android's 48 dp is met by every Home control too. */
    val MinTouchTarget: Dp = 44.dp

    /** Height of the solid drop edge under arcade buttons. */
    val ArcadeEdge: Dp = 8.dp

    /** Screen side gutter. */
    val ScreenGutter: Dp = 20.dp
}
