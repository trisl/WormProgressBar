package com.tristanroussel.wormprogressbar.worm

import android.view.animation.Interpolator

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
data class WormAnimationConfiguration(
        val duration: Long,
        val interpolator: Interpolator
)