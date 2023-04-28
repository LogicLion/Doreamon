package com.example.doreamon.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.doreamon.treasure.ext.dp

/**
 * 圆形百分比控件（仅支持2个占比分类）
 * @author wzh
 */
class CirclePercentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {

    private var centerPointX: Float = 0f
    private var centerPointY: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f
    private var percent1: Int = 0
    private var percent2: Int = 0
    private var percentTitle1: String = ""
    private var percentTitle2: String = ""

    private val ringWidth = 30f.dp
    private val circleRadius = 93f.dp
    private val rangeStrokeWidth = 6f.dp


    private val bgPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#EBEEFF")
        style = Paint.Style.FILL
    }
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }


    private val textPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            textSize = 13f.dp
        }
    }


    @Keep
    private var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    @Keep
    private var textAnimRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animatorSet: AnimatorSet by lazy {
        val set = AnimatorSet()
        val animator1 = ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
        val animator2 = ObjectAnimator.ofInt(this, "textAnimRate", 0, 100).setDuration(300)
        set.playSequentially(animator1, animator2)
        return@lazy set
    }

    /**
     * 设置比重，percent1+percent2必须等于100
     */
    fun setProportion(
        percent1: Int,
        percentTitle1: String,
        percent2: Int,
        percentTitle2: String
    ) {
        if (percent1 < 0 || percent2 < 0 || (percent1 + percent2) != 100) {
            return
        }

        this.percent1 = percent1
        this.percent2 = percent2
        this.percentTitle1 = "${percentTitle1}${percent1}%"
        this.percentTitle2 = "${percentTitle2}${percent2}%"

        if (animatorSet.isRunning) {
            animatorSet.cancel()
        }
        animatorSet.start()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(200.dp, widthMeasureSpec)
        val height = resolveSize(240.dp, heightMeasureSpec)
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

        if (percent1 == 0 && percent2 == 0) return

        canvas.drawCircle(centerPointX, centerPointY, circleRadius, bgPaint)

        val outerRadius = circleRadius - rangeStrokeWidth

        paint.color = Color.parseColor("#FFC146")
        val sweepAngle = percent1 * 360f / 100

        //这里采用useCenter为true的闭合扇形处理，而不是使用圆弧+paint描粗的处理方式
        // （实测android低版本会出现描粗后，描粗端口的角度会对不上的情况）
        if (animRate <= percent1) {
            canvas.drawArc(
                centerPointX - outerRadius,
                centerPointY - outerRadius,
                centerPointX + outerRadius,
                centerPointY + outerRadius,
                -90f,
                sweepAngle * animRate / percent1,
                true,
                paint
            )
        } else {
            canvas.drawArc(
                centerPointX - outerRadius,
                centerPointY - outerRadius,
                centerPointX + outerRadius,
                centerPointY + outerRadius,
                -90f,
                sweepAngle,
                true,
                paint
            )

            paint.color = Color.parseColor("#2AC1AE")

            canvas.drawArc(
                centerPointX - outerRadius,
                centerPointY - outerRadius,
                centerPointX + outerRadius,
                centerPointY + outerRadius,
                -90f + sweepAngle,
                (360f - sweepAngle) * (animRate - percent1) / (100 - percent1),
                true,
                paint
            )
        }

        val innerRadius = outerRadius - ringWidth
        canvas.drawCircle(centerPointX, centerPointY, innerRadius, bgPaint)


        textPaint.color = Color.parseColor("#FFC146")

        val animTranslationOffset = (10f.dp - textAnimRate * 10f.dp / 100)
        if (animRate == 100) {
            textPaint.alpha = textAnimRate * 255 / 100
            if (percent1 <= 50) {
                canvas.drawText(
                    percentTitle1,
                    centerPointX + 50f.dp - animTranslationOffset,
                    centerPointY - 90f.dp,
                    textPaint
                )
            } else {
                canvas.drawText(
                    percentTitle1,
                    centerPointX + 50f.dp - animTranslationOffset,
                    centerPointY + 90f.dp,
                    textPaint
                )
            }

            textPaint.color = Color.parseColor("#2AC1AE")
            textPaint.alpha = textAnimRate * 255 / 100
            val textWidth = textPaint.measureText(percentTitle2)
            if (percent2 < 50) {
                canvas.drawText(
                    percentTitle2,
                    centerPointX - 50f.dp - textWidth + animTranslationOffset,
                    centerPointY - 90f.dp,
                    textPaint
                )
            } else {
                canvas.drawText(
                    percentTitle2,
                    centerPointX - 50f.dp - textWidth + animTranslationOffset,
                    centerPointY + 90f.dp,
                    textPaint
                )
            }
        }

    }


}