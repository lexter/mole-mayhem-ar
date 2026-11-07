// Created by Lexter Labra

// Android entry point. AGP 9 requires it to live in its own module, separate from the KMP library modules.
// Kotlin support is built into AGP 9, so no Kotlin Android plugin is applied.
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("mole.lint")
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.foundation)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
}

android {
    namespace = "com.lexterlabra.molemayhem"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "com.lexterlabra.molemayhem"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "0.1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(
            JvmTarget.fromTarget(
                libs.versions.jvm.target
                    .get(),
            ),
        )
    }
}
