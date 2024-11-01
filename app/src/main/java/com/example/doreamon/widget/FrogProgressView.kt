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
 * 自定义蛙蛙logo进度条
 * @author wzh
 * @date 2023/11/29
 */
class FrogProgressView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var bgBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.bg_progress_bar)
    private var logoBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.icon_progress_point_2)
    private lateinit var srcRect: Rect
    private lateinit var destRect: Rect
    private lateinit var logoSrcRect: Rect
    private lateinit var logoDestRect: Rect

    @Keep
    var progress: Int = 0
        set(value) {
            field = value
            startAnimator()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    @Keep
    private var progressFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }


    private val animator: Animator by lazy {
        val animator1 = ObjectAnimator.ofFloat(this, "progressFraction", 0f, 100f).setDuration(3000)
        animator1.addUpdateListener {
            onAnimUpdateListener.invoke((it.animatedFraction * 100).toInt())
        }
        return@lazy animator1
    }

    private fun startAnimator() {
        animator.start()
    }

    var onAnimUpdateListener: (progress: Int) -> Unit =
        { progress -> }
//
//    override fun onAnimationRunning(fraction: Float) {
//        progressFraction = fraction * 100
//        val progressInt = (fraction * 100).toInt()
//        if (progressInt > currProgress) {
//            currProgress = progressInt
//            onAnimUpdateListener.invoke(currProgress)
//        }
//        invalidate()
//    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        srcRect = Rect(0, 0, w, 8.dp)
        destRect = Rect(0, h / 2 - 4.dp, w, h / 2 + 4.dp)
        logoSrcRect = Rect(0, 0, 30.dp, 30.dp)
        logoDestRect = Rect(0, 0, logoBitmap.width, logoBitmap.height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, height / 2 - 5f.dp, width.toFloat(), height / 2 + 5f.dp, paint)
        val progressWidth = (progressFraction * progress * width / 100 / 100).toInt()
        bgBitmap.let {
            srcRect.right = (progressFraction * it.width / 100 / 100).toInt()
            destRect.right = progressWidth
            canvas.drawBitmap(it, srcRect, destRect, null)
        }

        logoBitmap.let {
            logoDestRect.left = progressWidth - 15.dp
            logoDestRect.right = progressWidth + 15.dp
            canvas.drawBitmap(it, logoSrcRect, logoDestRect, null)
        }
    }

}