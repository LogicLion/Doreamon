package com.example.doreamon.widget

import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.doreamon.treasure.ext.dp
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Timer来实现一个小圆围绕大圆旋转的动画效果
 * @author wzh
 * @date 2023/5/8
 */
class CustomAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bigCircleRadius = 200f
    private val smallCircleRadius = 50f
    private val bigCirclePaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f.dp
        isAntiAlias = true
    }

    private val smallCirclePaint = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
    }

    private var rotationAngle = 0f
    private var animationDuration = 1000L
    private var isAnimationRunning = false
    private var timer: Timer? = null
    private var interpolator: TimeInterpolator = LinearInterpolator()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // 绘制大圆
        canvas.drawCircle(centerX, centerY, bigCircleRadius, bigCirclePaint)

        // 计算小圆的位置
        val smallCircleX =
            centerX + bigCircleRadius * cos(Math.toRadians(rotationAngle.toDouble())).toFloat()
        val smallCircleY =
            centerY + bigCircleRadius * sin(Math.toRadians(rotationAngle.toDouble())).toFloat()

        // 绘制小圆
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint)
    }

    fun startAnimation(duration: Long) {
        if (!isAnimationRunning) {
            isAnimationRunning = true
            animationDuration = duration
            rotationAngle = 0f

            // 创建定时器并启动定时任务
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val interpolatedTime =
                        (System.currentTimeMillis() % animationDuration).toFloat() / animationDuration
                    rotationAngle = interpolator.getInterpolation(interpolatedTime) * 360
                    postInvalidate() // 通知 View 进行重绘
                }
            }, 0, 8) // 这里使用固定的刷新频率，约为 120帧每秒
        }
    }

    fun stopAnimation() {
        isAnimationRunning = false
        timer?.cancel()
        timer = null
    }

    fun setInterpolator(interpolator: TimeInterpolator) {
        this.interpolator = interpolator
    }
}