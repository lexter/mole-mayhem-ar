// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

import kotlinx.serialization.Serializable

/**
 * A rigid transform: a [position] in metres plus a [rotation].
 *
 * The same type is used in two frames, so every property or parameter of type [Pose] must say which one it is:
 * - **Board-local:** relative to the Mole Mat's [BoardFrame]. This is the only frame that may be sent over the
 *   network (for example in `PlayerPose`), because every device agrees on it.
 * - **Device-world:** relative to one device's AR session origin. Each AR session has its own origin, so a
 *   device-world pose means nothing on another device and must never be sent.
 *
 * Axis convention (shared by ARKit and ARCore): right-handed, metres, +Y up.
 */
@Serializable
public data class Pose(
    val position: Vec3,
    val rotation: Quaternion,
) {
    public companion object {
        /** The identity transform. */
        public val Identity: Pose = Pose(Vec3.Zero, Quaternion.Identity)
    }
}
