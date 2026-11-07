// Created by Lexter Labra
package com.lexterlabra.molemayhem.ar

import com.lexterlabra.molemayhem.core.Vec3

/**
 * Result of [ArSceneController.hitTest].
 *
 * @property holeId The hole that was hit, matching `HoleSpec` ids in the protocol's `BoardSpec`.
 * @property point Hit point in board-local metres.
 * @property distanceMetres Distance from the ray origin to [point].
 */
public data class HoleHit(
    val holeId: Int,
    val point: Vec3,
    val distanceMetres: Float,
)
