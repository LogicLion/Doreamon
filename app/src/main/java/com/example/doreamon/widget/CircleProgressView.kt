package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R
import com.example.doreamon.widget.CircleProgressView.Type.*
import kotlin.math.min

/**
 * 圆形进度条
 * @author wzh
 */
class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {

    enum class Type {
        /**
         * 默认样式
         */
        DEFAULT,

        /**
         * 显示百分比的样式
         */
        FILL_PERCENT,

        /**
         * 显示百分比+标题的样式
         */
        FILL_TITLE
    }

    var chartType = DEFAULT
        set(value) {
            field = value

            resetCommonAttribute()
            invalidate()
        }

    private val bgPaint = Paint().apply {
        isAntiAlias = true
    }
    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = 22f.dp
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }

    private val metrics: FontMetrics by lazy {
        paint.fontMetrics
    }

    private val symbolPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            textSize = 14f.dp
            color = Color.WHITE
        }
    }

    private val symbolMetrics: FontMetrics by lazy {
        symbolPaint.fontMetrics
    }

    private val titlePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            textSize = 13f.dp
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
        }
    }

    private val titleMetrics: FontMetrics by lazy {
        titlePaint.fontMetrics
    }

    private val linearGradient: LinearGradient by lazy {
        LinearGradient(
            centerPointX,
            0f,
            centerPointX,
            viewHeight,
            Color.parseColor("#23CD92"),
            Color.parseColor("#2AC1AE"),
            Shader.TileMode.CLAMP
        )
    }

    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent: Int = 0


    private var rangeStrokeWidth = 4f.dp

    init {
        initAttrs(context, attrs)
    }

    @Keep
    private var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
    }

    /**
     * 设置百分比(0-100)
     */
    fun setPercent(percent: Int) {
        if (percent in 0..100) {
            this.percent = percent
            animator.start()
        }
    }


    /**
     * 部分属性在各个[CircleProgressView.Type]是共用的,在[CircleProgressView.Type]切换的时候,必须重置
     * 否则在recyclerView的复用模式中会错乱
     */
    private fun resetCommonAttribute() {

        when (chartType) {
            DEFAULT -> {
                rangeStrokeWidth = 4f.dp
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = 22f.dp
            }
            FILL_PERCENT -> {
                rangeStrokeWidth = 4f.dp
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = 22f.dp
            }
            FILL_TITLE -> {
                rangeStrokeWidth = 6f.dp
                paint.textAlign = Paint.Align.LEFT
                paint.textSize = 28f.dp
            }
        }

    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        paint.color = attributes.getColor(
            R.styleable.CircleProgressView_ring_bar_color,
            Color.parseColor("#2ac1ae")
        )
        when (attributes.getInt(R.styleable.CircleProgressView_circle_bar_style, 0)) {
            0 -> chartType = DEFAULT
            1 -> chartType = FILL_PERCENT
            2 -> chartType = FILL_TITLE
        }

        attributes.recycle()

        resetCommonAttribute()
    }

    fun setProgressBarColor(color: Int) {
        paint.color = color
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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bgPaint.shader = null
        bgPaint.style = Paint.Style.FILL
        bgPaint.color = Color.parseColor("#F8F9FF")
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2, bgPaint)

        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = rangeStrokeWidth
        bgPaint.color = Color.parseColor("#EBEEFE")
        canvas.drawCircle(
            centerPointX,
            centerPointY,
            viewHeight / 2 - rangeStrokeWidth / 2,
            bgPaint
        )

        when (chartType) {
            DEFAULT -> drawDefault(canvas)
            FILL_PERCENT -> drawFillPercent(canvas)
            FILL_TITLE -> drawFillTitle(canvas)
        }
    }


    private fun drawDefault(canvas: Canvas) {
        canvas.drawArc(
            rangeStrokeWidth,
            rangeStrokeWidth,
            viewWidth - rangeStrokeWidth,
            viewHeight - rangeStrokeWidth,
            -90f,
            360f * percent / 100 * animRate / 100,
            true,
            paint
        )

        bgPaint.style = Paint.Style.FILL
        bgPaint.color = Color.WHITE
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - 15f.dp, bgPaint)

        val text = "${percent * animRate / 100}%"
        paint.getFontMetrics(metrics)

        //居中绘制，这里descent和ascent应该理解为偏移量，因为文字的绘制x的初始点还是在baseline位置
        canvas.drawText(
            text,
            centerPointX,
            centerPointY - (metrics.descent + metrics.ascent) / 2,
            paint
        )
    }


    private fun drawFillPercent(canvas: Canvas) {

        bgPaint.shader = linearGradient
        bgPaint.style = Paint.Style.FILL

        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - rangeStrokeWidth, bgPaint)

        paint.color = Color.WHITE
        val text = "${percent * animRate / 100}%"
        paint.getFontMetrics(metrics)

        canvas.drawText(
            text,
            centerPointX,
            centerPointY - (metrics.descent + metrics.ascent) / 2,
            paint
        )
    }


    private fun drawFillTitle(canvas: Canvas) {
        bgPaint.shader = linearGradient
        bgPaint.style = Paint.Style.FILL

        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - rangeStrokeWidth, bgPaint)

        paint.color = Color.WHITE

        val textPercent = "${percent * animRate / 100}"
        val textSymbol = "%"
        val textTitle = "正确率"

        paint.getFontMetrics(metrics)
        val textPercentWidth = paint.measureText(textPercent)

        symbolPaint.getFontMetrics(symbolMetrics)
        val textSymbolWidth = symbolPaint.measureText(textSymbol)

        titlePaint.getFontMetrics(titleMetrics)
        val textVerticalMargin = 8f.dp

        canvas.drawText(
            textPercent,
            centerPointX - (textPercentWidth + textSymbolWidth) / 2,
            centerPointY - (titleMetrics.descent + titleMetrics.ascent) / 2 - textVerticalMargin / 2,
            paint
        )
        canvas.drawText(
            textSymbol,
            centerPointX - (textPercentWidth + textSymbolWidth) / 2 + textPercentWidth,
            centerPointY - (titleMetrics.descent + titleMetrics.ascent) / 2 - textVerticalMargin / 2,
            symbolPaint
        )
        canvas.drawText(
            textTitle,
            centerPointX,
            centerPointY - (metrics.descent + metrics.ascent + titleMetrics.descent + titleMetrics.ascent) / 2 + textVerticalMargin / 2,
            titlePaint
        )

    }
}