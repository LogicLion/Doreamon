package com.example.doreamon.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.doreamon.treasure.ext.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author wzh
 * @date 2023/5/11
 */
class BallView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val bigCircleRadius = 200f
    private val smallCircleRadius = 50f

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private var rotationAngle = 0f
    private var animator: ValueAnimator? = null


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

    init {
        // 初始化动画
        animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            interpolator= LinearInterpolator()
            addUpdateListener { animation ->
                rotationAngle = animation.animatedValue as Float
                invalidate()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 在尺寸变化时更新大球的中心点和半径
        centerX = w / 2f
        centerY = h / 2f
        animator?.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(centerX, centerY, bigCircleRadius, bigCirclePaint)
        val smallCircleX =
            centerX + bigCircleRadius * cos(Math.toRadians(rotationAngle.toDouble())).toFloat()
        val smallCircleY =
            centerY + bigCircleRadius * sin(Math.toRadians(rotationAngle.toDouble())).toFloat()
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint)
    }

    fun isAnimationRunning(): Boolean {
        return animator?.isRunning ?: false
    }

    fun startAnimation() {
        animator?.start()
    }

    fun pauseAnimation() {
        animator?.pause()
    }

    fun resumeAnimation() {

        animator?.resume()
    }

    fun stopAnimation() {
        animator?.cancel()
    }
}