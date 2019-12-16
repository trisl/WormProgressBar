package com.tristanroussel.wormprogressbar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_worm_progress_bar.view.*

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
class WormProgressBar(
        context: Context,
        attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

    private val progressBar: ProgressBar

    //region Constants
    private val primaryDefaultColor: Int = context.color(android.R.color.holo_blue_bright)
    private val secondaryDefaultColor: Int = context.color(android.R.color.holo_blue_dark)
    //endregion Constants

    init {
        LayoutInflater.from(context).inflate(R.layout.view_worm_progress_bar, this).apply {
            progressBar = progress_bar
        }

        val configuration = retrieveConfiguration(attributeSet)
        configView(configuration)
    }

    private fun retrieveConfiguration(attributeSet: AttributeSet): WormConfiguration {
        val configuration = WormConfiguration()

        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.WormProgressBar,
                0,
                0
        ).run {
            val primaryColor = getColor(R.styleable.WormProgressBar_wormPrimaryColor, primaryDefaultColor)
            val secondaryColor = getColor(R.styleable.WormProgressBar_wormSecondaryColor, secondaryDefaultColor)

            configuration.primaryColor = primaryColor
            configuration.secondaryColor = secondaryColor

            recycle()
        }

        return configuration
    }

    private fun configView(configuration: WormConfiguration) {
        with(configuration) {
            setPrimaryColor(primaryColor)
            setSecondaryColor(secondaryColor)
        }
    }

    fun setPrimaryColor(@ColorInt primaryColor: Int) {
        progressBar.progressTintList = ColorStateList.valueOf(primaryColor)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(primaryColor)
    }

    fun setSecondaryColor(@ColorInt secondaryColor: Int) {
        progressBar.secondaryProgressTintList = ColorStateList.valueOf(secondaryColor)
    }
}