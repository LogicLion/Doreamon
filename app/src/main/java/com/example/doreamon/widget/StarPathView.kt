package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.doreamon.treasure.ext.dp
import com.example.doreamon.widget.custom_animator.CustomAnimationView
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author wzh
 * @date 2023/5/10
 */
class StarPathView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CustomAnimationView(context, attrs) {

    private val smallCircleRadius = 50f

    private val smallCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#492d22")
        style = Paint.Style.FILL
    }


    private var pathLength = 0f
    private var pathLengthProgress = 0f

    private val ballPosition = FloatArray(2)


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
                ballPosition[0] = x
                ballPosition[1] = y
            } else {
                lineTo(x, y)
            }
        }
        close()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pathLength = pathMeasure.length
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制五角星
        canvas.drawPath(path, starPaint)
        canvas.drawCircle(ballPosition[0], ballPosition[1], smallCircleRadius, smallCirclePaint)
    }


    override fun onAnimationRunning(elapsedTime: Long) {
        pathLengthProgress = (elapsedTime % animationDuration) * pathLength / animationDuration
        pathMeasure.getPosTan(pathLengthProgress, ballPosition, null)
    }


}