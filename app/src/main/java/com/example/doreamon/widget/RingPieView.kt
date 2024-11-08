package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.doreamon.treasure.ext.dp
import com.example.doreamon.entity.PieData
import java.lang.StrictMath.toDegrees
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * 环形饼图
 * @author wzh
 * @date 2024/11/7
 */
class RingPieView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {

    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f

    private var allValueSum: Int = 0

    private val pieDataList = mutableListOf<PieData>()

    val rotationAngleList = mutableListOf<Float>()
    private val pathList = mutableListOf<Path>()

    private val normalStrokeWidth = 50f.dp
    private val focusStrokeWidth = 60f.dp

    private val pieMargin = 50f.dp

    private var targetAngleIndex = -1

    private val ringPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = normalStrokeWidth
    }

    private val bgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#F9F9FE")
        strokeWidth = 3f.dp
    }

    private val linePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 1.5f.dp
        }
    }

    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        textSize = 19f.dp

    }

    private val decMetrics: FontMetrics by lazy {
        textPaint.fontMetrics
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(100.dp, widthMeasureSpec)
        val height = resolveSize(100.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w.toFloat()
        viewHeight = h.toFloat()

        centerPointX = viewWidth / 2
        centerPointY = viewHeight / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = viewHeight / 2 - pieMargin - focusStrokeWidth

        canvas.drawCircle(centerPointX, centerPointY, radius + normalStrokeWidth + 6f.dp, bgPaint)

        val normalRadiusHeight = radius + normalStrokeWidth / 2
        val focusRadiusHeight = radius + focusStrokeWidth / 2

        var startAngle = 90f

        pieDataList.forEachIndexed { index, pie ->
            val sweepAngle = (pie.value * 360f) / allValueSum
            ringPaint.color = pie.color

            val radiusHeight = if (index == targetAngleIndex) {
                ringPaint.strokeWidth = focusStrokeWidth
                focusRadiusHeight
            } else {
                ringPaint.strokeWidth = normalStrokeWidth
                normalRadiusHeight
            }
            canvas.drawArc(
                width / 2 - radiusHeight,
                centerPointY - radiusHeight,
                width / 2 + radiusHeight,
                centerPointY + radiusHeight,
                startAngle,
                sweepAngle,
                false,
                ringPaint
            )
            startAngle += sweepAngle
        }


        var preRotationAngle = 90f
        pathList.forEachIndexed { index, path ->
            val rotationAngle = rotationAngleList[index]
            val pieData = pieDataList[index]
            path.reset()
            linePaint.color = pieData.color


            val d = preRotationAngle + rotationAngle.toDouble() / 2
            var directAngle1 = Math.toRadians(
                d
            )

            preRotationAngle += rotationAngle


            val sin1 = sin(directAngle1).toFloat()
            val cos1 = cos(directAngle1).toFloat()

            val lineRadius = radius + normalStrokeWidth
            val startX1 = centerPointX + (lineRadius - 15f.dp) * cos1
            val startY1 = centerPointY + (lineRadius - 15f.dp) * sin1
            val endX1 = centerPointX + (lineRadius + 20f.dp) * cos1
            val endY1 = centerPointY + (lineRadius + 20f.dp) * sin1
            path.moveTo(startX1, startY1)
            path.lineTo(endX1, endY1)

            val decText = "${(pieData.value * 100 / allValueSum).toInt()}%"
            val decTextWidth = textPaint.measureText(decText)

            textPaint.getFontMetrics(decMetrics)
            textPaint.color = pieData.color


            if (d in 90.0..270.0) {
                path.lineTo(endX1 - 40f.dp, endY1)
                canvas.drawText(
                    decText,
                    endX1 - decTextWidth - 40f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textPaint
                )
            } else {
                path.lineTo(endX1 + 40f.dp, endY1)

                canvas.drawText(
                    decText,
                    endX1 + 40f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textPaint
                )
            }

            canvas.drawPath(path, linePaint)
        }

    }

    private val gestureDetector = GestureDetectorCompat(context, MyGestureListener()).apply {
        setIsLongpressEnabled(false)
        setOnDoubleTapListener(null)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


    fun setPieDataList(list: List<PieData>) {
        targetAngleIndex = -1
        pieDataList.clear()
        pieDataList.addAll(list)

        allValueSum = 0
        pieDataList.filter { it.value > 0 }.forEach {
            allValueSum += it.value
        }

        if (allValueSum == 0) {
            return
        }

        rotationAngleList.clear()
        pathList.clear()
        pieDataList.forEach {
            val sweepAngle = (it.value * 360f) / allValueSum
            rotationAngleList.add(sweepAngle)

            val path = Path()
            pathList.add(path)
        }


        invalidate()
    }


    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            val calculateAngle = calculateAngle(centerPointX, centerPointY, e.x, e.y)
            val targetAngle = normalizeAngle(calculateAngle.toFloat() - 90f)
            for (i in rotationAngleList.indices) {
                val startAngle = rotationAngleList.subList(0, i).sum()
                val endAngle = startAngle + rotationAngleList[i]
                if (targetAngle in startAngle..endAngle) {
                    targetAngleIndex = i
                    invalidate()
                    break
                }
            }
            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    }


    fun calculateAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        // 计算坐标差值
        val deltaX = x2 - x1
        val deltaY = y2 - y1

        // 使用 atan2 计算弧度
        val angleInRadian = atan2(deltaY, deltaX)

        // 将弧度转换为角度
        var angleInDegree = toDegrees(angleInRadian.toDouble())

        // 如果角度是负数，则加上 360°
        if (angleInDegree < 0) {
            angleInDegree += 360f
        }

        return angleInDegree
    }


    fun normalizeAngle(angle: Float): Float {
        // 将角度转换为0到360之间
        return (angle % 360 + 360) % 360
    }
}