package com.tristanroussel.worm

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import kotlinx.android.synthetic.main.view_worm_progress_bar.view.*

/**
 * Created by Tristan Roussel on 2019-12-16.
 */
class WormProgressBar(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

    private enum class AnimationState {
        SECONDARY,
        PRIMARY,
        PRIMARY_REVERSE,
        SECONDARY_REVERSE
    }

    private val progressBar: ProgressBar

    private var animationState: AnimationState = AnimationState.SECONDARY

    //region Constants
    private val defaultPrimaryColor: Int = context.color(android.R.color.holo_blue_bright)
    private val defaultSecondaryColor: Int = context.color(android.R.color.holo_blue_dark)
    private val defaultWormAnimationConfiguration = WormAnimationConfiguration()
    //endregion Constants

    //region Animator
    private lateinit var secondaryProgressAnimator: ObjectAnimator
    private lateinit var secondaryProgressReverseAnimator: ObjectAnimator
    private lateinit var primaryProgressAnimator: ObjectAnimator
    private lateinit var primaryProgressReverseAnimator: ObjectAnimator
    //endregion Animator

    var isAnimating: Boolean = false
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.view_worm_progress_bar, this).apply {
            progressBar = progress_bar
        }

        configView(configuration = retrieveConfiguration(attributeSet))
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
            val primaryColor =
                getColor(R.styleable.WormProgressBar_wormPrimaryColor, defaultPrimaryColor)
            val secondaryColor =
                getColor(R.styleable.WormProgressBar_wormSecondaryColor, defaultSecondaryColor)

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

    private fun WormAnimationConfiguration.createAnimator(
        propertyName: String,
        startValue: Int,
        endValue: Int,
        actionOnStart: () -> Unit,
        actionOnEnd: () -> Unit
    ): ObjectAnimator =
        ObjectAnimator
            .ofInt(progressBar, propertyName, startValue, endValue)
            .apply {
                duration = this@createAnimator.duration
                interpolator = this@createAnimator.interpolator
                doOnStart { actionOnStart.invoke() }
                doOnEnd { actionOnEnd.invoke() }
            }

    private fun checkInitialization() {
        if (::secondaryProgressAnimator.isInitialized.not()
            || ::secondaryProgressReverseAnimator.isInitialized.not()
            || ::primaryProgressAnimator.isInitialized.not()
            || ::primaryProgressReverseAnimator.isInitialized.not()
        ) {
            throw Exception("Animation is not initialized, may be you forgot to call the initAnimation method ?")
        }
    }
    //endregion private

    //region Worm configuration
    fun setPrimaryColor(@ColorInt primaryColor: Int) {
        progressBar.progressTintList = ColorStateList.valueOf(primaryColor)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(primaryColor)
    }

    fun setSecondaryColor(@ColorInt secondaryColor: Int) {
        progressBar.secondaryProgressTintList = ColorStateList.valueOf(secondaryColor)
    }
    //endregion Worm configuration

    //region Worm animation
    fun initAnimation(configuration: WormAnimationConfiguration? = null) {
        if (!isAnimating) {
            val animationConfiguration = configuration ?: defaultWormAnimationConfiguration

            secondaryProgressAnimator = animationConfiguration.createAnimator(
                propertyName = "secondaryProgress",
                startValue = 0,
                endValue = 1000,
                actionOnStart = { animationState = AnimationState.SECONDARY },
                actionOnEnd = { primaryProgressAnimator.start() }
            )

            secondaryProgressReverseAnimator = animationConfiguration.createAnimator(
                propertyName = "secondaryProgress",
                startValue = 1000,
                endValue = 0,
                actionOnStart = {
                    animationState =
                        AnimationState.SECONDARY_REVERSE
                },
                actionOnEnd = { secondaryProgressAnimator.start() }
            )

            primaryProgressAnimator = animationConfiguration.createAnimator(
                propertyName = "progress",
                startValue = 0,
                endValue = 1000,
                actionOnStart = { animationState = AnimationState.PRIMARY },
                actionOnEnd = { primaryProgressReverseAnimator.start() }
            )

            primaryProgressReverseAnimator = animationConfiguration.createAnimator(
                propertyName = "progress",
                startValue = 1000,
                endValue = 0,
                actionOnStart = { animationState = AnimationState.PRIMARY_REVERSE },
                actionOnEnd = { secondaryProgressReverseAnimator.start() }
            )
        }
    }

    fun start() {
        checkInitialization()

        isAnimating = true

        when (animationState) {
            AnimationState.SECONDARY -> secondaryProgressAnimator.start()
            AnimationState.PRIMARY -> primaryProgressAnimator.start()
            AnimationState.PRIMARY_REVERSE -> primaryProgressReverseAnimator.start()
            AnimationState.SECONDARY_REVERSE -> secondaryProgressReverseAnimator.start()
        }
    }

    fun pause() {
        checkInitialization()

        isAnimating = false

        when (animationState) {
            AnimationState.SECONDARY -> secondaryProgressAnimator.pause()
            AnimationState.PRIMARY -> primaryProgressAnimator.pause()
            AnimationState.PRIMARY_REVERSE -> primaryProgressReverseAnimator.pause()
            AnimationState.SECONDARY_REVERSE -> secondaryProgressReverseAnimator.pause()
        }
    }

    fun complete(animate: Boolean = true) {
        pause()

        if (animate) {
            ObjectAnimator
                .ofInt(progressBar, "progress", progressBar.progress, 0)
                .apply {
                    duration = progressBar.progress * 500L / progressBar.max
                    interpolator = AccelerateDecelerateInterpolator()
                }
                .start()

            ObjectAnimator
                .ofInt(
                    progressBar,
                    "secondaryProgress",
                    progressBar.secondaryProgress,
                    progressBar.max
                )
                .apply {
                    duration =
                        (progressBar.max - progressBar.secondaryProgress) * 500L / progressBar.max
                    interpolator = AccelerateDecelerateInterpolator()
                }
                .start()
        } else {
            progressBar.progress = 0
            progressBar.secondaryProgress = progressBar.max
        }
    }
    //endregion Worm animation
}