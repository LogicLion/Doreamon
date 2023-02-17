package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.doreamon.treasure.ext.dp

/**
 * 环状图
 * @author wzh
 */
class RingChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {

    val bgPaint = Paint()
    private val paint = Paint()

    var centerPointX: Float = 0f
    var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent: Int = 0

    val rect = Rect()

    init {
        bgPaint.isAntiAlias = true
        paint.isAntiAlias = true
        paint.textSize = 22f.dp
        paint.isFakeBoldText = true
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
     * 设置百分比
     */
    fun setPercent(percent: Int) {
        if (percent < 0 || percent > 100) {
            return
        }
        this.percent = percent
        animator.start()
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

        bgPaint.style = Paint.Style.FILL
        bgPaint.color = Color.parseColor("#F8F9FF")
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2, bgPaint)

        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = 4f.dp
        bgPaint.color = Color.parseColor("#EBEEFE")
        canvas.drawCircle(centerPointX, centerPointY, viewHeight / 2 - 2f.dp, bgPaint)


        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#FEAE54")
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
        val textWidth = paint.measureText(text)
        paint.getTextBounds(text, 0, text.length, rect)
        val textHeight = rect.bottom - rect.top
        canvas.drawText(text, centerPointX - textWidth / 2, centerPointY + textHeight / 2, paint)

    }
}