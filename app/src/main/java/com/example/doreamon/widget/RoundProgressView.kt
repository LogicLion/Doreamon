package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import com.example.doreamon.R
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Keep
import java.time.Duration

/**
 * 可设置圆角进度条控件,可考虑实现:拖动进度
 * @author wzh
 * @date 2022/3/10
 */
class RoundProgressView : View {
    //背景画笔
    var bgPaint = Paint()

    //进度条画笔
    var progressPaint = Paint()

    //进度条颜色
    private var progressColor = 0

    //背景颜色
    private var bgColor = 0

    //总进度
    private var total = 100

    //进度
    private var progress = 0

    //背景透明度
    private var bgAlpha = 255

    //圆角
    private var cornerRadius = 0f

    /**
     * 0:横向
     * 1:竖向
     */
    private var orientation = 0


    private lateinit var rectF: RectF
    private lateinit var rectPath: Path

    private lateinit var animator: Animator

    private var progressLength = 0




    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
        init()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressView)
        progressColor =
            attributes.getColor(R.styleable.RoundProgressView_progress_bar_color, Color.RED)
        bgColor =
            attributes.getColor(R.styleable.RoundProgressView_progress_background_color, Color.GRAY)
        total = attributes.getInt(R.styleable.RoundProgressView_progress_total, 100)
        progress = attributes.getInt(R.styleable.RoundProgressView_progress, 0)
        cornerRadius =
            attributes.getDimension(R.styleable.RoundProgressView_round_corner_radius, 0f)
        orientation = attributes.getInt(R.styleable.RoundProgressView_progress_bar_orientation, 0)
        attributes.recycle()
    }

    private fun init() {
        bgPaint.isAntiAlias = true
        bgPaint.color = bgColor
        bgPaint.alpha = bgAlpha
        progressPaint.isAntiAlias = true
        progressPaint.color = progressColor
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Log.v("执行顺序","onSizeChanged")
        val width = width
        val height = height
        computeProgressBarLength()
        rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        rectPath = Path()
        rectPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)

        animator = ObjectAnimator.ofInt(this, "progressLength", 0, progressLength)
        animator.duration = 500
        animator.start()

        animator.addListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(
            rectF,
            cornerRadius, cornerRadius, bgPaint
        )
        canvas.save()

        canvas.clipPath(rectPath)
        progressPaint.color = progressColor
        if (orientation == 1) {
            canvas.drawRoundRect(
                0f,
                (height - progressLength).toFloat(),
                width.toFloat(),
                (height + height - progressLength).toFloat(),
                cornerRadius,
                cornerRadius,
                progressPaint
            )
        } else {
            canvas.drawRoundRect(
                (-width + progressLength).toFloat(),
                0f,
                progressLength.toFloat(),
                height.toFloat(),
                cornerRadius,
                cornerRadius,
                progressPaint
            )
        }
        canvas.restore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.v("执行顺序","onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::animator.isInitialized && animator.isRunning) {
            animator.cancel()
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    fun setProgress(progress: Int) {
        this.progress = progress

        computeProgressBarLength()
    }

    @Keep
    fun setProgressLength(length:Int) {
        this.progressLength = length
        invalidate()
    }

    private fun computeProgressBarLength() {
        if (orientation == 1) {
            progressLength = height * progress / total
        } else {
            progressLength = width * progress / total
        }
    }


    fun updateProgressWithAnimator(progress: Int, duration: Long = 500) {
        this.progress = progress
        computeProgressBarLength()
        animator = ObjectAnimator.ofInt(this, "progressLength", 0, progressLength)
        animator.duration = duration
        animator.start()
    }

    /**
     * 设置总进度，默认100
     * @param total
     */
    fun setTotal(total: Int) {
        this.total = total
        invalidate()
    }

    /**
     * 设置进度条颜色
     * @param progressColor 进度条颜色
     */
    fun setProgressColor(progressColor: Int) {
        this.progressColor = progressColor
        progressPaint.color = progressColor
    }

    /**
     * 设置背景颜色
     * @param backgroundColor 背景颜色
     */
    fun setBgColor(backgroundColor: Int) {
        this.bgColor = backgroundColor
        bgPaint.color = backgroundColor
    }

    /**
     * 设置背景透明度和颜色
     * @param bgAlpha         透明度0~255，0完全透明，255完全不透明
     * @param backgroundColor  背景颜色
     */
    fun setBgAlphaColor(bgAlpha: Int, backgroundColor: Int) {
        this.bgColor = backgroundColor
        this.bgAlpha = bgAlpha
        bgPaint.color = backgroundColor
        bgPaint.alpha = bgAlpha
    }

    /**
     * 设置进度和总进度
     * @param progress 进度
     * @param total 总进度
     */
    fun setProgressAndTotal(progress: Int, total: Int) {
        if (total <= 0) {
            this.total = 100
        } else {
            this.total = total
        }

        if (progress > total) {
            this.progress = total
        } else {
            this.progress = progress
        }

        computeProgressBarLength()
        invalidate()
    }

}