# Hand-off to Claude Code — Mole Mayhem AR

**Date:** 2026-11-07
**From:** planning session (Claude, Cowork)
**Owner:** Lexter Labra

## Where things stand
- Planning and the UI concept are done. **No code exists yet.** This folder holds only the docs.
- Read first:
  - `CLAUDE.md`: rules, architecture, conventions.
  - `docs/plan/DEVELOPMENT_PLAN.md`
  - `docs/plan/PROTOCOL.md`
  - `docs/design/UI_CONCEPT.md`, plus the generated screens in `docs/design/screens/` (PNG) and
    `docs/design/preview/` (HTML). See `docs/design/README.md`.
- Living copies:
  - Plan doc: https://claude.ai/artifact/X8NRHa6LmRbx7CF7wCUjV5
  - UI canvas: https://claude.ai/artifact/MkJghptizUSuAHpCMDKXXE
- The six open decisions at the end of the plan are still open. Don't hard-code an answer to any
  of them; put it behind a config value and ask.

## Your first task: Phase 0, part 1 (scaffold and CI)
Work on a branch named `phase-0/scaffold`. Check every Gradle and plugin version against the
official release notes or docs before you pin it.

1. [x] `git init` (if not already a repo) and add a `.gitignore` covering Gradle, IDE files,
       Xcode, `local.properties`, keystores and `*.jks`.
2. [x] Generate the base project the officially recommended way: the Kotlin Multiplatform wizard
       or the IDE template, targeting Android and iOS. Then reshape it into the modules in
       `CLAUDE.md`: `:core`, `:game-engine`, `:net`, `:input`, `:ar-api`, `composeApp`,
       `androidApp` and `iosApp`. If the current official template uses a different shape,
       follow the template and record that in `CLAUDE.md`.
3. [x] Set up `gradle/libs.versions.toml` with Kotlin, Compose Multiplatform, AGP, coroutines,
       serialization, Ktor network, Koin, multiplatform-settings, kotlin.test, coroutines-test
       and Turbine.
4. [x] Turn on `explicitApi()` in the shared modules. Add detekt and ktlint, and SwiftLint in
       `iosApp`.
5. [x] Add placeholder interfaces with KDoc, and no implementation yet:
       - `ArSceneController`, `MotionSource`, `LobbyDiscovery`, `Haptics`
       - `SessionClock`, `Pose`, `BoardFrame`
6. [x] Add `MoleTheme` in `composeApp` from the tokens in `UI_CONCEPT.md`, with the bundled
       fonts and the OFL license file.
       Check it against `docs/design/screens/01-home.png` by building a placeholder Home screen.
7. [x] Add a GitHub Actions workflow that runs `./gradlew check` on Ubuntu, plus an iOS
       simulator build and `allTests` on macOS.
8. [x] Add the platform config listed in `CLAUDE.md`: Info.plist keys, `PrivacyInfo.xcprivacy`,
       Android manifest permissions and the ARCore meta-data.
9. [x] Update the "Commands" section of `CLAUDE.md` with the commands that really work.

**Done when:**
- `./gradlew check` passes locally and in CI.
- The Android debug build installs.
- The iOS app builds in Xcode and shows a themed placeholder screen.
- Nothing is committed until the user asks.

## Next tasks (Phase 0, part 2: the spikes)
Do these in this order. Spike C and Spike D can run without the AR work.
- **Spike C, LAN:**
  - Bonjour/NSD advertising and browsing behind `LobbyDiscovery`.
  - Ktor TCP echo with the frame codec from `PROTOCOL.md`.
  - Measure round-trip p50 and p95 between an iPhone and an Android phone.
- **Spike D, motion recorder:**
  - 100 Hz IMU recorder on both platforms that writes JSON traces to `testdata/motion/`.
  - Include a labelling UI: swing, walk, reposition.
- **Spike A, AR in Compose:** AR view with the Compose HUD overlay on both platforms. Check touch
  pass-through.
- **Spike B, shared frame:** detect the Mole Mat on iOS and Android, and measure cross-device drift
  at 1 m. Write the result in `docs/spikes/`.

Each spike ends with a short write-up in `docs/spikes/<name>.md`: what was measured, the result
against the Phase 0 exit criteria, and go or no-go.

## Things that will bite you
- The iOS Simulator has no ARKit world tracking, so AR testing needs physical devices. The Android
  emulator's ARCore virtual scene is fine for smoke tests only.
- iOS shows a Local Network permission prompt on first discovery. Without
  `NSBonjourServices`, browsing fails silently.
- Don't use UDP broadcast or multicast, and don't use Cloud Anchors. See "Corrections" in the plan.
- RealityKit can't be called from Kotlin/Native. Write the iOS AR controller in Swift and inject
  it.
- Never use `java.*` or `System.currentTimeMillis()` in `commonMain`.

## Kickoff prompt (paste into Claude Code from this folder)
> Read CLAUDE.md and HANDOFF.md, then the three docs they point to, and look at the PNGs in
> docs/design/screens/. Before you write code, list
> the module structure and the dependency versions you plan to use, each with a link to the
> official release page that confirms it's the latest stable version, and wait for my OK. Then
> do "Your first task: Phase 0, part 1", ticking the checklist in HANDOFF.md as you go. Don't
> commit until I ask.
