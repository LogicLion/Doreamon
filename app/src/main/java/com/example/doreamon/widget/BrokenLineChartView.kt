package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Keep
import com.example.doreamon.R
import com.example.doreamon.entity.ChartData
import com.doreamon.treasure.ext.dp
import java.util.ArrayList


/**
 * 折线图
 */
class BrokenLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    val paint = Paint()
    private val yPaint = Paint()
    private val textPaint = Paint().apply {
        color = Color.parseColor("#333333")
        textAlign = Paint.Align.CENTER
        textSize = 12f.dp
        typeface = Typeface.SANS_SERIF
    }
    var list: List<ChartData> = ArrayList()

    private var chartMarginBottom = 30f.dp
    private val barMarginTop = 30f.dp
    private val barMarginBottom = 30f.dp

    //图表可绘制区域最高高度
    private var yMaxHeight: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f

    private var shouldDrawXDes = false

    //以左上角为（0,0）点
    //x坐标
    var xList: MutableList<Float> = ArrayList()

    //y坐标
    var yList: MutableList<Float> = ArrayList()

    //折线
    private val linePath = Path()


    //每个折点动画执行的起始位置(默认在可绘制区域的中间)
    var yAnimStartList: MutableList<Float> = ArrayList()


    @Keep
    var progressRate = 0
        set(rate) {
            field = rate
            invalidate()
        }


    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "progressRate", 0, 100).setDuration(500)
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BrokenLineChartView)
        shouldDrawXDes =
            attributes.getBoolean(R.styleable.BrokenLineChartView_draw_x_des_mode, false)
        attributes.recycle()
    }

    init {
        paint.isAntiAlias = true
        yPaint.color = Color.parseColor("#FFD889")
        yPaint.style = Paint.Style.STROKE
        yPaint.strokeWidth = 3f.dp


        initAttrs(context, attrs)
    }

    fun setChartList(list: List<ChartData>) {
        this.list = list

        updateXY()
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.v("HistogramChartView", "onSizeChanged:" + h)

        viewWidth = w.toFloat()
        viewHeight = h.toFloat()
        chartMarginBottom = if (shouldDrawXDes) 50f.dp else 30f.dp

        yMaxHeight = viewHeight - chartMarginBottom - barMarginBottom
        updateXY()
    }

    private fun updateXY() {
        if (viewHeight == 0f || viewHeight == 0f) {
            return
        }


        val size = list.size

        if (xList.size != size) {
            yAnimStartList.clear()
            for (i in 0 until size) {
                yAnimStartList.add((yMaxHeight + barMarginTop) / 2)
            }
        } else {
            for (i in 0 until size) {
                yAnimStartList[i] = yList[i]
            }
        }

        xList.clear()
        yList.clear()

        if (size == 0) return

        val xSpace = viewWidth / size
        val availableChartHeight = yMaxHeight - barMarginTop

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
            val chartData = list[i]
            val data = chartData.y
            if (maximumY > 0) {
                yList.add(yMaxHeight - availableChartHeight * data / maximumY)
            } else {
                yList.add(0f)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            300.dp
        }

        val measureHeight =
            if (heightMode == MeasureSpec.EXACTLY) {
                heightSize
            } else {
                150.dp
            }

        setMeasuredDimension(measuredWidth, measureHeight)
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


        linePath.reset()
        for (i in 0 until size) {
            textPaint.color = Color.parseColor("#666666")
            //x轴文字
            val xData = list[i]
            val xText = xData.x.trim()
            val xDesText = xData.xDes ?: ""
            val currX = xList[i]

            val yDelta = yList[i] - yAnimStartList[i]
            val currY = yList[i] - (yDelta - yDelta * progressRate / 100)
            if (shouldDrawXDes) {
                textPaint.color = Color.parseColor("#666666")
                textPaint.textSize = 12f.dp
                canvas.drawText(
                    xText,
                    xList[i],
                    (viewHeight - 30f.dp),
                    textPaint
                )

                textPaint.color = Color.parseColor("#999999")
                textPaint.textSize = 10f.dp
                canvas.drawText(
                    xDesText,
                    xList[i],
                    (viewHeight - 12f.dp),
                    textPaint
                )
            } else {
                canvas.drawText(
                    xText,
                    xList[i],
                    (viewHeight - 10f.dp),
                    textPaint
                )
            }


            if (i == 0) {
                linePath.moveTo(currX, currY)
            } else {
                linePath.lineTo(currX, currY)
            }
        }

        canvas.drawPath(linePath, yPaint)


        for (i in 0 until size) {

            val yDelta = yList[i] - yAnimStartList[i]
            val currY = yList[i] - (yDelta - yDelta * progressRate / 100)

            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawCircle(xList[i], currY, 4f.dp, paint)

            paint.color = Color.parseColor("#FFE2C1")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1f.dp
            canvas.drawCircle(xList[i], currY, 4f.dp, paint)

            paint.color = Color.parseColor("#FEAE54")
            paint.style = Paint.Style.FILL
            canvas.drawCircle(xList[i], currY, 2.5f.dp, paint)
        }


        if (progressRate == 100) {
            for (i in 0 until size) {

                //y轴标记文字
                val chartData = list[i]
                val markText = chartData.y.toString() + chartData.yUnit

                val y = if (i == 0) {
                    yList[i] - 10f.dp
                } else if (yList[i] < yList[i - 1]) {
                    yList[i] - 10f.dp
                } else {
                    yList[i] + 15f.dp
                }
                textPaint.color = Color.parseColor("#333333")
                textPaint.textSize = 10f.dp
                canvas.drawText(
                    markText,
                    xList[i],
                    y,
                    textPaint
                )
            }
        }

    }

}