package com.example.doreamon.widget

import android.animation.Animator
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
 * 定制目标进度饼状图
 * @author wzh
 * @date 2023/12/26
 */
class CustomTargetPieView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs) {


    private val bgPaint1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#DEEBFC")
    }

    private val bgPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#C9E0FF")
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#62A0F4")
        style = Paint.Style.FILL
    }

    var onAnimUpdateListener: (progress: Int) -> Unit =
        { progress -> }

    @Keep
    var progress: Int = 0
        set(value) {
            field = value
            animator.start()
        }

    @Keep
    var progressFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator: Animator by lazy {
        val animator1 = ObjectAnimator.ofFloat(this, "progressFraction", 0f, 100f).setDuration(1000)
        animator1.addUpdateListener {
            onAnimUpdateListener.invoke((it.animatedFraction * 100).toInt())
        }
        return@lazy animator1
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val center = width.toFloat() / 2

        canvas.drawCircle(center, center, 185f.dp / 2, bgPaint1)
        canvas.drawCircle(center, center, 160f.dp / 2, bgPaint2)

        val sweepAngle = 360 * progress * progressFraction / 100 / 100
        val startAngle = 270 - sweepAngle
        canvas.drawArc(
            center - 195f.dp / 2,
            center - 195f.dp / 2,
            center + 195f.dp / 2,
            center + 195f.dp / 2,
            startAngle,
            sweepAngle,
            true,
            paint
        )
    }


}