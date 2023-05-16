package com.example.doreamon.widget.custom_animator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.doreamon.treasure.ext.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 圆圈顺向移动
 * @author wzh
 * @date 2023/5/15
 */
class CirclePathView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CustomAnimationView(context, attrs) {

    private val bigCircleRadius = 200f
    private val smallCircleRadius = 50f


    //旋转角度
    private var rotationAngle: Float = 0f

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

    override fun onAnimationRunning(elapsedTime: Long) {
        rotationAngle = -360f * elapsedTime / animationDuration
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