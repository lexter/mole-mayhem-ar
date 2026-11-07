// Created by Lexter Labra

// LAN wire protocol (docs/plan/PROTOCOL.md), Ktor raw TCP transport, clock sync, LobbyDiscovery interface.
plugins {
    id("mole.kmp.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.core)
            api(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.network)
        }
    }
}
