package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Path
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.doreamon.treasure.ext.dp
import com.example.doreamon.entity.PieData
import java.lang.StrictMath.toDegrees
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 环形饼图
 * @author wzh
 * @date 2024/11/7
 */
class RingPieView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs) {

    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f


    private val pieDataList = mutableListOf<PieData>()

    val percentList = mutableListOf<Int>()
    val rotationAngleList = mutableListOf<Float>()


    private val originLabelAngleList = mutableListOf<Float>()
    private val adjustLabelAngleList = mutableListOf<Float>()
    private val pathList = mutableListOf<Path>()

    private val normalStrokeWidth = 50f.dp
    private val focusStrokeWidth = 60f.dp

    private val pieMargin = 60f.dp

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

    private val whiteBgPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 1.5f.dp
        }
    }


    private val titleTextPaint = TextPaint().apply {
        textSize = 18f.dp
        color = Color.parseColor("#333333")
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }


    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        textSize = 19f.dp
    }

    private val textSymbolPaint: Paint = Paint().apply {
        isAntiAlias = true
        textSize = 15f.dp
    }

    private val textDecPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#666666")
        textSize = 13f.dp
    }

    private val decMetrics: FontMetrics by lazy {
        textPaint.fontMetrics
    }

    private var staticLayout: StaticLayout? = null


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
            val sweepAngle = rotationAngleList[index]
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

        startAngle = 90f

        pieDataList.forEachIndexed { index, pie ->
            val sweepAngle = rotationAngleList[index]
            linePaint.color = Color.WHITE
            val angle = Math.toRadians(startAngle.toDouble())
            val sin = sin(angle).toFloat()
            val cos = cos(angle).toFloat()

            val startX = centerPointX + radius * cos
            val startY = centerPointY + radius * sin

            if (targetAngleIndex != -1 && (index == targetAngleIndex || index == targetAngleIndex + 1)) {
                val endX = centerPointX + (radius + focusStrokeWidth) * cos
                val endY = centerPointY + (radius + focusStrokeWidth) * sin
                canvas.drawLine(startX, startY, endX, endY, linePaint)
            } else {
                val endX = centerPointX + (radius + normalStrokeWidth) * cos
                val endY = centerPointY + (radius + normalStrokeWidth) * sin
                canvas.drawLine(startX, startY, endX, endY, linePaint)
            }

            startAngle += sweepAngle
        }

        pathList.forEachIndexed { index, path ->
            val pieData = pieDataList[index]
            path.reset()
            linePaint.color = pieData.color

            val labelAngel = originLabelAngleList[index]
            var directAngle1 = Math.toRadians(
                labelAngel.toDouble()
            )

            val labelAngel2 = adjustLabelAngleList[index]
            var directAngle2 = Math.toRadians(
                labelAngel2.toDouble()
            )

            val sin1 = sin(directAngle1).toFloat()
            val cos1 = cos(directAngle1).toFloat()

            val sin2 = sin(directAngle2).toFloat()
            val cos2 = cos(directAngle2).toFloat()

            val lineRadius = radius + normalStrokeWidth
            val focusLineRadius = radius + focusStrokeWidth
            val startX1 = if (index == targetAngleIndex) {
                centerPointX + focusLineRadius * cos1
            } else {
                centerPointX + lineRadius * cos1
            }

            val startY1 = if (index == targetAngleIndex) {
                centerPointY + focusLineRadius * sin1
            } else {
                centerPointY + lineRadius * sin1
            }

            val endX1 = centerPointX + (lineRadius + 40f.dp) * cos2
            val endY1 = centerPointY + (lineRadius + 40f.dp) * sin2
            path.moveTo(startX1, startY1)
            path.lineTo(endX1, endY1)

            val decText = "${(percentList[index])}"
            val decSymbol = "%"
            val decTextWidth = textPaint.measureText(decText)
            val decSymbolWidth = textSymbolPaint.measureText(decSymbol)

            textPaint.getFontMetrics(decMetrics)
            textPaint.color = pieData.color

            textSymbolPaint.color = pieData.color


            if (startX1 > endX1) {
                path.lineTo(endX1 - 20f.dp, endY1)

                canvas.drawText(
                    decText,
                    endX1 - decTextWidth - decSymbolWidth - 25f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textPaint
                )

                canvas.drawText(
                    decSymbol,
                    endX1 - decSymbolWidth - 25f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textSymbolPaint
                )

                canvas.drawText(
                    pieData.des,
                    endX1 - decTextWidth - decSymbolWidth - 25f.dp,
                    endY1 + 25f.dp,
                    textDecPaint
                )

                canvas.drawPath(path, linePaint)
                canvas.drawCircle(endX1 - 20f.dp, endY1, 2f.dp, whiteBgPaint)
                canvas.drawCircle(endX1 - 20f.dp, endY1, 2f.dp, linePaint)
            } else {
                path.lineTo(endX1 + 20f.dp, endY1)

                canvas.drawText(
                    decText,
                    endX1 + 25f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textPaint
                )

                canvas.drawText(
                    decSymbol,
                    endX1 + decTextWidth + 25f.dp,
                    endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
                    textSymbolPaint
                )

                canvas.drawText(
                    pieData.des, endX1 + 25f.dp, endY1 + 25f.dp, textDecPaint
                )

                canvas.drawPath(path, linePaint)
                canvas.drawCircle(endX1 + 20f.dp, endY1, 2f.dp, whiteBgPaint)
                canvas.drawCircle(endX1 + 20f.dp, endY1, 2f.dp, linePaint)
            }

        }

        staticLayout?.let {
            // 在画布上绘制文本
            canvas.save()
            canvas.translate(
                centerPointX,
                centerPointY - it.height / 2
            )
            it.draw(canvas)
            canvas.restore()
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
        pieDataList.addAll(list.filter { it.value > 0 })

        var allValueSum = 0
        pieDataList.forEach {
            allValueSum += it.value
        }

        if (allValueSum == 0) {
            return
        }

        rotationAngleList.clear()
        originLabelAngleList.clear()
        pathList.clear()

        val list1 = pieDataList.map { it.value }

        convertPercent(list1)
        var preRotationAngle = 90f
        percentList.forEach {
            val sweepAngle = (it * 360f) / 100
            rotationAngleList.add(sweepAngle)

            originLabelAngleList.add(preRotationAngle + sweepAngle / 2)
            preRotationAngle += sweepAngle

            val path = Path()
            pathList.add(path)
        }

        adjustLabelAngle()

        invalidate()

    }


    fun setTitle(text: String) {
        staticLayout =
            StaticLayout.Builder.obtain(text, 0, text.length, titleTextPaint, 160.dp)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)  // 设置文本对齐方式
                .setLineSpacing(0f, 1.2f) // 设置行间距
                .build()

        invalidate()
    }


    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            val minRadius = viewHeight / 2 - pieMargin - focusStrokeWidth
            val maxRadius = viewHeight / 2 - pieMargin - focusStrokeWidth + normalStrokeWidth

            val distance = calculateDistance(centerPointX, centerPointY, e.x, e.y)

            if (distance < minRadius || distance > maxRadius) {
                return false
            }

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


    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        // 计算两个点之间的距离
        return Math.sqrt(((x2 - x1).toDouble().pow(2.0) + (y2 - y1).toDouble().pow(2.0))).toFloat()
    }


    fun normalizeAngle(angle: Float): Float {
        // 将角度转换为0到360之间
        return (angle % 360 + 360) % 360
    }

    /**
     * 错开一下角度，免得挨在一起
     */
    private fun adjustLabelAngle() {

        adjustLabelAngleList.clear()

        originLabelAngleList.forEach {
            adjustLabelAngleList.add(it)
        }

        for (i in 1 until adjustLabelAngleList.size) {
            var angle = adjustLabelAngleList[i]
            val lastAngle = adjustLabelAngleList[i - 1]

            if (angle > 270 && angle < 290) {
                angle += 20f
                adjustLabelAngleList[i] = angle
            }
            if (angle - lastAngle < 20 && lastAngle < 340) {
                angle = lastAngle + 20f
                adjustLabelAngleList[i] = angle
            }
        }

        for (i in adjustLabelAngleList.size - 2 downTo 0) {
            var angle = adjustLabelAngleList[i]
            val lastAngle = adjustLabelAngleList[i + 1]

            if (angle < 270 && angle > 250) {
                angle -= 20f
                adjustLabelAngleList[i] = angle
            }

            if (lastAngle - angle < 20) {
                angle = lastAngle - 20f
                adjustLabelAngleList[i] = angle
            }
        }
    }


    private fun convertPercent(list: List<Int>) {
        val total = list.sum()  // 数据总和
        var percentages = list.map { (it.toDouble() / total) * 100 }  // 计算百分比

        // 步骤1: 小于 1% 的百分比调整为 1%
        percentages = percentages.map { if (it < 1) 1.0 else it }

        // 步骤2: 四舍五入并转换为整数
        val integerPercentages = percentages.map { it.roundToInt().toInt() }

        // 步骤3: 计算总和误差
        val totalPercentage = integerPercentages.sum()
        val diff = 100 - totalPercentage

        // 步骤4: 按照百分比大小对列表排序，以便优先调整较大的百分比项
        val sortedPercentages = integerPercentages.withIndex().sortedByDescending { it.value }

        val list = mutableListOf<Int>()

        var remainingDiff = diff
        val finalPercentages = integerPercentages.toMutableList()

        // 步骤5: 分配误差
        // 逐个调整百分比，优先分配给较大的项
        for (i in sortedPercentages.indices) {
            if (remainingDiff == 0) break
            val index = sortedPercentages[i].index  // 获取原始数据的索引

            // 如果剩余误差较小，可以每次调整 1 或更小的值
            val increment = maxOf(minOf(remainingDiff, 1), -1) // 确保每次调整不过小或过大
            finalPercentages[index] += increment
            remainingDiff -= increment
        }

        // 如果误差没有完全分配完，将剩余误差分配给最后一项
        if (remainingDiff > 0.0) {
            finalPercentages[finalPercentages.size - 1] += remainingDiff
        }

        percentList.clear()
        percentList.addAll(finalPercentages)

    }


}

