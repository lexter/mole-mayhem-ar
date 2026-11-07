// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

import kotlinx.serialization.Serializable

/**
 * An immutable rotation stored as a quaternion `(x, y, z, w)`, with `w` the scalar part.
 *
 * Both ARKit (`simd_quatf`) and ARCore (`Pose.getRotationQuaternion()`) use this component order, so platform
 * adapters copy the four values across unchanged. Producers must send unit quaternions; consumers validate this
 * at the network boundary.
 */
@Serializable
public data class Quaternion(
    val x: Float,
    val y: Float,
    val z: Float,
    val w: Float,
) {
    public companion object {
        /** No rotation. */
        public val Identity: Quaternion = Quaternion(0f, 0f, 0f, 1f)
    }
}
