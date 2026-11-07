// Created by Lexter Labra
package com.lexterlabra.molemayhem.net

import kotlinx.coroutines.flow.Flow

/**
 * Finds and announces game lobbies on the local network using DNS-SD (Bonjour/NSD), service type
 * [SERVICE_TYPE]. There is no UDP broadcast or multicast in app code (see docs/plan/PROTOCOL.md, "Discovery").
 *
 * Platform implementations:
 * - Android: `NsdManager`.
 * - iOS: `NWListener` (advertise) and `NWBrowser` (browse) from Network.framework. Requires
 *   `NSBonjourServices` and `NSLocalNetworkUsageDescription` in Info.plist.
 *
 * Both operations are cold flows: work starts on collection and stops when the collector is cancelled, so their
 * lifetime follows structured concurrency.
 */
public interface LobbyDiscovery {
    /**
     * Advertises [advert] while collected. Emits the service name the OS actually registered (it may add a suffix
     * to resolve a name conflict).
     */
    public fun advertise(advert: LobbyAdvert): Flow<String>

    /**
     * Browses for lobbies while collected and emits the full current list each time it changes. Entries whose
     * protocol version differs from the app's are filtered out by the implementation.
     */
    public fun browse(): Flow<List<DiscoveredLobby>>

    public companion object {
        /** DNS-SD service type. Must match `NSBonjourServices` in the iOS Info.plist. */
        public const val SERVICE_TYPE: String = "_molemayhem._tcp"
    }
}

/**
 * What a host advertises. Becomes the DNS-SD TXT record (`pv`, `name`, `mode`, `n`, `max`).
 *
 * @property protocolVersion Wire protocol version (`pv`).
 * @property lobbyName Display name of the lobby (`name`).
 * @property mode Game mode identifier (`mode`).
 * @property playerCount Players currently joined (`n`).
 * @property maxPlayers Maximum players (`max`).
 * @property port TCP port the host is listening on (OS-assigned).
 */
public data class LobbyAdvert(
    val protocolVersion: Int,
    val lobbyName: String,
    val mode: String,
    val playerCount: Int,
    val maxPlayers: Int,
    val port: Int,
)

/**
 * A lobby found while browsing. All fields come from the network, so they are untrusted until validated.
 *
 * @property serviceName The DNS-SD service instance name (stable key for the list).
 * @property host Resolved host address as text (IPv4 or IPv6).
 * @property port Resolved TCP port.
 * @property advert The decoded TXT record.
 */
public data class DiscoveredLobby(
    val serviceName: String,
    val host: String,
    val port: Int,
    val advert: LobbyAdvert,
)
