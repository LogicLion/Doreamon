package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import com.doreamon.treasure.ext.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author wzh
 * @date 2023/5/10
 */
class StarPathView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var animationStartTime: Long = 0L

    private val smallCircleRadius = 50f

    private val smallCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#492d22")
        style = Paint.Style.FILL
    }


    //旋转一周所需时长
    private var duration = 5000f

    private var pathLength = 0f
    private var pathLengthProgress = 0f

    private val ballPosition = FloatArray(2)

    var isAnimationRunning: Boolean = false
        private set

    private val starPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#d8c7b5")
        style = Paint.Style.STROKE
        strokeWidth = 2f.dp
    }


    private val pathMeasure: PathMeasure by lazy {
        PathMeasure(path, true)
    }

    private val path = Path().apply {
        // 构建五角星路径
        val centerX = 100f.dp
        val centerY = 100f.dp
        val outerRadius = 80.dp


        val angleList = listOf(270, 126, 342, 198, 54)

        for ((index, angle) in angleList.withIndex()) {

            val x = centerX + outerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = centerY + outerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            if (index == 0) {
                moveTo(x, y) // 移动到起始点
            } else {
                lineTo(x, y)
            }
        }
        close()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        isAnimationRunning = true
        pathLength = pathMeasure.length
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制五角星
        canvas.drawPath(path, starPaint)
        canvas.drawCircle(ballPosition[0], ballPosition[1], smallCircleRadius, smallCirclePaint)
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


    private fun updateAnimation(currentTimeMillis: Long) {

        if (animationStartTime == 0L) {
            animationStartTime = currentTimeMillis
        }

        val elapsedTime = currentTimeMillis - animationStartTime

        if (elapsedTime <= duration) {
            pathLengthProgress = (elapsedTime % duration) * pathLength / duration
            pathMeasure.getPosTan(pathLengthProgress, ballPosition, null)
        }

    }


    fun startAnimation() {
        if (!isAnimationRunning) {
            isAnimationRunning = true
            Choreographer.getInstance().postFrameCallback(frameCallback)
        }
    }


    fun pauseAnimation() {
        if (isAnimationRunning) {
            //先停止绘制
            isAnimationRunning = false
            //移除帧绘制监听
            Choreographer.getInstance().removeFrameCallback(frameCallback)
            //记录已旋转角度
//            lastRotationAngle = rotationAngle
            animationStartTime = 0

            invalidate()
        }
    }


    fun stopAnimation() {
        isAnimationRunning = false
        Choreographer.getInstance().removeFrameCallback(frameCallback)
        animationStartTime = 0
//        lastRotationAngle = 0f
//        rotationAngle = 0f
        invalidate()
    }
}