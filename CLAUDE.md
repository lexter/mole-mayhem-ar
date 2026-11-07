# Mole Mayhem AR — Claude Code project guide

Offline augmented-reality whack-a-mole for iOS and Android (phones and tablets), built with
Kotlin Multiplatform + Compose Multiplatform. The player swings the phone like a hammer. Up to 4
players can play together over a shared Wi-Fi router or hotspot. No internet, no backend, no
accounts.

Read these before writing code:
- `HANDOFF.md`: current status and your first task
- `docs/plan/DEVELOPMENT_PLAN.md`: phases, exit criteria, risks, open decisions
- `docs/plan/PROTOCOL.md`: LAN wire protocol (v1)
- `docs/design/UI_CONCEPT.md`: screens, design tokens, interaction rules
- `docs/design/screens/*.png` and `docs/design/preview/*.html`: the generated UI design (see
  `docs/design/README.md`)

## Working rules
- Act as a senior engineer and architect. Before you write any code that uses an API, check it
  against the official documentation for that language, SDK or library (links are in
  DEVELOPMENT_PLAN.md). Never guess at an API signature or a version number.
- Pin dependencies in `gradle/libs.versions.toml` to the latest **stable** release, checked
  against the official release pages at the time you add them. Don't use alpha or beta releases
  unless there is a documented reason.
- Follow each platform's standards: Android (Material/Compose guidelines, permissions, Play
  policies) and iOS (Human Interface Guidelines, App Store Review Guidelines, privacy manifests).
- Security and privacy come first:
  - No analytics or ad SDKs, and no network calls outside the LAN.
  - Validate every network input.
  - Never log personal data. Nicknames are the only personal data in the app.
  - Never commit secrets, signing keys, keystores or `local.properties`.
- Keep the code legal: use only properly licensed assets and libraries, and record each
  third-party license in `THIRD_PARTY_NOTICES.md`.
- Commit or push only when the user asks. Branch off `main` for any work.
- Start each new source file with the header `// Created by Lexter Labra`, matching the existing
  snippets.

## Architecture (do not drift from this without updating the plan)
```
androidApp (Kotlin)  ── ARCore + SceneView ArSceneController, NSD discovery, haptics, audio
iosApp (Swift)       ── ARKit + RealityKit ArSceneController, Bonjour (Network.framework), haptics, audio
composeApp (CMP)     ── menus, lobby, calibration, HUD drawn over the AR view, Koin DI, navigation
shared/
  :core          models, board-frame math, SessionClock, serialization. NO platform imports.
  :game-engine   GameSession state machine, seeded spawner, scoring, hit arbitration (pure)
  :net           wire protocol, Ktor raw TCP transport, clock sync, LobbyDiscovery interface
  :input         MotionSource interface, StrikeDetector (pure), calibration
  :ar-api        ArSceneController, Pose, BoardFrame, Ray, HoleHit
```
- Dependencies only point downward. `:core` and `:game-engine` must run headless in `commonTest`.
- Platform services are **interfaces in commonMain** with platform implementations bound in
  Koin. Use `expect fun` only for small factories. Avoid `expect`/`actual` classes, which are
  still Beta.
- RealityKit is Swift-only, so you can't call it from Kotlin/Native. The iOS AR controller is
  written in Swift and passed in as `MainViewController(arFactory)`. CoreMotion and ARKit's
  Objective-C APIs can be called from Kotlin if that turns out simpler, but keep that behind the
  same interfaces.
- All coordinates sent over the network are **board-local metres**. All timestamps are **host
  session-clock milliseconds**, built on `TimeSource.Monotonic`. Never use wall-clock time, and
  never use `System.currentTimeMillis()` in common code.
- No JVM-only APIs in `commonMain`. For example, use `io.ktor.network.sockets.InetSocketAddress`,
  never `java.net.InetSocketAddress`.

## Non-negotiable technical decisions (from the plan's "Corrections" section)
1. **Shared world frame:** every device detects the same printed Mole Mat image (ARKit image
   detection, ARCore Augmented Images). Don't use ARCore Cloud Anchors (they need internet), and
   don't send raw world transforms between devices.
2. **Discovery:** Bonjour/NSD service `_molemayhem._tcp`, then unicast TCP. Don't use UDP
   broadcast or multicast: on iOS 14+ it needs Apple's restricted multicast entitlement.
3. **Motion:**
   - Android: `Sensor.TYPE_LINEAR_ACCELERATION` and `TYPE_GYROSCOPE`.
   - iOS: `CMDeviceMotion.userAcceleration` and `rotationRate`.
   - Normalise both to G and rad/s, and keep the sensor timestamps.
   - One shared `StrikeDetector` with one set of thresholds.
4. **Aim:** lock the aim from the last frame with good AR tracking before the swing starts.
   Contact mode uses a virtual hammer head about 20 cm in front of the device. The phone never
   touches the table.
5. **Host-authoritative events:** the host sends a scheduled mole timeline (`Spawn` events with
   `upAt`/`downAt`), not 20 Hz state snapshots. Hits are settled by the earliest `strikeAt`
   within a 150 ms window.

