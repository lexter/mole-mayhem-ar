// Created by Lexter Labra

// Shared Compose UI (menus, lobby, calibration, HUD), Koin wiring, iOS entry point (MainViewController).
// Produces the static `ComposeApp` framework consumed by iosApp.
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("mole.lint")
}

kotlin {
    android {
        namespace = "com.lexterlabra.molemayhem.ui"
        compileSdk =
            libs.versions.android.compileSdk
                .get()
                .toInt()
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        compilerOptions {
            jvmTarget.set(
                JvmTarget.fromTarget(
                    libs.versions.jvm.target
                        .get(),
                ),
            )
        }
        androidResources {
            enable = true
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core)
            implementation(projects.shared.gameEngine)
            implementation(projects.shared.net)
            implementation(projects.shared.input)
            implementation(projects.shared.arApi)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.multiplatform.settings)
        }
    }
}

compose.resources {
    packageOfResClass = "com.lexterlabra.molemayhem.ui.resources"
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
