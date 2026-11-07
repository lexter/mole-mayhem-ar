// Created by Lexter Labra

// Plugins are versioned once, in gradle/libs.versions.toml, and put on the classpath by build-logic.
// Requesting one build-logic plugin here (not applied) loads that classpath into the root project, so every
// module can apply AGP, Kotlin, Compose, detekt, ktlint and Kover by plain id, without a version.
plugins {
    id("mole.lint") apply false
}
