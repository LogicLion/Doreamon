package com.example.doreamon.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

/**
 * 可拖拽伸缩的layout
 * @author wzh
 * @date 2024/9/7
 */
class ElasticallyLayout @JvmOverloads constructor(
    private val mContext: Context, private val attr: AttributeSet? = null, val defStyle: Int = 0
) : ConstraintLayout(mContext, attr) {

    private var logoBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.icon_draggable)

    private var logoSrcRect: Rect
    private var logoDestRect: Rect

    init {
        setWillNotDraw(false)
        logoSrcRect = Rect(0, 0, 36.dp, 54.dp)
        logoDestRect = Rect(0, 0, logoBitmap.width, logoBitmap.height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (isDragging) {
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightSize = MeasureSpec.getSize(heightMeasureSpec)
            setMeasuredDimension(newWidth.toInt(), newHeight.toInt())
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        logoBitmap.let {
            logoDestRect.left = width - 51.dp
            logoDestRect.right = width - 15.dp
            logoDestRect.top = height / 2 - 27.dp
            logoDestRect.bottom = height / 2 + 27.dp
            canvas.drawBitmap(it, logoSrcRect, logoDestRect, null)
        }
    }

    private var isDragging = false

    var startX: Float = 0f
    var startY: Float = 0f

    var newWidth: Float = 0f
    var newHeight: Float = 0f

    var initialWidth = 0f
    var initialHeight = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (inAnchorArea(event.x, event.y)) {
                    startX = event.x
                    startY = event.y
                    initialWidth = width.toFloat()
                    initialHeight = height.toFloat()
                    isDragging = true
                    return true
                } else {
                    return super.onTouchEvent(event)
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {

                    val dx = event.x - startX
                    val dy = event.y - startY
                    newWidth = initialWidth + dx
                    newHeight = initialHeight +  dy
//                    Log.v("ElasticallyLayout:", "newWidth:$newWidth")
                    Log.v("ElasticallyLayout:", "startY:$startY")
                    Log.v("ElasticallyLayout:", "event.y:${event.y}")
                    Log.v("ElasticallyLayout:", "dy:$dy")
//                    Log.v("ElasticallyLayout:", "newHeight:$newHeight")
                    requestLayout()
                }

            }
            MotionEvent.ACTION_UP -> {
                isDragging = false

            }


        }
        return true
    }


    private fun inAnchorArea(x: Float, y: Float): Boolean {
        return x > width - 51.dp && x < width - 15.dp && y > height / 2 - 27.dp && y < height / 2 + 27.dp
    }
}