// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui

import androidx.compose.runtime.Composable
import com.lexterlabra.molemayhem.ui.home.HomeActions
import com.lexterlabra.molemayhem.ui.home.HomeScreen
import com.lexterlabra.molemayhem.ui.resources.Res
import com.lexterlabra.molemayhem.ui.resources.placeholder_nickname
import com.lexterlabra.molemayhem.ui.theme.MoleTheme
import org.jetbrains.compose.resources.stringResource

/** Root composable shared by Android (`MainActivity`) and iOS (`MainViewController`). */
@Composable
fun MoleMayhemApp() {
    MoleTheme {
        // Phase 0: Home only. Navigation and the real nickname arrive with the solo slice (Phase 4).
        HomeScreen(
            nickname = stringResource(Res.string.placeholder_nickname),
            actions = HomeActions(),
        )
    }
}
