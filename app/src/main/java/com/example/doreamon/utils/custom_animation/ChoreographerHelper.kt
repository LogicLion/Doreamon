package com.example.doreamon.utils.custom_animation

import android.util.Log
import android.view.Choreographer

/**
 *
 * @author wzh
 * @date 2023/8/29
 */
class ChoreographerHelper {

    //完整执行一次所需时长,毫秒
    protected var animationDuration = 999 * 1000L

    protected var repeat: Int = 1


    var isAnimationRunning: Boolean = false
        private set


    private var elapsedTime: Long = 0L

    //暂停时记录已消耗时长
    private var lastElapsedTime: Long = 0L


    private var animationStartTime: Long = 0L
    private var countDownTime = 0L

    private var isCompleteFlag: Boolean = false

    var isPause = false
        private set

    var isEnd = false
        private set


    var animatorListener: CustomChoreographerListener? = null

    var animationUpdateListener: AnimationUpdateListener? = null


    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            val frameTimeMillis = frameTimeNanos / 1_000_000

            if (isAnimationRunning) {
                updateAnimation(frameTimeMillis)

                Choreographer.getInstance().postFrameCallback(this)
            }
        }
    }


    fun startAnimation() {
        if (!isAnimationRunning && !isEnd) {
            isAnimationRunning = true
            Choreographer.getInstance().postFrameCallback(frameCallback)
            animatorListener?.onAnimationStart()

            if (isPause) {
                isPause = false
            } else {
                animatorListener?.onTimeCountDown(countDownTime.toInt())
            }
        }
    }


    fun pauseAnimation() {
        if (isAnimationRunning && !isPause) {
            //先停止绘制
            isAnimationRunning = false
            //移除帧绘制监听
            Choreographer.getInstance().removeFrameCallback(frameCallback)
            //记录已消耗时长
            lastElapsedTime = elapsedTime

            animationStartTime = 0

            animatorListener?.onAnimationPause()
            isPause = true
        }
    }


    fun stopAnimation() {
        Log.v("stopAnimation:", elapsedTime.toString())
        isAnimationRunning = false
        isPause = false

        animationUpdateListener?.onAnimUpdate(1f)

        animatorListener?.onAnimationEnd()
        animatorListener?.onTimeCountDown(animationDuration.toInt() * repeat / 1000)

        Choreographer.getInstance().removeFrameCallback(frameCallback)
        if (isCompleteFlag && !isEnd) {
            animationUpdateListener?.onAnimFinish()
            isCompleteFlag = false
        }
        animationStartTime = 0
        elapsedTime = 0L
        lastElapsedTime = 0L
        countDownTime = 0

        isEnd = true

    }


    fun setAnimationDuration(duration: Float) {
        if (!isAnimationRunning) {
            animationDuration = if (duration < 1) {
                1000L
            } else {
                (duration * 1000).toLong()
            }
        }
    }

    fun setAnimationRepeat(animationRepeat: Int) {
        if (!isAnimationRunning) {
            repeat = if (animationRepeat < 1) {
                1
            } else {
                animationRepeat
            }
        }
    }

    private fun updateAnimation(currentTimeMillis: Long) {
        if (animationStartTime == 0L) {
            animationStartTime = currentTimeMillis
        }
        elapsedTime = currentTimeMillis - animationStartTime + lastElapsedTime

        if (elapsedTime < animationDuration * repeat) {

            val fraction = (elapsedTime % animationDuration).toFloat() / animationDuration
            animationUpdateListener?.onAnimUpdate(fraction)
            if (elapsedTime / 1000 != countDownTime) {
                countDownTime++
                animatorListener?.onTimeCountDown(countDownTime.toInt())
            }
        } else {
            isCompleteFlag = true
            stopAnimation()
        }
    }


    interface AnimationUpdateListener {
        fun onAnimUpdate(fraction: Float)

        /**
         * end可能是中途中断，finish是完整执行后调用
         */
        fun onAnimFinish()
    }


}