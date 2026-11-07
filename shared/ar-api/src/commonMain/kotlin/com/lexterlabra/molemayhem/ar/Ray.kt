// Created by Lexter Labra
package com.lexterlabra.molemayhem.ar

import com.lexterlabra.molemayhem.core.Vec3

/**
 * A ray in **board-local** metres.
 *
 * @property origin Start point of the ray.
 * @property direction Direction of the ray. Must be non-zero; implementations normalise it.
 */
public data class Ray(
    val origin: Vec3,
    val direction: Vec3,
)
