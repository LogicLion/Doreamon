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
 * @date 2023/5/11
 */
class PathAnimationView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val ballRadius = 50f
    private val ballPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val path = Path().apply {
        // 构建五角星路径
        val centerX = 150f.dp
        val centerY = 150f.dp
        val outerRadius = 120.dp


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


    private val ballPosition = FloatArray(2)
    private var animationStartTime: Long = 0


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Choreographer.getInstance().postFrameCallback(animationCallback)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.drawPath(path, ballPaint)
        canvas.drawCircle(ballPosition[0], ballPosition[1], ballRadius, ballPaint)
    }

    private val animationCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            if (animationStartTime == 0L) {
                animationStartTime = frameTimeNanos
            }

            val elapsedTime = (frameTimeNanos - animationStartTime) / 1_000_000f // 将纳秒转换为毫秒
            val pathMeasure = PathMeasure(path, false)
            val pathLength = pathMeasure.length

            // 根据动画时间计算小球在路径上的位置
            val distance = (elapsedTime % pathLength)
            pathMeasure.getPosTan(distance, ballPosition, null)

            invalidate() // 触发重绘

            Choreographer.getInstance().postFrameCallback(this) // 继续下一帧动画
        }
    }
}