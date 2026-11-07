// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Shared bindings. Platform services (ArSceneController, MotionSource, LobbyDiscovery, Haptics) are interfaces in
 * commonMain; their implementations are bound here per platform as they are written (Phase 0 spikes onward).
 */
internal val appModule =
    module {
        // Nothing to bind yet.
    }

/**
 * Starts Koin once per process. Called from `MoleMayhemApplication.onCreate()` on Android and from the SwiftUI
 * `App` initialiser on iOS (`KoinKt.doInitKoin()`).
 */
fun initKoin(): KoinApplication =
    startKoin {
        modules(appModule)
    }
