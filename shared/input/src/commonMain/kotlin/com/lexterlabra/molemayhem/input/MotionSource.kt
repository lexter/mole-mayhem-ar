// Created by Lexter Labra
package com.lexterlabra.molemayhem.input

import kotlinx.coroutines.flow.Flow

/**
 * A stream of device-motion samples for the shared `StrikeDetector`.
 *
 * Platform implementations:
 * - Android: `Sensor.TYPE_LINEAR_ACCELERATION` plus `Sensor.TYPE_GYROSCOPE`.
 * - iOS: `CMDeviceMotion.userAcceleration` plus `CMDeviceMotion.rotationRate`.
 *
 * Both are normalised to the same units (see [MotionSample]) so one detector with one set of thresholds works on
 * both platforms.
 */
public interface MotionSource {
    /** `false` if the device lacks the required sensors; the game then offers tap mode only. */
    public val isAvailable: Boolean

    /**
     * Starts the sensors at roughly [rateHz] (the game uses 100 Hz) when collected, and stops them when the
     * collector is cancelled. The flow is cold: each collector gets its own sensor registration.
     */
    public fun samples(rateHz: Int): Flow<MotionSample>
}

/**
 * One fused motion sample, gravity removed.
 *
 * @property sensorTimestampNanos The sensor's own timestamp, in nanoseconds on the platform's monotonic sensor
 *   clock (Android `SensorEvent.timestamp`, iOS `CMLogItem.timestamp` converted to ns). Converted to session time
 *   by the caller; never wall-clock.
 * @property accelX Linear (user) acceleration along the device X axis, in G.
 * @property accelY Linear (user) acceleration along the device Y axis, in G.
 * @property accelZ Linear (user) acceleration along the device Z axis, in G.
 * @property gyroX Rotation rate about the device X axis, in rad/s.
 * @property gyroY Rotation rate about the device Y axis, in rad/s.
 * @property gyroZ Rotation rate about the device Z axis, in rad/s.
 */
public data class MotionSample(
    val sensorTimestampNanos: Long,
    val accelX: Float,
    val accelY: Float,
    val accelZ: Float,
    val gyroX: Float,
    val gyroY: Float,
    val gyroZ: Float,
)
