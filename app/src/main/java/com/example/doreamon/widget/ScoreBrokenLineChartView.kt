package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R
import com.example.doreamon.entity.ChartData


/**
 * 得分-折线图
 */
class ScoreBrokenLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    private val paint = Paint()
    private val yPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE

        color = Color.parseColor("#5668FF")
        strokeWidth = 0.5f.dp

        val pathEffect = DashPathEffect(floatArrayOf(4f.dp, 3f.dp), 0f)
        setPathEffect(pathEffect)
    }

    private val xTextPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#B3AEA6")
        textAlign = Paint.Align.CENTER
        textSize = 12f.dp
    }

    private val yTextPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#5668FF")
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
        textSize = 14f.dp
    }

    private val axisPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#666666")
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 1f.dp
    }

    private val axisGapPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#BEBAE8")
        style = Paint.Style.STROKE
        strokeWidth = 0.25f.dp

        val pathEffect = DashPathEffect(floatArrayOf(4f.dp, 3f.dp), 0f)
        setPathEffect(pathEffect)
    }


    private var list: List<ChartData> = ArrayList()

    private var chartMarginBottom = 30f.dp
    private val barMarginTop = 30f.dp
    private val barMarginBottom = 30f.dp
    private val barMarginLeft = 0f.dp


    //图表可绘制区域最高高度
    private var yMaxHeight: Float = 0f


    private var availableChartHeight: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f


    //以左上角为（0,0）点
    //x坐标
    private var xList: MutableList<Float> = ArrayList()

    //y坐标
    private var yList: MutableList<Float> = ArrayList()


    private val xLinePath = Path()

    //折线1
    private val linePath = Path()


    var maxAxisY = 100


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BrokenLineChartView)
        attributes.recycle()
    }

    init {
        paint.isAntiAlias = true
        initAttrs(context, attrs)
    }

    fun setChartList(list: List<ChartData>) {
        this.list = list

        updateXY()

        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w.toFloat()
        viewHeight = h.toFloat()

        yMaxHeight = viewHeight - chartMarginBottom - barMarginBottom
        updateXY()
    }

    private fun updateXY() {
        if (viewWidth == 0f || viewHeight == 0f) {
            return
        }

        val size = list.size

        xList.clear()
        yList.clear()

        if (size == 0) return

        val xSpace = (viewWidth - barMarginLeft) / size
        availableChartHeight = yMaxHeight - barMarginTop


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
            val chartData = list[i]
            val data = chartData.y1
            if (data > maximumY) {
                maximumY = data
            }
        }

        maxAxisY = 100
        while (maxAxisY < maximumY) {
            maxAxisY += 100
        }


        for (i in 0 until size) {
            val i1 = (xSpace / 2 + xSpace * i) + barMarginLeft
            xList.add(i1)
            val chartData = list[i]
            val yVal = chartData.y
            if (maximumY > 0) {
                yList.add(yMaxHeight - availableChartHeight * yVal / maxAxisY)
            } else {
                yList.add(yMaxHeight)
            }

        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(300.dp, widthMeasureSpec)
        val height = resolveSize(360.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (list.isEmpty()) {
            return
        }

        val size = list.size


        //x轴

        xLinePath.reset()
        xLinePath.moveTo(barMarginLeft, viewHeight - chartMarginBottom)
        xLinePath.lineTo(viewWidth, viewHeight - chartMarginBottom)
        canvas.drawPath(xLinePath, axisPaint)

        xLinePath.reset()
        xLinePath.moveTo(viewWidth, viewHeight - chartMarginBottom)
        xLinePath.lineTo(viewWidth - 3.5f.dp, viewHeight - barMarginBottom - 2f.dp)
        xLinePath.lineTo(viewWidth - 3.5f.dp, viewHeight - barMarginBottom + 2f.dp)
        xLinePath.close()
        canvas.drawPath(xLinePath, axisPaint)



        linePath.reset()
        for (i in 0 until size) {
            //x轴文字
            val xData = list[i]
            val xText = xData.x.trim()
            val currX = xList[i]

            canvas.drawText(
                xText,
                xList[i],
                (viewHeight - 10f.dp),
                xTextPaint
            )

            val currY = yList[i]
            if (i == 0) {
                linePath.moveTo(currX, currY)
            } else {
                linePath.lineTo(currX, currY)
            }

            canvas.drawLine(currX, 0f, currX, viewHeight - chartMarginBottom, axisGapPaint)

        }


        canvas.drawPath(linePath, yPaint)


        for (i in 0 until size) {

            val currY = yList[i]

            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawCircle(xList[i], currY, 3f.dp, paint)

            paint.color = Color.parseColor("#5668FF")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f.dp
            canvas.drawCircle(xList[i], currY, 3f.dp, paint)

        }


        for (i in 0 until size) {

            //y轴标记文字
            val chartData = list[i]
            val markText = chartData.y.toString() + chartData.yUnit

            val y = yList[i] + 20f.dp
            canvas.drawText(
                markText,
                xList[i],
                y,
                yTextPaint
            )
        }


    }

}