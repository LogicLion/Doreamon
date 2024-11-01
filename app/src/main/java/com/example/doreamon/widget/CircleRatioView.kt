package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.doreamon.treasure.ext.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 *圆形占比控件
 */
class CircleRatioView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {


    private val bgPaint = Paint().apply {
        isAntiAlias = true
    }
    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = 34f.dp
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }

    private val progressStrokeWidth = 30f.dp
    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = progressStrokeWidth
    }

    private val mMatrix: Matrix by lazy {
        Matrix()
    }

    private val mMatrix1: Matrix by lazy {
        Matrix()
    }

    private val blurMaskFilter = BlurMaskFilter(30f.dp, BlurMaskFilter.Blur.OUTER)
    private val progressBlurMaskFilter = BlurMaskFilter(10f.dp, BlurMaskFilter.Blur.NORMAL)

    private val blurMaskPaint = Paint().apply {
        isAntiAlias = true
    }

    private val metrics: FontMetrics by lazy {
        paint.fontMetrics
    }

    private val linePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 2f.dp
        }
    }

    private val titlePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            textSize = 13f.dp
            color = Color.parseColor("#6C8393")
            textAlign = Paint.Align.CENTER
        }
    }

    private val titleMetrics: FontMetrics by lazy {
        titlePaint.fontMetrics
    }

    private val gradient: SweepGradient by lazy {
        SweepGradient(
            centerPointX,
            centerPointY,
            colorsArray1,
            null
        )
    }


    private val gradient1: SweepGradient by lazy {
        SweepGradient(
            centerPointX,
            centerPointY,
            colorsArray2,
            null
        )
    }

    private var color1 = Color.parseColor("#25A6FB")
    private var color2 = Color.parseColor("#FFA40A")

    private var colorsArray1 = intArrayOf(
        Color.parseColor("#25A6FB"),
        Color.parseColor("#64C1FF"),
        Color.parseColor("#25A6FB")
    )

    private var colorsArray2 = intArrayOf(
        Color.parseColor("#FFA933"),
        Color.parseColor("#FD8241"),
        Color.parseColor("#FFA933")
    )
    private val color666666 = Color.parseColor("#666666")


    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent: Int = 0


    private var rangeStrokeWidth = 4f.dp

    private var titleText = "掌握率"

    private var line1 = Path()
    private var line2 = Path()

    private val decTextPaint = Paint().apply {
        isAntiAlias = true
        textSize = 16f.dp
    }

    private var decText1 = "实测掌握：3个知识点"
    private var decText2 = "实测未掌握：3个知识点"

    private var ratioVal1 = 0
    private var ratioVal2 = 0

    private val decMetrics: FontMetrics by lazy {
        decTextPaint.fontMetrics
    }

    /**
     * 设置百分比(0-100)
     */
    fun setPercent(percent: Int) {
        if (percent in 0..100) {
            this.percent = percent

            invalidate()
        }
    }

    fun setRatio(val1: Int, val2: Int) {
        if (val1 + val2 > 0) {
            this.percent = (val1 * 100 / (val1 + val2)).toInt()
            ratioVal1 = val1
            ratioVal2 = val2
            invalidate()
        }
    }

    fun setMarkColor1(color: Int) {
        this.color1 = color
        invalidate()
    }

    fun setMarkColor2(color: Int) {
        this.color2 = color
        invalidate()
    }


    fun setProgressBarColor1(@ColorInt colors: IntArray) {
        this.colorsArray1 = colors
        invalidate()
    }

    fun setProgressBarColor2(@ColorInt colors: IntArray) {
        this.colorsArray2 = colors
        invalidate()
    }

    fun setText(text: String) {
        this.titleText = text
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(100.dp, widthMeasureSpec)
        val height = resolveSize(100.dp, heightMeasureSpec)
//        val minWidth = min(width, height)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w.toFloat()
        viewHeight = h.toFloat()

        centerPointX = viewWidth / 2
        centerPointY = viewHeight / 2

        //matrix必须在onDraw方法调用前设置，否则不生效
        mMatrix.reset()
        mMatrix.setRotate(90f, centerPointX, centerPointY)
        gradient.setLocalMatrix(mMatrix)

        mMatrix1.reset()
        mMatrix1.setRotate(270f, centerPointX, centerPointY)
        gradient1.setLocalMatrix(mMatrix1)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = viewHeight / 2 - 30f.dp
        bgPaint.shader = null
        bgPaint.style = Paint.Style.FILL
        bgPaint.color = Color.parseColor("#F8F9FF")
        canvas.drawCircle(centerPointX, centerPointY, radius, bgPaint)

        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = rangeStrokeWidth
        bgPaint.color = Color.parseColor("#EBEEFE")
        canvas.drawCircle(
            centerPointX,
            centerPointY,
            radius - rangeStrokeWidth / 2,
            bgPaint
        )

        bgPaint.color = Color.WHITE
        bgPaint.style = Paint.Style.FILL
        canvas.drawCircle(centerPointX, centerPointY, radius, bgPaint)

        bgPaint.color = Color.parseColor("#1a25A6FB")
        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = 2f.dp
        canvas.drawCircle(centerPointX, centerPointY, radius - 1f.dp, bgPaint)

        val progressMargin = progressStrokeWidth / 2 + 6f.dp

        blurMaskPaint.color = Color.parseColor("#64C1FF")
        blurMaskPaint.maskFilter = blurMaskFilter
        canvas.drawCircle(
            centerPointX,
            centerPointY,
            radius - progressStrokeWidth - 6f.dp,
            blurMaskPaint
        )
        drawFillTitle(canvas)

        if (ratioVal1 + ratioVal2 <= 0) {
            return
        }


        progressPaint.shader = gradient

        val rotationAngle = percent * 360f / 100f
        canvas.drawArc(
            width / 2 - radius + progressMargin,
            centerPointY - radius + progressMargin,
            width / 2 + radius - progressMargin,
            centerPointY + radius - progressMargin,
            90f,
            rotationAngle,
            false,
            progressPaint
        )

        progressPaint.shader = gradient1

        canvas.drawArc(
            width / 2 - radius + progressMargin,
            centerPointY - radius + progressMargin,
            width / 2 + radius - progressMargin,
            centerPointY + radius - progressMargin,
            90 + rotationAngle,
            360 - rotationAngle,
            false,
            progressPaint
        )

        linePaint.color = Color.WHITE
        val angle = Math.toRadians(90 + rotationAngle.toDouble())
        val sin = sin(angle).toFloat()
        val cos = cos(angle).toFloat()

        val startX = centerPointX + (radius - progressStrokeWidth - 6f.dp) * cos
        val startY = centerPointY + (radius - progressStrokeWidth - 6f.dp) * sin
        val endX = centerPointX + (radius - 6f.dp) * cos
        val endY = centerPointY + (radius - 6f.dp) * sin
        canvas.drawLine(startX, startY, endX, endY, linePaint)

        canvas.drawLine(
            centerPointX,
            centerPointY + radius - progressStrokeWidth - 6f.dp,
            centerPointX,
            centerPointY + radius - 6f.dp,
            linePaint
        )


        line1.reset()
        linePaint.color = color1
        val directAngle1 = Math.toRadians(90 + rotationAngle.toDouble() / 2)
        val sin1 = sin(directAngle1).toFloat()
        val cos1 = cos(directAngle1).toFloat()

        val startX1 = centerPointX + (radius - 6f.dp) * cos1
        val startY1 = centerPointY + (radius - 6f.dp) * sin1
        val endX1 = centerPointX + (radius + 15f.dp) * cos1
        val endY1 = centerPointY + (radius + 15f.dp) * sin1
        line1.moveTo(startX1, startY1)
        line1.lineTo(endX1, endY1)
        line1.lineTo(endX1 - 40f.dp, endY1)
        canvas.drawPath(line1, linePaint)

        val decText1 = "实测掌握："
        val decText2 = ratioVal1.toString()
        val decText3 = "个知识点"
        val decTextWidth1 = decTextPaint.measureText(decText1)
        val decTextWidth2 = decTextPaint.measureText(decText2)
        val decTextWidth3 = decTextPaint.measureText(decText3)

        decTextPaint.getFontMetrics(decMetrics)
        decTextPaint.color = color666666
        canvas.drawText(
            decText1,
            endX1 - decTextWidth1 - decTextWidth2 - decTextWidth3 - 40f.dp,
            endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )

        decTextPaint.color = color1
        canvas.drawText(
            decText2,
            endX1 - decTextWidth2 - decTextWidth3 - 40f.dp,
            endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )

        decTextPaint.color = color666666
        canvas.drawText(
            decText3,
            endX1 - decTextWidth3 - 40f.dp,
            endY1 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )


        line2.reset()
        linePaint.color = color2
        val directAngle2 =
            Math.toRadians(90 + rotationAngle.toDouble() + (360f - rotationAngle) / 2)
        val sin2 = sin(directAngle2).toFloat()
        val cos2 = cos(directAngle2).toFloat()

        val startX2 = centerPointX + (radius - 6f.dp) * cos2
        val startY2 = centerPointY + (radius - 6f.dp) * sin2
        val endX2 = centerPointX + (radius + 15f.dp) * cos2
        val endY2 = centerPointY + (radius + 15f.dp) * sin2
        line2.moveTo(startX2, startY2)
        line2.lineTo(endX2, endY2)
        line2.lineTo(endX2 + 40f.dp, endY2)
        canvas.drawPath(line2, linePaint)

        val decText4 = "实测未掌握："
        val decText5 = ratioVal2.toString()
        val decText6 = "个知识点"
        val decTextWidth4 = decTextPaint.measureText(decText4)
        val decTextWidth5 = decTextPaint.measureText(decText5)
        val decTextWidth6 = decTextPaint.measureText(decText6)

        decTextPaint.getFontMetrics(decMetrics)
        decTextPaint.color = color666666
        canvas.drawText(
            decText4,
            endX2 + 40f.dp,
            endY2 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )

        decTextPaint.color = color2
        canvas.drawText(
            decText5,
            endX2 + decTextWidth4 + 40f.dp,
            endY2 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )

        decTextPaint.color = color666666
        canvas.drawText(
            decText6,
            endX2 + decTextWidth4 + decTextWidth5 + 40f.dp,
            endY2 - (decMetrics.descent + decMetrics.ascent) / 2,
            decTextPaint
        )


    }


    private fun drawFillTitle(canvas: Canvas) {

        paint.color = Color.parseColor("#333333")

        val textPercent = "${percent}%"

        paint.getFontMetrics(metrics)

        titlePaint.getFontMetrics(titleMetrics)
        val textVerticalMargin = 12f.dp

        canvas.drawText(
            textPercent,
            centerPointX,
            centerPointY - (titleMetrics.descent + titleMetrics.ascent) / 2 - textVerticalMargin / 2,
            paint
        )

        val textBgWidth = titlePaint.measureText(titleText) + 16f.dp
        val textBgHeight = 26f.dp

        paint.color = Color.parseColor("#EFF8FF")

        canvas.drawRoundRect(
            centerPointX - textBgWidth / 2,
            centerPointY - (metrics.descent + metrics.ascent) / 2 + textVerticalMargin / 2 - textBgHeight / 2,
            centerPointX + textBgWidth / 2,
            centerPointY - (metrics.descent + metrics.ascent) / 2 + textVerticalMargin / 2 + textBgHeight / 2,
            13f.dp,
            13f.dp,
            paint
        )

        canvas.drawText(
            titleText,
            centerPointX,
            centerPointY - (metrics.descent + metrics.ascent + titleMetrics.descent + titleMetrics.ascent) / 2 + textVerticalMargin / 2,
            titlePaint
        )

    }
}