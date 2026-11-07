// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

/**
 * The shared board coordinate frame, defined by the detected printed Mole Mat image.
 *
 * Every device detects the same marker (ARKit image detection on iOS, ARCore Augmented Images on Android), so the
 * marker pose is a frame all players agree on without exchanging raw world transforms or using Cloud Anchors.
 * Board-local coordinates have their origin at the centre of the marker, +Y along the marker's normal (up out of
 * the table) and units in metres.
 *
 * This value is local to one device and is never sent over the network.
 *
 * @property markerId Identifier of the reference image that was detected (matches `BoardSpec.markerId`).
 * @property worldFromBoard Device-world pose of the marker centre: transforms board-local points into this
 *   device's AR world frame.
 * @property markerWidthMetres Physical width of the printed marker, as configured in the reference image.
 */
public data class BoardFrame(
    val markerId: String,
    val worldFromBoard: Pose,
    val markerWidthMetres: Float,
)
