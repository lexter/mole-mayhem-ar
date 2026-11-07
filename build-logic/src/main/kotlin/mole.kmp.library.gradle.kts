// Created by Lexter Labra

// Convention for the pure shared modules under shared/ (:core, :game-engine, :net, :input, :ar-api).
// Targets: Android (new KMP library plugin, required for AGP 9), JVM (headless tests + Kover), iOS device + simulator.
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.kotlinx.kover")
    id("mole.lint")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun version(alias: String): String = libs.findVersion(alias).get().requiredVersion

kotlin {
    explicitApi()

    android {
        namespace = "com.lexterlabra.molemayhem.${project.name.replace("-", "")}"
        compileSdk = version("android-compileSdk").toInt()
        minSdk = version("android-minSdk").toInt()
        // Run commonTest on the Android host JVM too, so `check` covers the Android target.
        withHostTest {}
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(version("jvm-target")))
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(version("jvm-target")))
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.findLibrary("kotlinx-coroutines-test").get())
            implementation(libs.findLibrary("turbine").get())
        }
    }
}
