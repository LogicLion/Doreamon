package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.Choreographer
import android.view.View
import com.doreamon.treasure.ext.dp
import com.example.doreamon.widget.custom_animator.CustomAnimatorListener
import kotlin.math.cos
import kotlin.math.sin

/**
 * 使用Choreographer实现一个小圆围绕大圆旋转的动画效果
 * @author wzh
 * @date 2023/5/8
 */
class CustomChoreographerAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {


    private val bigCircleRadius = 200f
    private val smallCircleRadius = 50f

    //旋转一周所需时长,毫秒
    private var animationDuration = 2000L

    //旋转角度
    private var rotationAngle: Float = 0f
    var isAnimationRunning: Boolean = false
        private set


    private var elapsedTime: Long = 0L

    //暂停时记录已消耗时长
    private var lastElapsedTime: Long = 0L

    private var rotationSpeed: Float = 0f

    private var animationStartTime: Long = 0L
    private var countDownTime = 0L
    private var isPause = false

    var animatorListener: CustomAnimatorListener? = null

    private val bigCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#f9d2e4")
        style = Paint.Style.STROKE
        strokeWidth = 5f.dp
    }

    private val smallCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#01847f")
        style = Paint.Style.FILL
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            val frameTimeMillis = frameTimeNanos / 1_000_000

            if (isAnimationRunning) {
                updateAnimation(frameTimeMillis)
                invalidate()
                Choreographer.getInstance().postFrameCallback(this)
            }
        }
    }


    fun startAnimation() {
        if (!isAnimationRunning) {
            isAnimationRunning = true
            rotationSpeed = 360f / animationDuration
            Choreographer.getInstance().postFrameCallback(frameCallback)
            animatorListener?.onAnimationStart()

            if (isPause) {
                isPause = false
            } else {
                countDownTime = animationDuration / 1000
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
            //记录已旋转角度
            lastElapsedTime = elapsedTime
            animationStartTime = 0

            invalidate()
            animatorListener?.onAnimationPause()
            isPause = true
        }
    }


    fun stopAnimation() {
        Log.v("stopAnimation:", elapsedTime.toString())
        isAnimationRunning = false
        isPause = false
        Choreographer.getInstance().removeFrameCallback(frameCallback)
        animationStartTime = 0
        elapsedTime = 0L
        lastElapsedTime = 0L
        rotationAngle = 0f
        invalidate()

        animatorListener?.onAnimationEnd()
        animatorListener?.onTimeCountDown(0)
    }

    fun setAnimationDuration(duration: Int) {
        if (!isAnimationRunning) {
            animationDuration = duration * 1000L
        }
    }

    private fun updateAnimation(currentTimeMillis: Long) {
        if (animationStartTime == 0L) {
            animationStartTime = currentTimeMillis
        }
        elapsedTime = currentTimeMillis - animationStartTime + lastElapsedTime

        if (elapsedTime < animationDuration) {
            rotationAngle = rotationSpeed * elapsedTime

            if ((animationDuration / 1000 - elapsedTime / 1000) != countDownTime) {
                countDownTime--
                animatorListener?.onTimeCountDown(countDownTime.toInt())
            }
        } else {
            stopAnimation()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        canvas.drawCircle(centerX, centerY, bigCircleRadius, bigCirclePaint)
        val smallCircleX =
            centerX + bigCircleRadius * cos(Math.toRadians(rotationAngle.toDouble())).toFloat()
        val smallCircleY =
            centerY + bigCircleRadius * sin(Math.toRadians(rotationAngle.toDouble())).toFloat()
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint)
    }

}