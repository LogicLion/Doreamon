package com.example.doreamon.utils.custom_animation

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @author wzh
 * @date 2023/5/8
 */
abstract class CustomAnimationView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    val choreographerHelper = ChoreographerHelper()

    //完整执行一次所需时长,毫秒
    protected var animationDuration = 999 * 1000L

    protected var repeat: Int = 1


    var isAnimationRunning: Boolean = false
        private set
        get() {
            return choreographerHelper.isAnimationRunning
        }

    var customAnimationListener: CustomAnimationListener? = null

    init {
        choreographerHelper.animationUpdateListener = object :
            ChoreographerHelper.AnimationUpdateListener {
            override fun onAnimUpdate(fraction: Float) {
                onAnimationRunning(fraction)
            }

            override fun onAnimFinish() {
                onAnimationFinish()
                invalidate()
            }
        }
    }


    var animatorListener: CustomChoreographerListener? = null
        set(value) {
            choreographerHelper.animatorListener = value
            field = value
        }


    fun startAnimation() {
        choreographerHelper.startAnimation()
        invalidate()
    }


    fun pauseAnimation() {
        choreographerHelper.pauseAnimation()
        invalidate()

    }


    private fun stopAnimation() {
        choreographerHelper.stopAnimation()
        onAnimationFinish()
        invalidate()
    }

    protected open fun onAnimationFinish() {
        customAnimationListener?.onAnimationFinish()
    }

    fun setAnimationDuration(duration: Float) {
        if (!choreographerHelper.isAnimationRunning) {
            choreographerHelper.setAnimationDuration(duration)
        }

    }

    fun setAnimationRepeat(animationRepeat: Int) {
        if (!choreographerHelper.isAnimationRunning) {
            choreographerHelper.setAnimationRepeat(animationRepeat)
        }
    }

    abstract fun onAnimationRunning(fraction: Float)
}