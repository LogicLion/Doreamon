package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.doreamon.treasure.ext.dp

/**
 * @author wzh
 * @date 2023/8/2
 */
class DraggableProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 50 // 初始进度，默认为50
    private var maxProgress = 100 // 最大进度，默认为100
    private var progressColor = Color.BLUE // 进度条颜色
    private var thumbColor = Color.RED // 拖动块颜色

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = progressColor
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = thumbColor
    }

    private val thumbRadius = 10f.dp // 拖动块的半径

    private val progressRect = RectF() // 进度条的矩形

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // 绘制进度条
        progressRect.set(
            0f,
            0f,
            width.toFloat() * progress / maxProgress,
            height.toFloat()
        )
        canvas.drawRect(progressRect, progressPaint)

        // 绘制拖动块
        val thumbX = width.toFloat() * progress / maxProgress
        canvas.drawCircle(thumbX, height.toFloat() - thumbRadius, thumbRadius, thumbPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val newProgress = (x * maxProgress / width).toInt()
                progress = newProgress.coerceIn(0, maxProgress)
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

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
        invalidate()
    }
}