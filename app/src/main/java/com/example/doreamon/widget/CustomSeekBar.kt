package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.doreamon.treasure.ext.dp

class CustomSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0 // 初始进度，默认为50
    private var maxProgress = 100 // 最大进度，默认为100

    private var minProgress = 0

    val currProgress: Int
        get() = progress + minProgress


    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#9F78E2")
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 6f.dp
    }

    var onProgressChanged: (progress: Int, fromUser: Boolean) -> Unit = { _, _ ->
    }

    private val horizontalMargin = 10.dp


    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 6f.dp
    }

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#B990FF")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(200.dp, widthMeasureSpec)
        val height = resolveSize(90.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2f

        var progressWidth = if (maxProgress - minProgress == 0) {
            (width - horizontalMargin * 2 - 20f.dp).toInt()
        } else {
            (progress * (width - horizontalMargin * 2 - 20f.dp)).toInt() / (maxProgress - minProgress)
        }

        canvas.drawLine(
            10f.dp + horizontalMargin,
            centerY,
            width.toFloat() - 10f.dp - horizontalMargin,
            centerY,
            bgPaint
        )

        val progressX = 10f.dp + horizontalMargin + progressWidth
        canvas.drawLine(
            10f.dp + horizontalMargin,
            centerY,
            progressX,
            centerY,
            progressPaint
        )

        thumbPaint.color = Color.parseColor("#66B083FF")
        canvas.drawCircle(progressX, centerY, 8f.dp, thumbPaint)
        thumbPaint.color = Color.parseColor("#B990FF")
        canvas.drawCircle(progressX, centerY, 4f.dp, thumbPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x

                progress = if (maxProgress - minProgress == 0) {
                    0
                } else if (x <= horizontalMargin + 10f.dp) {
                    0
                } else if (x >= width.toFloat() - horizontalMargin - 10f.dp) {
                    maxProgress - minProgress
                } else {
                    val fl = width - horizontalMargin * 2 - 20f.dp
                    ((x - horizontalMargin - 10f.dp) * (maxProgress - minProgress + 1) / fl).toInt()
                }

                onProgressChanged(currProgress, true)

                invalidate()
                return true
            }

        }
        return super.onTouchEvent(event)
    }

    fun setProgress(progress: Int) {
        this.progress = progress.coerceIn(0, maxProgress)
        invalidate()
    }

    fun setProgressRange(minProgress: Int, maxProgress: Int) {
        this.minProgress = minProgress
        this.maxProgress = maxProgress
        if (maxProgress - minProgress < 0) {
            return
        }
        invalidate()
    }

}