package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.example.doreamon.R

/**
 * 自定义目标进度条
 * @author wzh
 * @date 2023/11/29
 */
class CustomTargetProgressView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs) {

    private var bgBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.bg_target_progress)
    private lateinit var srcRect: Rect
    private lateinit var destRect: Rect


    @Keep
    var progress: Int = 0
        set(value) {
            field = value
            animator.start()
        }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#3D62A0F4")
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


    fun startAnimator() {
        animator.start()
    }

    var onAnimUpdateListener: (progress: Int) -> Unit =
        { progress -> }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        srcRect = Rect(0, 0, w, bgBitmap.height)
        destRect = Rect(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), bgPaint)
        val bitmapProgressWidth = (progress * progressFraction * bgBitmap.width / 100 / 100).toInt()
        val progressWidth = (progress * progressFraction * width / 100 / 100).toInt()
        bgBitmap.let {
            srcRect.right = bitmapProgressWidth
            destRect.right = progressWidth
            canvas.drawBitmap(it, srcRect, destRect, null)
        }

    }

}