// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

/** The v1 game modes (docs/plan/DEVELOPMENT_PLAN.md, "In scope for v1"). */
public enum class GameMode {
    /** One player; waves get faster. */
    ArcadeSolo,

    /** Versus, 2–4 players on the same LAN. */
    WhackOff,

    /** Co-op, 2–4 players defend together. */
    CoopDefense,
}
