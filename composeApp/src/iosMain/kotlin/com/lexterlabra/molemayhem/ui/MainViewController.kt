// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * iOS entry point, hosted by SwiftUI in `iosApp/iosApp/ContentView.swift`.
 *
 * Phase 2 adds an `arFactory` parameter here so the Swift RealityKit `ArSceneController` can be injected
 * (CLAUDE.md, "Architecture").
 */
@Suppress("ktlint:standard:function-naming", "FunctionNaming") // Swift-facing factory, named like the type.
fun MainViewController(): UIViewController = ComposeUIViewController { MoleMayhemApp() }
