# LAN Protocol v1

Each client holds one TCP connection to the host and sends length-prefixed JSON frames. Discovery
uses Bonjour/NSD, so there is no broadcast, no multicast entitlement and no internet.

## Discovery
- The host advertises `_molemayhem._tcp` on a port the OS assigns.
- TXT records: `pv` (protocol version), `name` (lobby name), `mode`, `n` (players joined), `max`.
- Clients list lobbies whose `pv` matches. If nothing shows up within 5 s, offer manual join by IP
  address, since some routers block mDNS.
- Android uses `NsdManager`. iOS uses `NWListener`/`NWBrowser` (Network.framework). Both sit
  behind the common `LobbyDiscovery` interface.

## Framing
- Each frame is a 4-byte big-endian length followed by a UTF-8 JSON envelope:
  `{"v":1,"seq":n,"t":"Type","tok":"<session token>","b":{...}}`
- Frames over 16 KB are rejected and the connection is closed. Turn on `TCP_NODELAY`.
- `seq` increases per sender. Drop frames whose `seq` is a duplicate or out of order.

## Messages
```kotlin
@Serializable sealed interface Msg {
    @Serializable @SerialName("Hello")       data class Hello(val pv: Int, val nick: String, val pin: String, val platform: String) : Msg
    @Serializable @SerialName("Welcome")     data class Welcome(val playerId: String, val sessionToken: String, val roster: List<PlayerInfo>) : Msg
    @Serializable @SerialName("Reject")      data class Reject(val reason: RejectReason) : Msg
    @Serializable @SerialName("Ping")        data class Ping(val t0: Long) : Msg
    @Serializable @SerialName("Pong")        data class Pong(val t0: Long, val t1: Long, val t2: Long) : Msg
    @Serializable @SerialName("BoardSpec")   data class BoardSpec(val markerId: String, val holes: List<HoleSpec>) : Msg   // board-local metres
    @Serializable @SerialName("BoardLocked") data class BoardLocked(val playerId: String) : Msg
    @Serializable @SerialName("RoundStart")  data class RoundStart(val seed: Long, val startAt: Long, val config: RoundConfig) : Msg
    @Serializable @SerialName("Spawn")       data class Spawn(val moleId: Int, val holeId: Int, val type: MoleType, val upAt: Long, val downAt: Long) : Msg
    @Serializable @SerialName("HitClaim")    data class HitClaim(val moleId: Int, val strikeAt: Long, val mode: InputMode) : Msg
    @Serializable @SerialName("HitResult")   data class HitResult(val moleId: Int, val winner: String?, val hp: Int, val scores: Map<String, Int>) : Msg
    @Serializable @SerialName("PlayerPose")  data class PlayerPose(val playerId: String, val pose: Pose) : Msg      // 10 Hz, board-local
    @Serializable @SerialName("RoundEnd")    data class RoundEnd(val scores: Map<String, Int>) : Msg
    @Serializable @SerialName("Bye")         data class Bye(val reason: String) : Msg
}
```

## Clock sync
1. Each client sends 8 `Ping` messages at join, then one every 5 s.
2. The host stamps receipt (`t1`) and send (`t2`). The client records arrival as `t3`.
3. `offset = ((t1 − t0) + (t2 − t3)) / 2`, taken from the sample with the lowest round-trip time.

All times on the wire are host session-clock milliseconds (`TimeSource.Monotonic`).

## Mole delivery
- The host sends each `Spawn` at least 300 ms before `upAt`.
- Clients animate locally on the synced clock.
- `PlayerPose` at 10 Hz is the only continuous stream.

## Hit arbitration (host, pure function in `:game-engine`)
1. Reject a claim if:
   - the mole is unknown,
   - `strikeAt` is outside [`upAt`, `downAt` + 50 ms], or
   - `strikeAt` is more than 500 ms in the future by the host clock.
2. Hold the first valid claim for a mole for 150 ms to collect competing claims.
3. The earliest `strikeAt` wins. On an exact tie, the lower round-trip time wins. Apply damage
   (Helmet takes 2 hits), then broadcast `HitResult`.
4. A Bomb hit costs 3 points and locks that player's input for 2 s.

## Security and privacy
- A 4-digit PIN shown on the host gates `Hello`. After 5 wrong PINs, block that IP address for the
  lobby's lifetime.
- After `Welcome`, every frame must carry `sessionToken`: 128 bits from a CSPRNG, never logged.
- Each client may send at most 10 `HitClaim` per second. Range-check every field.
- Nicknames are at most 16 characters, filtered, and never stored by peers.
- v1 doesn't encrypt traffic. This model stops cross-talk between lobbies and casual griefing,
  but not a hostile attacker on the same LAN. State that in the privacy policy.
- Never deserialize polymorphic types outside the sealed `Msg` hierarchy. Use
  `Json { ignoreUnknownKeys = true; explicitNulls = false }`.
- Every protocol change bumps `pv` and gets a test.
