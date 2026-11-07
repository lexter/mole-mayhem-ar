// Created by Lexter Labra

// Models, board-frame maths, SessionClock, serialization. NO platform imports.
plugins {
    id("mole.kmp.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization.json)
        }
    }
}
