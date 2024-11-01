package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.provider.Settings
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
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


    enum class Type {
        /**
         * 垂直样式
         */
        VERTICAL,

        /**
         * 水平样式
         */
        HORIZONTAL,

        /**
         * 水平、百分比文字样式
         */
        HORIZONTAL_PERCENT_TEXT
    }

    private var chartType = Type.VERTICAL

    private val bgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val bgPaint2 = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.parseColor("#EBEEFE")
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textSize = 12f.dp
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }


    private lateinit var progressBitmap: Bitmap


    private val fontMetrics = Paint.FontMetrics()

    //背景
    private val bgPath = Path()

    private val path = Path()


    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f

    private val strokePadding: Float = 2.5f.dp

    private var progress = 0
    private var target = 100


    init {
        initAttrs(context, attrs)

        // 获取系统动画时长缩放比例
        val systemAnimatorScale: Float = Settings.Global.getFloat(
            context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f
        )

        Log.v("TargetProgressBarView", "获取系统动画时长缩放比例：${systemAnimatorScale}")
    }

    @Keep
    private var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animator: Animator by lazy {
        val animator1 = ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
        animator1.interpolator = LinearInterpolator()
        animator1

    }


    /**
     * 设置进度值与目标值
     * @param progress 进度值
     * @param target 目标值,必须大于0
     */
    fun setRate(progress: Int, target: Int) {
        if (target <= 0 || progress < 0) {
            return
        }
        this.progress = progress
        if (progress > target) {
            this.progress = target
        }
        this.target = target
        animator.start()
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

        when (attributes.getInt(R.styleable.TargetProgressView_target_bar_style, 0)) {
            0 -> chartType = Type.VERTICAL
            1 -> chartType = Type.HORIZONTAL
            2 -> chartType = Type.HORIZONTAL_PERCENT_TEXT
        }
        attributes.recycle()
    }

    fun setBarAndBGColor(barColor: Int, bgColor: Int) {
        paint.color = barColor
        bgPaint.color = bgColor
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(20.dp, widthMeasureSpec)
        val height = resolveSize(20.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w.toFloat()
        viewHeight = h.toFloat()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (chartType) {
            Type.VERTICAL -> drawVertical(canvas)
            Type.HORIZONTAL -> drawHorizontal(canvas)
            Type.HORIZONTAL_PERCENT_TEXT -> drawHorizontalPercentText(canvas)
        }
    }


    private fun drawVertical(canvas: Canvas) {
        bgPath.reset()
        bgPath.moveTo(0f, viewHeight)
        bgPath.lineTo(0f, viewWidth / 2)
        bgPath.arcTo(0f, 0f, viewWidth, viewWidth, 180f, 180f, false)
        bgPath.lineTo(viewWidth, viewHeight)
        canvas.drawPath(bgPath, bgPaint)

        if (progress > 0) {
            val barHeight =
                (viewHeight - viewWidth / 2 - strokePadding) * progress * animRate / target / 100
            val yBar = viewHeight - barHeight - strokePadding
            path.reset()
            path.moveTo(strokePadding, viewHeight)
            path.lineTo(
                strokePadding,
                yBar
            )
            path.arcTo(
                strokePadding,
                yBar - (viewWidth / 2 - strokePadding),
                viewWidth - strokePadding,
                yBar + (viewWidth / 2 - strokePadding),
                180f,
                180f,
                false
            )

            path.lineTo(viewWidth - strokePadding, viewHeight)
            canvas.drawPath(path, paint)
        }
    }

    private fun drawHorizontal(canvas: Canvas) {

        val radius = viewHeight / 2

        bgPaint.strokeCap = Paint.Cap.ROUND
        bgPaint.strokeWidth = viewHeight
        canvas.drawLine(
            radius,
            radius,
            viewWidth - radius,
            radius, bgPaint
        )

        if (progress <= 0) return

        val barLeft = strokePadding
        val barRadius = viewHeight / 2 - strokePadding
        val progressWidth =
            (viewWidth - barRadius * 2 - strokePadding * 2) * progress * animRate / target / 100

        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = barRadius * 2

        canvas.drawLine(
            barLeft + barRadius,
            radius,
            barLeft + barRadius + progressWidth,
            radius,
            paint
        )

    }

    private fun drawHorizontalPercentText(canvas: Canvas) {

        val radius = viewHeight / 2

        //画背景
        bgPaint2.strokeCap = Paint.Cap.ROUND
        bgPaint2.strokeWidth = viewHeight
        canvas.drawLine(
            radius,
            radius,
            viewWidth - radius,
            radius, bgPaint2
        )

        val barLeft = strokePadding
        val barTop = strokePadding
        val barRight = viewWidth - strokePadding
        val barBottom = viewHeight - strokePadding
        val barRadius = viewHeight / 2 - strokePadding
        path.reset()
        path.moveTo(barRadius + barLeft, barTop)
        path.lineTo(barRight - barRadius, barTop)
        path.arcTo(barRight - barRadius * 2, barTop, barRight, barBottom, 270f, 180f, false)
        path.lineTo(barLeft + barRadius, barBottom)
        path.arcTo(barLeft, barTop, barLeft + barRadius * 2, barBottom, 90f, 180f, false)

        canvas.save()

        //裁切效果实现的无法做抗锯齿优化
        canvas.clipPath(path)

        bgPaint.strokeWidth = viewHeight
        canvas.drawLine(barLeft, viewHeight / 2, barRight, viewHeight / 2, bgPaint)

        if (progress > 0) {
            paint.strokeWidth = viewHeight
            canvas.drawLine(
                barLeft,
                viewHeight / 2,
                barRight * progress * animRate / target / 100,
                viewHeight / 2,
                paint
            )
        }

        canvas.restore()

        val text = "${progress * animRate / 100}%"
        textPaint.getFontMetrics(fontMetrics)
        canvas.drawText(
            text,
            viewWidth / 2,
            viewHeight / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2,
            textPaint
        )

    }


}