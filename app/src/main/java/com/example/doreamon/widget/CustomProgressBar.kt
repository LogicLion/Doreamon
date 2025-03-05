package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.doreamon.treasure.ext.dp

class CustomProgressBar @JvmOverloads constructor(
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
        color = Color.parseColor("#FFC339")
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f.dp
    }

    var onProgressChanged: (progress: Int, fromUser: Boolean) -> Unit = { _, _ ->
    }

    private val horizontalMargin = 0.dp


    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#7C95AF")
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f.dp
    }

    private val bgWhitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f.dp
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
            bgWhitePaint
        )

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