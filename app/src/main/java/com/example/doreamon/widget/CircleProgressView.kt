package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

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
        DEFAULT, FILL_1, FILL_2
    }

    var chartType = Type.DEFAULT
        set(value) {
            field = value
            invalidate()
        }

    private val fontMetrics = Paint.FontMetrics()
    private val bgPaint = Paint().apply {
        isAntiAlias = true
    }
    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = 22f.dp
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }

    var centerPointX: Float = 0f
    var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent: Int = 0

    val rect = Rect()

    init {
        initAttrs(context, attrs)
    }

    @Keep
    var progressRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "progressRate", 0, 100).setDuration(500)
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


    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        paint.color = attributes.getColor(
            R.styleable.CircleProgressView_ring_bar_color,
            Color.parseColor("#2ac1ae")
        )
        attributes.recycle()
    }

    fun setProgressBarColor(color: Int) {
        paint.color = color
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            100.dp
        }

        setMeasuredDimension(measuredWidth, measuredWidth)
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
        bgPaint.strokeWidth = 4f.dp
        bgPaint.color = Color.parseColor("#EBEEFE")
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - 2f.dp, bgPaint)

        when (chartType) {
            Type.DEFAULT -> drawDefault(canvas)
            Type.FILL_1 -> drawFill1(canvas)
            Type.FILL_2 -> drawFill2(canvas)
        }
    }


    private fun drawDefault(canvas: Canvas) {


        canvas.drawArc(
            4f.dp,
            4f.dp,
            viewWidth - 4f.dp,
            viewHeight - 4f.dp,
            -90f,
            360f * percent / 100 * progressRate / 100,
            true,
            paint
        )

        bgPaint.style = Paint.Style.FILL
        bgPaint.color = Color.WHITE
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - 15f.dp, bgPaint)

        val text = "${percent * progressRate / 100}%"
        paint.getTextBounds(text, 0, text.length, rect)
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            text,
            centerPointX,
            centerPointY - (fontMetrics.descent + fontMetrics.ascent) / 2,
            paint
        )
    }


    private fun drawFill1(canvas: Canvas) {

        val gradient = LinearGradient(
            centerPointX,
            0f,
            centerPointX,
            viewHeight,
            Color.parseColor("#23CD92"),
            Color.parseColor("#2AC1AE"),
            Shader.TileMode.CLAMP
        )
        bgPaint.shader = gradient
        bgPaint.style = Paint.Style.FILL


        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - 4f.dp, bgPaint)


        paint.color = Color.WHITE
        val text = "${percent * progressRate / 100}%"
        paint.getTextBounds(text, 0, text.length, rect)
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            text,
            centerPointX,
            centerPointY - (fontMetrics.descent + fontMetrics.ascent) / 2,
            paint
        )

    }


    private fun drawFill2(canvas: Canvas) {
    }
}