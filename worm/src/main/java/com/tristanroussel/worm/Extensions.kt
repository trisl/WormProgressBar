package com.tristanroussel.worm

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
@ColorInt
fun Context.color(@ColorRes resource: Int): Int = ContextCompat.getColor(this, resource)