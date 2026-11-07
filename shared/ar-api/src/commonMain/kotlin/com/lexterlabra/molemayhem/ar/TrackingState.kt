// Created by Lexter Labra
package com.lexterlabra.molemayhem.ar

/** Platform-neutral AR tracking quality, mapped from ARKit `ARCamera.TrackingState` and ARCore `TrackingState`. */
public enum class TrackingState {
    /** The session is not running or has not produced a frame yet. */
    NotAvailable,

    /** Tracking is degraded (fast motion, low light, few features). Aim and placement are unreliable. */
    Limited,

    /** Tracking is good. */
    Normal,
}
