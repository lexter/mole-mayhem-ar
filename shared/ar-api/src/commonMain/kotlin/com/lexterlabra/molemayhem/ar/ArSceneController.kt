// Created by Lexter Labra
package com.lexterlabra.molemayhem.ar

import com.lexterlabra.molemayhem.core.BoardFrame
import com.lexterlabra.molemayhem.core.Pose
import kotlinx.coroutines.flow.StateFlow

/**
 * Owns the native AR session and the 3D board scene for one device.
 *
 * Implemented natively and injected into the shared UI:
 * - Android: ARCore + SceneView, in `androidApp`.
 * - iOS: ARKit + RealityKit, written in Swift in `iosApp` (RealityKit cannot be called from Kotlin/Native) and
 *   passed in through `MainViewController(arFactory)`.
 *
 * Contract:
 * - All methods are called on the main thread.
 * - Poses crossing this interface are **board-local** unless documented otherwise. Device-world transforms stay
 *   inside the implementation.
 * - The implementation renders the camera feed and the 3D scene; Compose only draws the HUD on top.
 */
public interface ArSceneController {
    /** Current AR tracking quality. The game pauses when this is [TrackingState.Limited] for more than 1 s. */
    public val trackingState: StateFlow<TrackingState>

    /** The Mole Mat frame once the marker has been detected and locked, or `null` before that. */
    public val boardFrame: StateFlow<BoardFrame?>

    /**
     * Starts (or restarts) the AR session and begins looking for the reference image [markerId].
     * Safe to call again after [stop].
     */
    public fun start(markerId: String)

    /** Pauses camera and tracking, for example when the app goes to the background. */
    public fun pause()

    /** Resumes after [pause]. Tracking may report [TrackingState.Limited] while it relocalises. */
    public fun resume()

    /** Stops the session and releases the camera and all scene resources. */
    public fun stop()

    /**
     * The device camera pose in the board frame for the most recent frame with good tracking, or `null` if the
     * board is not locked yet. Used to lock the aim before a swing starts.
     */
    public fun lastGoodCameraPose(): Pose?

    /**
     * Casts [ray] (board-local) against the hole colliders and returns the closest hit, or `null` for a miss.
     */
    public fun hitTest(ray: Ray): HoleHit?
}
