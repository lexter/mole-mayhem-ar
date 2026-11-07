# Mole Mayhem AR — Development Plan

Source of truth for planning. A living copy, with diagrams, is in the Claude Doc "Mole Mayhem AR —
Development Plan" (https://claude.ai/artifact/X8NRHa6LmRbx7CF7wCUjV5). Keep this file in sync
whenever scope changes.

## Summary
v1 is an offline AR whack-a-mole for iOS and Android, built over about 17 weeks by one engineer
plus a part-time 3D/audio artist. The phone is the hammer, and up to 4 players can play over a
shared Wi-Fi router or hotspot. Game rules, networking and UI are shared Kotlin. AR rendering is
native on each platform.

**In scope for v1**
- Game modes: Arcade Solo, Whack-Off (Versus, 2–4 players) and Co-op Defense.
- Phones and tablets on both platforms, with mixed iOS and Android lobbies.
- Phone-as-hammer input, with a tap-to-whack fallback for accessibility and seated play.
- Mole types: Standard (1 pt), Helmet (2 hits, 2 pts), Ninja (throws an acorn splat), Golden
  (5 pts), Bomb (−3 pts and a 2 s stun).
- Fully offline: no accounts, no backend, no analytics.

## Corrections to the earlier concept (why the architecture looks like this)
| # | Earlier idea | Problem | Decision |
|---|---|---|---|
| 1 | Host broadcasts its anchor position and rotation, and clients create the same anchor | Every AR session has its own world origin, so the host's coordinates mean nothing to a client | Printed Mole Mat image marker. The board frame is the marker pose. Cloud Anchors need internet, and ARKit collaborative sessions are iOS-only. |
| 2 | UDP broadcast to 255.255.255.255 | On iOS 14+, broadcast and multicast need Apple's multicast entitlement, many routers drop broadcasts, and Android needs a `MulticastLock` | Bonjour/NSD for discovery, then unicast TCP |
| 3 | `java.net.*`, `System.currentTimeMillis()` and old Ktor `NetworkAddress` in shared code | JVM-only or outdated, so the code doesn't compile for iOS | Ktor multiplatform socket types, `TimeSource.Monotonic`, and a synced session clock |
| 4 | Android low-pass gravity filter with different thresholds per platform | Lags during fast swings, false strikes, different feel per platform | `TYPE_LINEAR_ACCELERATION` with gyro on Android, `CMDeviceMotion` on iOS, one shared detector |
| 5 | Raycast at the moment of the IMU spike | Motion blur and `LIMITED` tracking during the swing | Lock the aim from the last frame with good tracking before the swing. Contact mode uses a virtual hammer head 20 cm out. |
| 6 | Hits settled by packet arrival order, 20 Hz full-state snapshots | Unfair to players further from the router, wasted bandwidth | Clock sync, earliest `strikeAt` wins within a 150 ms window, moles sent as scheduled events |

## Phases (each ends at a gate with exit criteria)

### Phase 0 — Foundation and risk spikes (weeks 1–2)
- Repo and Gradle version catalog.
- KMP modules: `:core`, `:game-engine`, `:net`, `:input`, `:ar-api` and `composeApp`, plus the
  `androidApp` and `iosApp` shells.
- CI on GitHub Actions: `./gradlew check`, plus an Xcode build on a macOS runner.
- Static analysis: detekt, ktlint and SwiftLint.
- **Spike A, AR in Compose:** AR camera view with a Compose overlay and touch pass-through on both
  platforms.
- **Spike B, shared frame:** one iOS and one Android device detect a printed marker. Measure where
  a virtual pin lands on each device versus the real point.
- **Spike C, LAN:** Bonjour/NSD discovery plus a Ktor TCP echo between iOS and Android. Test on a
  home router, an Android hotspot and an iPhone Personal Hotspot.
- **Spike D, motion capture:** an app that records 100 Hz IMU and AR-pose traces to JSON, so you
  can gather labelled swings and non-swings.
