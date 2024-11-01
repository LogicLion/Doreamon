package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.util.AttributeSet
import android.view.View
import com.doreamon.treasure.ext.dp
import kotlin.math.min

/**
 * 答题正确率控件
 */
class CorrectPercentView @JvmOverloads constructor(
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
    private val blurMaskFilter = BlurMaskFilter(30f.dp, BlurMaskFilter.Blur.OUTER)
    private val progressBlurMaskFilter = BlurMaskFilter(10f.dp, BlurMaskFilter.Blur.NORMAL)

    private val blurMaskPaint = Paint().apply {
        isAntiAlias = true
    }

    private val metrics: FontMetrics by lazy {
        paint.fontMetrics
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
            centerPointX, centerPointY, Color.parseColor("#25A6FB"),
            Color.parseColor("#64C1FF")
        )
    }


    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent: Int = 0


    private var rangeStrokeWidth = 4f.dp

    private var titleText = "答题正确率"


    /**
     * 设置百分比(0-100)
     */
    fun setPercent(percent: Int) {
        if (percent in 0..100) {
            this.percent = percent

            invalidate()
        }
    }

    fun setText(text: String) {

        this.titleText = text
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(100.dp, widthMeasureSpec)
        val height = resolveSize(100.dp, heightMeasureSpec)
        val minWidth = min(width, height)
        setMeasuredDimension(minWidth, minWidth)
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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = viewHeight / 2
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
            viewHeight / 2 - rangeStrokeWidth / 2,
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

        progressPaint.shader = gradient

        canvas.drawArc(
            width / 2 - height.toFloat() / 2 + progressMargin,
            progressMargin,
            width / 2 + height.toFloat() / 2 - progressMargin,
            height.toFloat() - progressMargin,
            90f,
            percent * 360f / 100f,
            false,
            progressPaint
        )


        drawFillTitle(canvas)
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