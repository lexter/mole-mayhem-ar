// Created by Lexter Labra

// MotionSource interface, StrikeDetector (pure), calibration.
plugins {
    id("mole.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.core)
            api(libs.kotlinx.coroutines.core)
        }
    }
}
