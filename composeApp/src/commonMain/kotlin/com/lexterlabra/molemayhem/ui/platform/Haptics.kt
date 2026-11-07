// Created by Lexter Labra
package com.lexterlabra.molemayhem.ui.platform

/**
 * Haptic feedback, implemented per platform and bound in Koin.
 *
 * - Android: `View.performHapticFeedback` / `Vibrator` with `VibrationEffect`.
 * - iOS: `UIImpactFeedbackGenerator` / `UINotificationFeedbackGenerator`.
 *
 * Every hit gets a haptic within one frame of the strike being confirmed locally (docs/design/UI_CONCEPT.md,
 * "Interaction rules"). Implementations must be cheap to call on the main thread and must respect the user's
 * system setting for haptics.
 */
interface Haptics {
    /** Plays [effect]. Does nothing if the device has no haptic engine or haptics are disabled. */
    fun perform(effect: HapticEffect)
}

/** The game's haptic vocabulary, one entry per kind of event. */
enum class HapticEffect {
    /** A UI tap on a button. */
    Tap,

    /** A confirmed hit on a standard or golden mole. */
    Hit,

    /** A hit on a helmet that did not finish it ("CLANK"). */
    Clank,

    /** A bomb hit ("KABOOM"): strongest effect. */
    Bomb,

    /** A swing that hit nothing. */
    Miss,
}
