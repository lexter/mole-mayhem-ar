// Created by Lexter Labra

// GameSession state machine, seeded spawner, scoring, hit arbitration. Pure and deterministic.
plugins {
    id("mole.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

// CLAUDE.md: aim for >= 85% line coverage on :game-engine. Enforced by `koverVerify` (part of `check`).
kover {
    reports {
        verify {
            rule {
                minBound(85)
            }
        }
    }
}
