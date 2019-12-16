package com.tristanroussel.wormprogressbar.worm

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
interface IWormAnimation {

    fun start(configuration: WormAnimationConfiguration? = null)

    fun cancel()
}