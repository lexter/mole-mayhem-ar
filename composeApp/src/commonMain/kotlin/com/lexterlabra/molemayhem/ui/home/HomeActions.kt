// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.home

import com.lexterlabra.molemayhem.core.GameMode

/** Everything the Home screen can ask the app to do. Navigation is wired in a later phase. */
data class HomeActions(
    val onQuickPlay: () -> Unit = {},
    val onModeSelected: (GameMode) -> Unit = {},
    val onSettings: () -> Unit = {},
    val onMallets: () -> Unit = {},
    val onHowToSwing: () -> Unit = {},
    val onJoinGame: () -> Unit = {},
)
