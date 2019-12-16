package com.tristanroussel.wormprogressbar.worm

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.tristanroussel.wormprogressbar.R
import com.tristanroussel.wormprogressbar.color
import kotlinx.android.synthetic.main.view_worm_progress_bar.view.*

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
class WormProgressBar(
        context: Context,
        attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet),
        IWormConfiguration,
        IWormAnimation {

    private val progressBar: ProgressBar

    //region Constants
    private val defaultPrimaryColor: Int = context.color(android.R.color.holo_blue_bright)
    private val defaultSecondaryColor: Int = context.color(android.R.color.holo_blue_dark)
    private val defaultWormAnimationConfiguration = AnimationConfigurationBuilder().build()
    //endregion Constants

    init {
        LayoutInflater.from(context).inflate(R.layout.view_worm_progress_bar, this).apply {
            progressBar = progress_bar
        }

        val configuration = retrieveConfiguration(attributeSet)
        configView(configuration)
    }

    //region private
    private fun retrieveConfiguration(attributeSet: AttributeSet): WormConfiguration {
        val configuration = WormConfiguration()

        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.WormProgressBar,
                0,
                0
        ).run {
            val primaryColor = getColor(R.styleable.WormProgressBar_wormPrimaryColor, defaultPrimaryColor)
            val secondaryColor = getColor(R.styleable.WormProgressBar_wormSecondaryColor, defaultSecondaryColor)

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
    //endregion private

    //region Worm configuration
    override fun setPrimaryColor(@ColorInt primaryColor: Int) {
        progressBar.progressTintList = ColorStateList.valueOf(primaryColor)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(primaryColor)
    }

    override fun setSecondaryColor(@ColorInt secondaryColor: Int) {
        progressBar.secondaryProgressTintList = ColorStateList.valueOf(secondaryColor)
    }
    //endregion Worm configuration

    //region Worm animation
    override fun start(configuration: WormAnimationConfiguration?) {
        val animationConfiguration = configuration ?: defaultWormAnimationConfiguration

        val secondaryProgressReverseAnimation =
                ObjectAnimator
                        .ofInt(progressBar, "secondaryProgress", 1000, 0)
                        .apply {
                            duration = animationConfiguration.duration
                            interpolator = animationConfiguration.interpolator
                        }

        val primaryProgressReverseAnimator =
                ObjectAnimator
                        .ofInt(progressBar, "progress", 1000, 0)
                        .apply {
                            duration = animationConfiguration.duration
                            interpolator = animationConfiguration.interpolator

                            doOnEnd {
                                secondaryProgressReverseAnimation.start()
                            }
                        }

        val primaryProgressAnimator =
                ObjectAnimator
                        .ofInt(progressBar, "progress", 0, 1000)
                        .apply {
                            duration = animationConfiguration.duration
                            interpolator = animationConfiguration.interpolator

                            doOnEnd {
                                primaryProgressReverseAnimator.start()
                            }
                        }

        val secondaryProgressAnimation =
                ObjectAnimator
                        .ofInt(progressBar, "secondaryProgress", 0, 1000)
                        .apply {
                            duration = animationConfiguration.duration
                            interpolator = animationConfiguration.interpolator

                            doOnEnd {
                                primaryProgressAnimator.start()
                            }
                        }

        secondaryProgressReverseAnimation.doOnEnd { secondaryProgressAnimation.start() }

        secondaryProgressAnimation.start()
    }

    override fun cancel() {

    }
    //region Worm animation

    class AnimationConfigurationBuilder {

        var duration: Long = 400L
        var interpolator: Interpolator = AccelerateDecelerateInterpolator()

        fun build(): WormAnimationConfiguration = WormAnimationConfiguration(duration, interpolator)
    }
}