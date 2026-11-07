// Created by Lexter Labra

// Static analysis for every Kotlin module: detekt + ktlint. Both are wired into `check`.
plugins {
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

detekt {
    buildUponDefaultConfig = true
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    // KMP source sets live in src/<sourceSet>/kotlin, which detekt's defaults don't cover.
    source.setFrom(fileTree("src") { include("**/kotlin/**/*.kt") })
    parallel = true
}

ktlint {
    version.set(libs.findVersion("ktlint").get().requiredVersion)
    filter {
        exclude { it.file.path.contains("/build/") }
    }
}
