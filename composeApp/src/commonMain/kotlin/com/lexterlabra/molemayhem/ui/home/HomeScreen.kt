// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.lexterlabra.molemayhem.core.GameMode
import com.lexterlabra.molemayhem.ui.components.ArcadeButton
import com.lexterlabra.molemayhem.ui.components.IconTileButton
import com.lexterlabra.molemayhem.ui.components.ModeCard
import com.lexterlabra.molemayhem.ui.components.MoleText
import com.lexterlabra.molemayhem.ui.components.ProfileChip
import com.lexterlabra.molemayhem.ui.components.SecondaryButton
import com.lexterlabra.molemayhem.ui.resources.Res
import com.lexterlabra.molemayhem.ui.resources.home_game_modes
import com.lexterlabra.molemayhem.ui.resources.home_how_to_swing
import com.lexterlabra.molemayhem.ui.resources.home_join_game
import com.lexterlabra.molemayhem.ui.resources.home_mallets
import com.lexterlabra.molemayhem.ui.resources.home_profile_a11y
import com.lexterlabra.molemayhem.ui.resources.home_quick_play
import com.lexterlabra.molemayhem.ui.resources.home_settings
import com.lexterlabra.molemayhem.ui.resources.home_tagline
import com.lexterlabra.molemayhem.ui.resources.ic_hammer
import com.lexterlabra.molemayhem.ui.resources.ic_play
import com.lexterlabra.molemayhem.ui.resources.ic_settings
import com.lexterlabra.molemayhem.ui.resources.ic_shield
import com.lexterlabra.molemayhem.ui.resources.ic_versus
import com.lexterlabra.molemayhem.ui.resources.mode_arcade_solo_subtitle
import com.lexterlabra.molemayhem.ui.resources.mode_arcade_solo_title
import com.lexterlabra.molemayhem.ui.resources.mode_coop_subtitle
import com.lexterlabra.molemayhem.ui.resources.mode_coop_title
import com.lexterlabra.molemayhem.ui.resources.mode_whack_off_subtitle
import com.lexterlabra.molemayhem.ui.resources.mode_whack_off_title
import com.lexterlabra.molemayhem.ui.theme.MoleDimens
import com.lexterlabra.molemayhem.ui.theme.MoleTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Home (docs/design/screens/01-home.png): profile chip and settings, logo, QUICK PLAY, the three mode cards and
 * the secondary actions. Scrolls when the screen is shorter than the design (small phones, large font scale);
 * otherwise the secondary actions sit at the bottom.
 */
@Composable
fun HomeScreen(
    nickname: String,
    actions: HomeActions,
    modifier: Modifier = Modifier,
) {
    val colors = MoleTheme.colors
    BoxWithConstraints(
        modifier =
            modifier
                .fillMaxSize()
                .background(colors.background)
                .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        val minContentHeight = maxHeight
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = minContentHeight)
                        .padding(
                            start = MoleDimens.ScreenGutter,
                            top = 20.dp,
                            end = MoleDimens.ScreenGutter,
                            bottom = 28.dp,
                        ),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    TopBar(nickname = nickname, onSettings = actions.onSettings)
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MoleLogo()
                        MoleText(
                            text = stringResource(Res.string.home_tagline),
                            style = MoleTheme.typography.body,
                            color = colors.textSecondary,
                            modifier = Modifier.padding(top = 10.dp),
                        )
                    }
                    ArcadeButton(
                        text = stringResource(Res.string.home_quick_play),
                        onClick = actions.onQuickPlay,
                        leadingIcon = painterResource(Res.drawable.ic_play),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    GameModes(onModeSelected = actions.onModeSelected)
                }
                SecondaryActions(actions = actions, modifier = Modifier.padding(top = 20.dp))
            }
        }
    }
}

@Composable
private fun TopBar(
    nickname: String,
    onSettings: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileChip(
            nickname = nickname,
            accessibilityLabel = stringResource(Res.string.home_profile_a11y, nickname),
        )
        IconTileButton(
            icon = painterResource(Res.drawable.ic_settings),
            contentDescription = stringResource(Res.string.home_settings),
            onClick = onSettings,
        )
    }
}

@Composable
private fun GameModes(onModeSelected: (GameMode) -> Unit) {
    val colors = MoleTheme.colors
    Column(
        modifier = Modifier.padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        MoleText(
            text = stringResource(Res.string.home_game_modes),
            style = MoleTheme.typography.sectionHeader,
            color = colors.textSecondary,
            modifier = Modifier.semantics { heading() },
        )
        ModeCard(
            title = stringResource(Res.string.mode_arcade_solo_title),
            subtitle = stringResource(Res.string.mode_arcade_solo_subtitle),
            icon = painterResource(Res.drawable.ic_hammer),
            accent = colors.primary,
            onClick = { onModeSelected(GameMode.ArcadeSolo) },
        )
        ModeCard(
            title = stringResource(Res.string.mode_whack_off_title),
            subtitle = stringResource(Res.string.mode_whack_off_subtitle),
            icon = painterResource(Res.drawable.ic_versus),
            accent = colors.go,
            onClick = { onModeSelected(GameMode.WhackOff) },
        )
        ModeCard(
            title = stringResource(Res.string.mode_coop_title),
            subtitle = stringResource(Res.string.mode_coop_subtitle),
            icon = painterResource(Res.drawable.ic_shield),
            accent = colors.helmet,
            onClick = { onModeSelected(GameMode.CoopDefense) },
        )
    }
}

@Composable
private fun SecondaryActions(
    actions: HomeActions,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SecondaryButton(
            text = stringResource(Res.string.home_mallets),
            onClick = actions.onMallets,
            modifier = Modifier.weight(1f),
        )
        SecondaryButton(
            text = stringResource(Res.string.home_how_to_swing),
            onClick = actions.onHowToSwing,
            modifier = Modifier.weight(1f),
        )
        SecondaryButton(
            text = stringResource(Res.string.home_join_game),
            onClick = actions.onJoinGame,
            modifier = Modifier.weight(1f),
        )
    }
}
