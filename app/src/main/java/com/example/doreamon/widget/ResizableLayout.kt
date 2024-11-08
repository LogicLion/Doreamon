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
import com.blankj.utilcode.util.ScreenUtils
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

/**
 * 可调整宽高大小的layout
 * @author wzh
 * @date 2024/9/7
 */
class ResizableLayout @JvmOverloads constructor(
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

//        if (isDragging) {
//            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//            setMeasuredDimension(widthSize, heightSize)
//        }

        Log.v("ElasticallyLayout:", "heightSize:$heightSize")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

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

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    private var newWidth: Float = 0f
    private var newHeight: Float = 0f

    private var initialWidth = 0f
    private var initialHeight = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (inAnchorArea(event.x, event.y)) {
                    lastX = event.x
                    lastY = event.y
                    initialWidth = width.toFloat()
                    initialHeight = height.toFloat()
                    isDragging = true
                }

            }

            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                val dy = event.y - lastY
                newWidth = initialWidth + dx


                val halfAnchorHeight = height.toFloat() / 2f
                newHeight = (ScreenUtils.getScreenHeight() - event.rawY) * 2
//                Log.v("ElasticallyLayout:", "newWidth:$newWidth")
//                Log.v("ElasticallyLayout:", "startY:$lastY")

                Log.v("ElasticallyLayout:", "-----------------------------------")
                Log.v("ElasticallyLayout:", "event.y:${event.y}")
                Log.v("ElasticallyLayout:", "dy:$dy")
                Log.v("ElasticallyLayout:", "newHeight:$newHeight")
                lastY = event.y
                this.layoutParams.width = newWidth.toInt()
                this.layoutParams.height = newHeight.toInt()
                requestLayout()

//                lastX = event.x


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