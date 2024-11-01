package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

class NiuTouRenSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0 // 初始进度，默认为50
    private var maxProgress = 100 // 最大进度，默认为100

    private var minProgress = 0

    val currProgress: Int
        get() = progress + minProgress

    private var logoBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.icon_niu_seek)

    private var numBgBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.bg_niu_seek_num)

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f.dp
    }

    var onProgressChangeListener: (Int) -> Unit = {}

    private val horizontalMargin = 20.dp

    private lateinit var logoSrcRect: Rect
    private lateinit var logoDestRect: Rect

    private lateinit var numBgSrcRect: Rect
    private lateinit var numBgDestRect: Rect


    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#E5E6EF")
        this.strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f.dp
    }


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12f.dp
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(200.dp, widthMeasureSpec)
        val height = resolveSize(90.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val gradient = LinearGradient(
            0f,
            height - 15f.dp,
            width.toFloat(),
            height - 15f.dp,
            Color.parseColor("#D4BFFF"),
            Color.parseColor("#A07BFF"),
            Shader.TileMode.CLAMP
        )
        progressPaint.shader = gradient

        logoDestRect = Rect(0, height - 90.dp, 50.dp, height - 40.dp)
        logoSrcRect = Rect(0, 0, logoBitmap.width, logoBitmap.height)

        numBgDestRect = Rect(0, height - 22.dp, 34.dp, height)
        numBgSrcRect = Rect(0, 0, numBgBitmap.width, numBgBitmap.height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        var progressWidth = if (maxProgress - minProgress == 0) {
            (width - horizontalMargin * 2 - 20f.dp).toInt()
        } else {
            (progress * (width - horizontalMargin * 2 - 20f.dp)).toInt() / (maxProgress - minProgress)
        }

        canvas.drawLine(
            10f.dp + horizontalMargin,
            height - 35f.dp,
            width.toFloat() - 10f.dp - horizontalMargin,
            height - 35f.dp,
            bgPaint
        )

        val progressX = 10f.dp + horizontalMargin + progressWidth
        canvas.drawLine(
            10f.dp + horizontalMargin,
            height - 35f.dp,
            progressX,
            height - 35f.dp,
            progressPaint
        )

        logoBitmap.let {
            logoDestRect.left = progressX.toInt() - 25.dp
            logoDestRect.right = progressX.toInt() + 25.dp
            canvas.drawBitmap(it, logoSrcRect, logoDestRect, null)
        }

        numBgBitmap.let {
            numBgDestRect.left = progressX.toInt() - 17.dp
            numBgDestRect.right = progressX.toInt() + 17.dp
            canvas.drawBitmap(it, numBgSrcRect, numBgDestRect, null)
        }

        canvas.drawText((progress + minProgress).toString(), progressX, height - 6f.dp, textPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x

//                if (x < 10f.dp + horizontalMargin|| x > width.toFloat() - 10f.dp - horizontalMargin) {
//                    return false
//                }

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

                onProgressChangeListener(currProgress)
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