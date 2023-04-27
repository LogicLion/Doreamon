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
import com.example.doreamon.entity.ChartData


/**
 * 柱状图
 */
class HistogramChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    private val paint = Paint()
    private val yPaint = Paint()
    private val markPaint = Paint()
    private val guideLinePaint = Paint()
    private var list: List<ChartData> = ArrayList()

    private var chartBarWidth = 5f.dp
    private val chartMarginBottom = 30f.dp
    private val barMarginTop = 30f.dp
    private val barMarginBottom = 10f.dp
    private val xTextSize = 12f.dp
    private val markTextSize = 10f.dp

    //柱状体的起点，也就是x轴
    private var yStart: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f

    //x坐标
    private var xList: MutableList<Float> = ArrayList()

    //y高度
    private var yHeightList: MutableList<Float> = ArrayList()

    private var xyPointList: MutableList<Point> = ArrayList()

    //柱体path
    private var pathList: MutableList<Path> = ArrayList()

    @Keep
    private var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }


    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(500)
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HistogramChartView)
        yPaint.color = attributes.getColor(
            R.styleable.HistogramChartView_histogram_bar_color,
            Color.parseColor("#5FC0FF")
        )
        chartBarWidth =
            attributes.getDimension(R.styleable.HistogramChartView_histogram_bar_width, 5f.dp)
        attributes.recycle()
    }

    init {
        paint.isAntiAlias = true
        yPaint.color = Color.parseColor("#5FC0FF")
        yPaint.style = Paint.Style.FILL
        yPaint.isAntiAlias = true
        markPaint.isAntiAlias = true
        markPaint.textSize = markTextSize
        markPaint.textAlign = Paint.Align.CENTER
        markPaint.typeface = Typeface.SANS_SERIF

        guideLinePaint.style = Paint.Style.STROKE

        initAttrs(context, attrs)
    }

    fun setChartList(list: List<ChartData>) {
        this.list = list

        updateXY()
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w.toFloat()
        viewHeight = h.toFloat()
        yStart = viewHeight - chartMarginBottom - barMarginBottom
        updateXY()
    }

    private fun updateXY() {
        if (viewWidth == 0f || viewHeight == 0f) {
            return
        }

        xList.clear()
        yHeightList.clear()
        pathList.clear()
        val size = list.size
        if (size == 0) return
        val xSpace = viewWidth / size
        val availableChartHeight = yStart - barMarginTop

        //最大值
        var maximumY = 0
        for (i in 0 until size) {
            val chartData = list[i]
            val data = chartData.y
            if (data > maximumY) {
                maximumY = data
            }
        }
        for (i in 0 until size) {
            val i1 = xSpace / 2 + xSpace * i
            xList.add(i1)
            pathList.add(Path())
            val chartData = list[i]
            val data = chartData.y
            if (maximumY > 0) {
                yHeightList.add(availableChartHeight * data / maximumY)
            } else {
                yHeightList.add(0f)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(300.dp, widthMeasureSpec)
        val height = resolveSize(200.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (list.isEmpty()) {
            return
        }

        val size = list.size

        //白色背景
        paint.color = Color.WHITE
        canvas.drawRoundRect(
            0f,
            0f,
            viewWidth,
            viewHeight - chartMarginBottom,
            10f.dp,
            10f.dp,
            paint
        )

        //x轴
        guideLinePaint.strokeWidth = 1f.dp
        guideLinePaint.color = Color.parseColor("#EAEAEA")
        canvas.drawLine(
            10f.dp,
            yStart,
            viewWidth - 10f.dp,
            yStart, guideLinePaint
        )


        //画3个等分线
        guideLinePaint.strokeWidth = 0.5f.dp
        guideLinePaint.color = Color.parseColor("#F5F5F5")
        val spaceAverageGuideLine = yStart / 4
        for (count in 1..3) {
            canvas.drawLine(
                10f.dp,
                yStart - spaceAverageGuideLine * count,
                viewWidth - 10f.dp,
                yStart - spaceAverageGuideLine * count,
                guideLinePaint
            )
        }


        paint.textSize = xTextSize
        for (i in 0 until size) {
            paint.color = Color.parseColor("#666666")
            //x轴文字
            val xData = list[i]
            val xText = xData.x.trim()
            val xTextWidth = markPaint.measureText(xText)
            val currX = xList[i]
            canvas.drawText(
                xText,
                (currX - xTextWidth / 2),
                (viewHeight - 10f.dp),
                paint
            )

            //柱状体
            val yPath = pathList[i]

            val yRate = yHeightList[i] * animRate / 100
            if (yHeightList[i] > 0) {
                yPath.moveTo(
                    (currX - chartBarWidth / 2),
                    yStart
                )
                yPath.lineTo(
                    (currX - chartBarWidth / 2),
                    (viewHeight - chartMarginBottom - yRate)
                )
                yPath.arcTo(
                    (currX - chartBarWidth / 2),
                    ((yStart - yRate) - chartBarWidth / 2),
                    (currX + chartBarWidth / 2),
                    ((yStart - yRate) + chartBarWidth / 2),
                    -180f,
                    180f,
                    false
                )
                yPath.lineTo(
                    (currX + chartBarWidth / 2),
                    yStart
                )
                yPath.close()
                canvas.drawPath(yPath, yPaint)

            }
        }

        //y轴标记文字
        if (animRate == 100) {
            for (i in 0 until size) {
                val chartData = list[i]
                val markText = chartData.y.toString() + chartData.yUnit
                val y = viewHeight - chartMarginBottom - yHeightList[i] - 24f.dp
                markPaint.color = Color.parseColor("#333333")
                canvas.drawText(
                    markText,
                    xList[i],
                    y,
                    markPaint
                )
            }
        }

    }

}