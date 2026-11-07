// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.lexterlabra.molemayhem.ui.resources.Res
import com.lexterlabra.molemayhem.ui.resources.lilita_one_regular
import com.lexterlabra.molemayhem.ui.resources.nunito_black
import com.lexterlabra.molemayhem.ui.resources.nunito_bold
import com.lexterlabra.molemayhem.ui.resources.nunito_extrabold
import com.lexterlabra.molemayhem.ui.resources.nunito_semibold
import org.jetbrains.compose.resources.Font

/**
 * Type scale. Lilita One for game words (logo, buttons, scores), Nunito 600–900 for everything else.
 * Both fonts are bundled under the SIL OFL 1.1 (see THIRD_PARTY_NOTICES.md); nothing is loaded from the network.
 * Sizes are in `sp`, so menus follow the system font scale / Dynamic Type.
 */
@Immutable
data class MoleTypography(
    /** Logo word "MOLE". */
    val logoLarge: TextStyle,
    /** Logo word "MAYHEM". */
    val logoMedium: TextStyle,
    /** Small logo badge ("AR"). */
    val logoBadge: TextStyle,
    /** Primary arcade button label. */
    val button: TextStyle,
    /** Card titles (mode names). */
    val cardTitle: TextStyle,
    /** Body copy and taglines. */
    val body: TextStyle,
    /** Card subtitles and captions. */
    val caption: TextStyle,
    /** Secondary button and chip labels. */
    val label: TextStyle,
    /** Upper-case section headers ("GAME MODES"). */
    val sectionHeader: TextStyle,
    /** Avatar initials. */
    val avatar: TextStyle,
)

@Composable
internal fun rememberMoleTypography(): MoleTypography {
    val lilita = FontFamily(Font(Res.font.lilita_one_regular, FontWeight.Normal))
    val nunito =
        FontFamily(
            Font(Res.font.nunito_semibold, FontWeight.SemiBold),
            Font(Res.font.nunito_bold, FontWeight.Bold),
            Font(Res.font.nunito_extrabold, FontWeight.ExtraBold),
            Font(Res.font.nunito_black, FontWeight.Black),
        )
    return MoleTypography(
        logoLarge = TextStyle(fontFamily = lilita, fontSize = 68.sp, lineHeight = 0.9.em, letterSpacing = 1.sp),
        logoMedium = TextStyle(fontFamily = lilita, fontSize = 52.sp, lineHeight = 0.95.em),
        logoBadge = TextStyle(fontFamily = lilita, fontSize = 26.sp),
        button = TextStyle(fontFamily = lilita, fontSize = 34.sp, letterSpacing = 2.sp),
        cardTitle = TextStyle(fontFamily = lilita, fontSize = 21.sp),
        body = TextStyle(fontFamily = nunito, fontWeight = FontWeight.Bold, fontSize = 15.sp),
        caption = TextStyle(fontFamily = nunito, fontWeight = FontWeight.Bold, fontSize = 13.sp),
        label = TextStyle(fontFamily = nunito, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp),
        sectionHeader =
            TextStyle(
                fontFamily = nunito,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                letterSpacing = 1.5.sp,
            ),
        avatar = TextStyle(fontFamily = nunito, fontWeight = FontWeight.Black, fontSize = 15.sp),
    )
}
