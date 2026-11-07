// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

/**
 * The match clock, in **host session-clock milliseconds**.
 *
 * Every timestamp in the game and on the wire (`upAt`, `downAt`, `strikeAt`, `startAt`, ...) is expressed on this
 * clock. On the host it starts at 0 when the session is created. On a client it is the local monotonic clock plus
 * the offset estimated by clock sync (see docs/plan/PROTOCOL.md, "Clock sync").
 *
 * Implementations must be built on `kotlin.time.TimeSource.Monotonic`. Wall-clock time
 * (`System.currentTimeMillis()`, `Clock.System`, `NSDate`) is never used: it can jump when the user or the network
 * changes the device time.
 *
 * Game logic receives a [SessionClock] by injection so tests can drive time deterministically.
 */
public interface SessionClock {
    /**
     * The current session time in milliseconds. Never decreases between calls on the same instance.
     */
    public fun nowMillis(): Long
}
