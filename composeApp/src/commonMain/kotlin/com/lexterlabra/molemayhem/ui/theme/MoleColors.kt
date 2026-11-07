// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * "Sunny Park Arcade" colour tokens (docs/design/UI_CONCEPT.md, "Tokens").
 *
 * This file is the only place in the UI where colour literals are allowed. Screens and components read colours
 * through [MoleTheme.colors].
 */
@Immutable
data class MoleColors(
    /** Meadow screen background. */
    val background: Color,
    /** Cards, chips, inactive buttons. */
    val surface: Color,
    /** Forest-green text and icons. Also the text colour on [primary] and [go]. */
    val onSurface: Color,
    /** Captions and labels (4.5:1 on [background]). */
    val textSecondary: Color,
    /** Card borders, secondary button borders. */
    val outline: Color,
    /** Anything you press or score. */
    val primary: Color,
    /** The arcade button's solid drop edge under [primary]. */
    val primaryEdge: Color,
    /** Ready, safe, locked, combo. */
    val go: Color,
    /** Drop edge under [go]. */
    val goEdge: Color,
    /** Outdoor sky, illustration backgrounds. */
    val sky: Color,
    val boardDirt: Color,
    val boardRim: Color,
    val hole: Color,
    val holeRim: Color,
    /** Standard mole body. */
    val mole: Color,
    /** Mole nose (illustrations). */
    val moleNose: Color,
    /** Hard hat. Also the Co-op Defense accent. */
    val helmet: Color,
    val helmetEdge: Color,
    val golden: Color,
    val goldenGlow: Color,
    val bomb: Color,
    val bombDanger: Color,
    /** Player ring and score pill colours. Player 4 is an open design decision, so only three are defined. */
    val players: List<Color>,
)

/** The light "Sunny Park Arcade" palette. */
val SunnyParkColors: MoleColors =
    MoleColors(
        background = Color(0xFFE9F8D6),
        surface = Color(0xFFFFFFFF),
        onSurface = Color(0xFF1F3A12),
        textSecondary = Color(0xFF4E6B3A),
        outline = Color(0xFFBFE09A),
        primary = Color(0xFFFF7A1A),
        primaryEdge = Color(0xFFC4560A),
        go = Color(0xFF8EDB3F),
        goEdge = Color(0xFF5DA324),
        sky = Color(0xFFBFE6FF),
        boardDirt = Color(0xFFB98552),
        boardRim = Color(0xFF8A5E35),
        hole = Color(0xFF3A2614),
        holeRim = Color(0xFF6B4A2B),
        mole = Color(0xFF8A5A3C),
        moleNose = Color(0xFFFF8FB1),
        helmet = Color(0xFFFFD23F),
        helmetEdge = Color(0xFFC99A00),
        golden = Color(0xFFFFC83D),
        goldenGlow = Color(0xFFFFF6C2),
        bomb = Color(0xFF2E2A33),
        bombDanger = Color(0xFFFF4D4D),
        players = listOf(Color(0xFFFF7A1A), Color(0xFF2FA8FF), Color(0xFFF2559A)),
    )
