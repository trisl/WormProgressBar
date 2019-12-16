package com.tristanroussel.wormprogressbar.worm

import androidx.annotation.ColorInt

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
interface IWormConfiguration {

    fun setPrimaryColor(@ColorInt primaryColor: Int)

    fun setSecondaryColor(@ColorInt secondaryColor: Int)
}