## Code conventions
- Kotlin: official code style. Use `explicitApi()` in the shared library modules, immutable data
  classes, and `StateFlow` for state. Use structured concurrency: no `GlobalScope`, and no scopes
  that are never cancelled.
- Game logic is pure and deterministic. Inject `Random(seed)` and the clock, so the same seed
  gives the same result on JVM and iOS.
- Errors: throw or return typed results at module boundaries. Never swallow exceptions silently.
  Close sockets in `finally`/`use`.
- Swift: Swift concurrency, and no force unwraps in production paths.
- Lint and formatting: detekt and ktlint for Kotlin, SwiftLint for Swift. CI must pass
  `./gradlew check`.
- Tests:
  - kotlin.test, kotlinx-coroutines-test and Turbine.
  - Aim for ≥ 85% line coverage on `:game-engine`.
  - Replay tests for `StrikeDetector` use recorded traces in `testdata/motion/`.

## Commands (verified 2026-11-07 on macOS, JDK 17/21, Xcode 27)
- Build and test everything (detekt, ktlint, JVM + Android-host + iOS-simulator tests, Kover): `./gradlew check`
- Shared tests only (JVM + Android host + iOS simulator): `./gradlew allTests`
- Auto-fix Kotlin formatting: `./gradlew ktlintFormat`
- Android debug build: `./gradlew :androidApp:assembleDebug`
  (APK: `androidApp/build/outputs/apk/debug/androidApp-debug.apk`; install with
  `./gradlew :androidApp:installDebug` or `adb install -r <apk>`; verified on an arm64 emulator, API 35)
- iOS simulator build from the command line:
  `cd iosApp && xcodebuild -project iosApp.xcodeproj -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,name=iPhone 17' -derivedDataPath build/DerivedData CODE_SIGNING_ALLOWED=NO build`
  (`generic/platform=iOS Simulator` fails: the Kotlin framework is built for arm64 simulators only.)
- iOS in Xcode: open `iosApp/iosApp.xcodeproj`. Its "Compile Kotlin Framework" phase runs
  `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`. Set `TEAM_ID` in `iosApp/Configuration/Config.xcconfig`
  locally for device builds (never commit it). AR needs a **physical device**, because the iOS Simulator has no ARKit
  world tracking. On Android you can use a real device, or the emulator's ARCore virtual scene for smoke tests.
- SwiftLint: `cd iosApp && swiftlint lint --config .swiftlint.yml` (also runs as an Xcode build phase).

## Build setup notes
- Versions live only in `gradle/libs.versions.toml`. `build-logic/` (included build) puts every Gradle plugin on the
  classpath once, so modules apply plugins by id without a version. Convention plugins: `mole.kmp.library`
  (shared modules: Android + JVM + iOS targets, `explicitApi()`, test deps, Kover, lint) and `mole.lint`
  (detekt + ktlint).
- AGP is held at 9.3.x and Gradle at 9.7.0 because Kotlin 2.4.21's compatibility table stops there. detekt is 1.23.8
  (the only stable line; 2.0 is alpha). Re-check both when bumping Kotlin.
- The official KMP template (Kotlin/KMP-App-Template, AGP 9 layout) calls the shared Compose module `shared`; this
  repo names it `composeApp` and keeps the pure modules under `shared/` (`:shared:core`, `:shared:game-engine`,
  `:shared:net`, `:shared:input`, `:shared:ar-api`). `androidApp` is a separate module because AGP 9 requires the
  Android entry point outside KMP library modules. Shared modules use `com.android.kotlin.multiplatform.library`
  with the `kotlin { android { } }` block.
- `Pose`, `BoardFrame`, `Vec3`, `Quaternion` and `SessionClock` live in `:shared:core` (they are sent over the wire or
  used by the engine); `:shared:ar-api` reuses them. `Haptics` lives in `composeApp`.
- Minimum OS: Android 10 (minSdk 29), iOS 15.0. compileSdk 37, targetSdk 36.
- UI uses a custom `MoleTheme` on Compose Foundation, not Material 3 (its Compose Multiplatform build is alpha).

## Platform configuration you must not forget
- iOS `Info.plist`:
  - `NSCameraUsageDescription`
  - `NSMotionUsageDescription`
  - `NSLocalNetworkUsageDescription`
  - `NSBonjourServices = [_molemayhem._tcp]`
  - Also add `PrivacyInfo.xcprivacy`.
- Android manifest:
  - Permissions: `CAMERA`, `INTERNET`, `ACCESS_NETWORK_STATE`.
  - The ARCore `<meta-data com.google.ar.core>` value: `optional` (keeps the tap-only mode
    reachable), unless the user decides otherwise.
- Bundle the fonts (Lilita One, Nunito) as Compose resources, and include their OFL license
  text. Don't load fonts from the network at runtime.

## Design system (see docs/design/UI_CONCEPT.md for the full token list)
"Sunny Park Arcade" theme:
- Meadow background `#E9F8D6`, white cards, forest text `#1F3A12`.
- Mallet orange `#FF7A1A` for anything you press or score, grass green `#8EDB3F` for
  ready/safe/go.
- Lilita One for game words, Nunito for everything else.
- Touch targets ≥ 44 pt/dp, and text contrast ≥ 4.5:1.