- **Exit:**
  - AR and HUD work on both platforms.
  - Cross-device board drift is ≤ 2 cm at 1 m.
  - TCP round trip p95 is ≤ 30 ms on the home router.
  - At least 200 labelled traces are recorded.

### Phase 1 — Shared core and game engine (weeks 3–4)
- `:core` models: `Hole`, `Mole`, `MoleType`, `Player`, `BoardFrame`, `Pose`, `SessionClock`.
- `GameSession` state machine: Lobby → Calibrating → Countdown → Playing → Results. It is seeded
  and deterministic.
- Spawner that outputs a mole timeline (holeId, type, upAt, downAt) from the seed and difficulty
  curve.
- Rules for each mole type, scoring, combos, and hit arbitration as a pure function.
- Headless simulator that runs 10,000 seeded games for balance.
- **Exit:** ≥ 85% coverage on `:game-engine`. The same seed gives the same timeline on JVM and
  iOS.

### Phase 2 — AR layer (weeks 5–7)
- `ArSceneController` on Android (SceneView) and iOS (RealityKit, Swift).
- Placement:
  - Solo: detect a plane and tap to place.
  - Multiplayer: detect the marker and lock the board to it.
- Render holes, moles and pop/retreat animations from `BoardSceneState`, using entity pooling.
- Pause play and show guidance when tracking is `LIMITED` for more than 1 s.
- Asset pipeline: Blender source exported to GLB and USDZ. All 3D assets together must be ≤ 15 MB.
- **Exit:** a steady 60 fps on a mid-range reference device per platform, and no tracking loss in
  a 3-minute idle test.

### Phase 3 — Hammer input (weeks 8–9)
- `MotionSource` implementations at 100 Hz, with timestamps converted to the session clock.
- `StrikeDetector`:
  - Detects a downward jerk followed by a deceleration peak, debounced at 250 ms.
  - Aim mode: the aim locks before the swing.
  - Contact mode: a strike counts when the virtual hammer head crosses the board plane over a
    hole.
- Replay test suite running against the Phase 0 traces.
- 20-second per-player calibration, saved locally.
- Tap fallback, haptics and impact sound.
- **Exit:** on held-out traces, recall is ≥ 95% and there is ≤ 1 false strike per minute of
  walking.

### Phase 4 — Solo vertical slice (week 10)
- Arcade Solo end to end: menu, safety prompt, placement, countdown, play, results, local high
  scores.
- **Exit:** internal alpha on TestFlight and Play internal testing. Five testers play 10 rounds
  each with no crashes.

### Phase 5 — LAN multiplayer (weeks 11–13)
- `LobbyDiscovery` implementations, host/join flow, 4-digit PIN and roster.
- The protocol in `PROTOCOL.md`.
- Shared calibration: play starts only when every player reports `BoardLocked`.
- Other players appear in AR as markers driven by `PlayerPose`.
- Rejoin within 15 s. If the host leaves, the match ends gracefully. Host migration is out of
  scope for v1.
- Whack-Off (Versus) mode.
- **Exit:** a 4-device mixed iOS/Android match over 20 rounds with no desync. Hit-to-score p95 is
  ≤ 100 ms.

### Phase 6 — Modes, content and polish (weeks 14–15)
- Co-op Defense, plus the Ninja and Bomb behaviours.
- Particles, squash-and-stretch, screen shake, and spatial audio panning.
- Cosmetic unlocks stored locally.
- Phone and tablet layouts, plus a seated or one-handed mode.
- Accessibility: reduce motion, colour-blind-safe cues, sound and haptic cue for every event.
- **Exit:** feature-complete beta.

### Phase 7 — QA, performance and store readiness (weeks 16–17)
- Run the full device matrix, plus a 20-minute battery and thermal soak.
- iOS: `PrivacyInfo.xcprivacy`, usage strings, and App Review notes about local network use.
- Android: Data safety form declaring no data collected, and the ARCore optional/required
  setting.
