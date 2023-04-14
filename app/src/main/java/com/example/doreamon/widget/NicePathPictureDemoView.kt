package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.NinePatch
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.example.doreamon.R

/**
 * .9图在自定义控件中使用演示
 * @author wzh
 * @date 2023/3/30
 */
class NinePathPictureDemoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    @Keep
    var animRate = 0
        set(value) {
            field = value
            invalidate()
        }

    val rect = Rect()

    private val ninePatch: NinePatch by lazy {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_progress_bar_2)
        return@lazy NinePatch(bitmap, bitmap.ninePatchChunk, null)
    }

    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        animator.startDelay = 500L
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.right = (width - 10) * animRate / 100 + 10
        rect.bottom = height
        ninePatch.draw(canvas, rect)


    }

}