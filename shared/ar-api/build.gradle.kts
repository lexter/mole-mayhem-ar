// Created by Lexter Labra

// ArSceneController and AR value types. Implemented natively: SceneView/ARCore (androidApp), RealityKit (iosApp, Swift).
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
