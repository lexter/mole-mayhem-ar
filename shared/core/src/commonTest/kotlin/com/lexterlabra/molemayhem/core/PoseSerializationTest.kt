// Created by Lexter Labra
package com.lexterlabra.molemayhem.core

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Smoke test that the shared model compiles, serializes and behaves the same on every target (JVM, Android host,
 * iOS simulator). Board-local poses travel over the wire in `PlayerPose`, so the JSON shape is part of the protocol.
 */
class PoseSerializationTest {
    private val json = Json { encodeDefaults = true }

    @Test
    fun identityPoseHasTheExpectedWireShape() {
        val encoded = json.encodeToString(Pose.serializer(), Pose.Identity)
        assertEquals(
            """{"position":{"x":0.0,"y":0.0,"z":0.0},"rotation":{"x":0.0,"y":0.0,"z":0.0,"w":1.0}}""",
            encoded,
        )
    }

    @Test
    fun poseRoundTripsThroughJson() {
        val pose = Pose(Vec3(0.12f, 0f, -0.3f), Quaternion(0f, 0.7071068f, 0f, 0.7071068f))
        val decoded = json.decodeFromString(Pose.serializer(), json.encodeToString(Pose.serializer(), pose))
        assertEquals(pose, decoded)
    }
}
