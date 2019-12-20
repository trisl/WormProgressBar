package com.tristanroussel.worm

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
data class WormAnimationConfiguration(
        val duration: Long = 500L,
        val interpolator: Interpolator = AccelerateDecelerateInterpolator()
)