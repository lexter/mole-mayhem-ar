// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

import kotlinx.serialization.Serializable

/**
 * An immutable 3D vector in metres.
 *
 * Which frame the components are expressed in is a property of the value that holds it (for example [Pose]
 * documents whether it is board-local or device-world). Coordinates sent over the network are always board-local
 * metres.
 */
@Serializable
public data class Vec3(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    public companion object {
        /** The origin, (0, 0, 0). */
        public val Zero: Vec3 = Vec3(0f, 0f, 0f)
    }
}
