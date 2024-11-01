package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doreamon.R
import com.example.doreamon.entity.ChartData
import com.hyy.highlightpro.util.dp


/**
 * 多条折线图
 */
class MultiBrokenLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    private val paint = Paint()
    private val yPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#FF9152")
        strokeWidth = 2f.dp
    }

    private val y1Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#877CFE")
        strokeWidth = 2f.dp
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#979BA7")
        textAlign = Paint.Align.CENTER
        textSize = 18f.dp
    }

    private val axisPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#E3E7FF")
        style = Paint.Style.STROKE
        strokeWidth = 2.5f.dp
    }

    private val axisGapPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#F5F5F5")
        style = Paint.Style.STROKE
        strokeWidth = 1.5f.dp
    }

    private val shadowPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    var shadow1: LinearGradient? = null
    var shadow2: LinearGradient? = null

    private var list: List<ChartData> = ArrayList()

    private var chartMarginBottom = 30f.dp
    private val barMarginTop = 80f.dp
    private val barMarginBottom = 0f.dp
    private val barMarginLeft = 60f.dp


    var yAxisUnit: String = "s"
    var yAxisText: String? = "时间"

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

    //y坐标
    private var y1List: MutableList<Float> = ArrayList()

    //折线1
    private val linePath = Path()

    //折线2
    private val line1Path = Path()

    var maxAxisY = 40


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
        y1List.clear()

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

        maxAxisY = 40
        while (maxAxisY < maximumY) {
            maxAxisY += 40
        }

        val shadowHeight = yMaxHeight - availableChartHeight * maximumY / maxAxisY

        shadow1 = LinearGradient(
            0f,
            shadowHeight,
            0f,
            yMaxHeight,
            Color.parseColor("#33FF9152"),
            Color.parseColor("#00FF9152"),
            Shader.TileMode.CLAMP
        )


        shadow2 = LinearGradient(
            0f,
            shadowHeight,
            0f,
            yMaxHeight,
            Color.parseColor("#33877CFE"),
            Color.parseColor("#00877CFE"),
            Shader.TileMode.CLAMP
        )






        for (i in 0 until size) {
            val i1 = (xSpace / 2 + xSpace * i) + barMarginLeft
            xList.add(i1)
            val chartData = list[i]
            val yVal = chartData.y
            val y1Val = chartData.y1
            if (maximumY > 0) {
                yList.add(yMaxHeight - availableChartHeight * yVal / maxAxisY)
                y1List.add(yMaxHeight - availableChartHeight * y1Val / maxAxisY)
            } else {
                yList.add(yMaxHeight)
                y1List.add(yMaxHeight)
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
        canvas.drawLine(
            barMarginLeft,
            viewHeight - chartMarginBottom,
            viewWidth,
            viewHeight - chartMarginBottom,
            axisPaint
        )

        //y轴
        canvas.drawLine(
            barMarginLeft,
            viewHeight - chartMarginBottom + 1.25f.dp,
            barMarginLeft,
            0f,
            axisPaint
        )

        linePath.reset()
        line1Path.reset()
        for (i in 0 until size) {
            //x轴文字
            val xData = list[i]
            val xText = xData.x.trim()
            val currX = xList[i]


            textPaint.textSize = 12f.dp
            canvas.drawText(
                xText,
                xList[i],
                (viewHeight - 10f.dp),
                textPaint
            )

            val currY = yList[i]
            if (i == 0) {
                linePath.moveTo(currX, currY)
            } else {
                linePath.lineTo(currX, currY)
            }

            val currY1 = y1List[i]
            if (i == 0) {
                line1Path.moveTo(currX, currY1)
            } else {
                line1Path.lineTo(currX, currY1)
            }
        }

        for (j in 0..4) {

            val currY = yMaxHeight - j * availableChartHeight / 4

            textPaint.textSize = 12f.dp
            canvas.drawText(
                "${maxAxisY / 4 * j}${yAxisUnit}",
                barMarginLeft - 20f.dp,
                currY + 5f.dp,
                textPaint
            )

            if (j != 0) {
                canvas.drawLine(
                    barMarginLeft,
                    currY,
                    viewWidth,
                    currY,
                    axisGapPaint
                )
            }
        }

        canvas.drawText(
            "${yAxisText}",
            barMarginLeft - 20f.dp,
            20f.dp,
            textPaint
        )

        canvas.drawPath(linePath, yPaint)
        canvas.drawPath(line1Path, y1Paint)

        linePath.lineTo(xList[size - 1], yMaxHeight)
        linePath.lineTo(xList[0], yMaxHeight)
        linePath.close()

        shadowPaint.shader = shadow1
        canvas.drawPath(linePath, shadowPaint)


        line1Path.lineTo(xList[size - 1], yMaxHeight)
        line1Path.lineTo(xList[0], yMaxHeight)
        line1Path.close()

        shadowPaint.shader = shadow2
        canvas.drawPath(line1Path, shadowPaint)

        for (i in 0 until size) {

            val currY = yList[i]

            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawCircle(xList[i], currY, 6f.dp, paint)

            paint.color = Color.parseColor("#FF9152")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.5f.dp
            canvas.drawCircle(xList[i], currY, 6f.dp, paint)

            val currY1 = y1List[i]

            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawCircle(xList[i], currY1, 6f.dp, paint)

            paint.color = Color.parseColor("#877CFE")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.5f.dp
            canvas.drawCircle(xList[i], currY1, 6f.dp, paint)
        }


    }

}