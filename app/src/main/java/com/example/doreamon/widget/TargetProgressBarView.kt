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
 * 目标进度控件
 * @author wzh
 */
class TargetProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {

    private val bgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    //背景
    private val bgPath = Path()

    private val path = Path()


    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f

    private var roundRadius = 0f
    private var progress = 0
    private var total = 0

    val rect = Rect()

    init {
        initAttrs(context, attrs)
    }

    @Keep
    var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
    }


    fun setRate(progress: Int, total: Int) {
        if (total <= 0) {
            return
        }
        if (progress > total) {
            this.progress = total
        }
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TargetProgressView)
        bgPaint.color = attributes.getColor(
            R.styleable.TargetProgressView_target_bg_color,
            Color.parseColor("#FFEDD8")
        )

        paint.color = attributes.getColor(
            R.styleable.TargetProgressView_target_bar_color,
            Color.parseColor("#FEAE54")
        )
        attributes.recycle()
    }

    fun setBarAndBGColor(barColor: Int, bgColor: Int) {
        paint.color = barColor
        bgPaint.color = bgColor
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth = if (widthMode == View.MeasureSpec.EXACTLY) {
            widthSize
        } else {
            20.dp
        }

        val measureHeight = if (heightMode == View.MeasureSpec.EXACTLY) {
            heightSize
        } else {
            120.dp
        }

        setMeasuredDimension(measuredWidth, measureHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w.toFloat()
        viewHeight = h.toFloat()

        roundRadius = viewWidth / 2

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bgPaint.style = Paint.Style.FILL
        bgPath.reset()
        bgPath.moveTo(0f, viewHeight)
        bgPath.lineTo(0f, viewHeight - viewWidth / 2)
        bgPath.arcTo(0f, 0f, viewWidth, viewWidth, 180f, 180f, false)
        bgPath.lineTo(viewWidth, viewHeight)
        canvas.drawPath(bgPath, bgPaint)

        path.reset()
        path.moveTo(0f, viewHeight)
        path.lineTo(0f, viewHeight - viewWidth / 2)
        path.arcTo(0f, 0f, viewWidth, viewWidth, 180f, 180f, false)
        path.lineTo(viewWidth, viewHeight)
        canvas.drawPath(path, paint)

        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = 2f.dp
        bgPath.reset()
        bgPath.moveTo(0f, viewHeight)
        bgPath.lineTo(0f, viewHeight - viewWidth / 2)
        bgPath.arcTo(0f, 0f, viewWidth, viewWidth, 180f, 180f, false)
        bgPath.lineTo(viewWidth, viewHeight)
        canvas.drawPath(bgPath, bgPaint)


    }
}