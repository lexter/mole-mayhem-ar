// Created by Lexter Labra

plugins {
    `kotlin-dsl`
}

// Every Gradle plugin the project uses is put on the classpath here, exactly once, at the version pinned in
// gradle/libs.versions.toml. Modules then apply plugins by id without a version.
dependencies {
    implementation(libs.gradlePlugin.kotlin)
    implementation(libs.gradlePlugin.android)
    implementation(libs.gradlePlugin.composeCompiler)
    implementation(libs.gradlePlugin.composeMultiplatform)
    implementation(libs.gradlePlugin.kotlinSerialization)
    implementation(libs.gradlePlugin.detekt)
    implementation(libs.gradlePlugin.ktlint)
    implementation(libs.gradlePlugin.kover)
}