- Age ratings, store assets, and a printable Mole Mat PDF bundled in the app and linked from the
  store listing.
- **Exit:** release candidates approved on both stores.

## Testing
| Layer | What | How |
|---|---|---|
| Unit | Spawner, scoring, arbitration, clock maths, frame codec | kotlin.test on JVM and the iOS simulator, with the same seeds |
| Replay | StrikeDetector | Labelled IMU and pose traces. CI fails if recall or the false-positive rate misses the targets. |
| Simulation | Balance | 10,000 seeded rounds per difficulty |
| Integration | Host ↔ clients | In-process host plus 3 clients over loopback, with injected latency and disconnects |
| Contract | Swift ArSceneController | Swift unit tests against a Kotlin-defined fixture scene |
| Device / field | AR, motion, haptics, LAN | Scripted runs on the device matrix, in 5 real rooms, on home, hotspot and café-style routers |

**Device matrix:**
- iOS: an older supported iPhone, a current iPhone, and an iPad.
- Android: a low-end and a mid-range ARCore-certified phone, a flagship phone, and an
  ARCore-certified tablet.
- Network: home router, Android hotspot, iPhone hotspot, and a router with client isolation on.

**Performance budgets:**
- 60 fps during play.
- Under 150 MB memory.
- Under 40 KB/s per client.
- Under 15% battery per 20 minutes on the reference devices.

## Risks
| Risk | Mitigation | Fallback |
|---|---|---|
| Marker alignment drifts more than 2 cm across platforms | A4 marker, re-align when the marker is seen again | "Mirrored" mode: each player gets their own board, all sharing one timeline |
| Swinging breaks tracking or causes false strikes | Aim lock, tuned detector, per-player calibration | Tap mode, always available |
| Injury or a dropped device | Safety prompt, chop gesture, hammer head stays above the table, 1.5 m clear-space check | Seated or tap mode as the default on tablets |
| Router blocks mDNS or has client isolation on | Join by IP, detect isolation and show a message | Use one player's phone as the hotspot |
| Compose/UIKit interop limits on iOS | Spike A, keep the HUD as simple flat layers | Native SwiftUI HUD on iOS |
| Thermal throttling | 90 s rounds, turn off plane detection after placement | Lower-quality preset |
| ARCore not supported on the device | `ArCoreApk.checkAvailability`, declare AR Optional | Tap-only "Tabletop 2D" mode |
| Store review asks about local network use | Clear usage strings, review notes and a demo video | — |

## Open decisions (ask the user; don't assume)
- [ ] "iPhone duo": currently read as two iPhones playing head to head.
- [ ] Is a printed Mole Mat acceptable for shared-board multiplayer?
- [ ] Minimum iOS version and Android `minSdk`.
- [ ] Maximum players: 4, or up to 6 on tablets?
- [ ] Monetisation: paid, free, or a one-time cosmetic unlock. In-app purchases are not in this
  plan.
- [ ] Art direction and the artist's availability.

## Official documentation
- Kotlin Multiplatform: https://kotlinlang.org/docs/multiplatform.html
- Compose Multiplatform: https://www.jetbrains.com/compose-multiplatform/
- Ktor sockets: https://ktor.io/docs/server-sockets.html
- kotlinx.serialization: https://kotlinlang.org/docs/serialization.html
- ARCore Augmented Images: https://developers.google.com/ar/develop/augmented-images
- SceneView (Android): https://github.com/SceneView/sceneview-android
- ARKit image detection: https://developer.apple.com/documentation/arkit/arkit_in_ios/content_anchors/detecting_images_in_an_ar_experience
- RealityKit: https://developer.apple.com/documentation/realitykit
- Android NSD: https://developer.android.com/develop/connectivity/wifi/use-nsd
- Apple Network framework: https://developer.apple.com/documentation/network
- Android motion sensors: https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion
- Core Motion: https://developer.apple.com/documentation/coremotion
- Local network privacy (TN3179): https://developer.apple.com/documentation/technotes/tn3179-understanding-local-network-privacy
