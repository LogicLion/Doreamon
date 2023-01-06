package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doreamon.R
import com.example.doreamon.entity.ChartData
import com.example.doreamon.ext.dp
import java.util.ArrayList

/**
 * 柱状图
 */
class HistogramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    var paint = Paint()
    var y1Paint = Paint()
    var markPaint = Paint()
    var linePaint = Paint()
    var list: List<ChartData> = ArrayList()
    var chartMarginBottom = 0
    var chartMarginTop = 0
    var textWidth = 0
    var textHeight = 0
    var textSize = 0
    var chartBarWidth = 0
    var halfChartBarWidth = 0

    var markHeight = 0
    var markTextPadding = 0
    var markTextSpace = 0
    var markPosition = -1
    private var chartStyle = 0

    //x坐标
    var xList: MutableList<Int> = ArrayList()

    //y坐标
    var yList: MutableList<Int> = ArrayList()

    //y1坐标
    var y1List: MutableList<Int> = ArrayList()


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StudyChartView)
        chartStyle = attributes.getInt(R.styleable.StudyChartView_chart_style, 0)
        attributes.recycle()
    }

    init {

        chartMarginBottom = 50.dp
        chartMarginTop = 30.dp

        textWidth = 16.dp
        textHeight = 20.dp
        textSize = 12.dp
        chartBarWidth = 15.dp
        halfChartBarWidth = 6.dp

        val markTextSize = 10f.dp
        markHeight = 18.dp
        markTextPadding = 5.dp
        markTextSpace = 50.dp
        paint.isAntiAlias = true
        y1Paint.color = Color.parseColor("#5FC0FF")
        y1Paint.style = Paint.Style.FILL
        markPaint.isAntiAlias = true
        markPaint.textSize = markTextSize
        markPaint.typeface = Typeface.SANS_SERIF
        linePaint.isAntiAlias = true
        linePaint.style = Paint.Style.STROKE
        linePaint.color = Color.parseColor("#A7E3FF")

        initAttrs(context, attrs)
    }

    fun setChartList(list: List<ChartData>) {
        this.list = list
        xList.clear()
        yList.clear()
        y1List.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        xList.clear()
        yList.clear()
        y1List.clear()
        if (list.isEmpty()) {
            return
        }
        val size = list.size
        val xSpace = width / (size + 1)
        val maxChartHeight = height - chartMarginBottom - chartMarginTop
        var maxData = 0
        for (i in 0 until size) {
            val chartData = list[i]
            val data = chartData.y
            if (data > maxData) {
                maxData = data
            }
        }
        for (i in 0 until size) {
            val i1 = xSpace + xSpace * i
            xList.add(i1)
            val chartData = list[i]
            val data = chartData.y
            if (maxData > 0) {
                yList.add(maxChartHeight * data / maxData)
            } else {
                yList.add(0)
            }
        }
        paint.textSize = textSize.toFloat()
        for (i in 0 until size) {
            paint.color = Color.parseColor("#333333")
            canvas.drawText(
                list[i].x,
                (xList[i] - textWidth).toFloat(),
                (height - textHeight).toFloat(),
                paint
            )
            val yPath = Path()
            val yHeight = yList[i]
            if (yHeight > 0) {
                yPath.moveTo(
                    (xList[i] - chartBarWidth / 2).toFloat(),
                    (height - chartMarginBottom).toFloat()
                )
                yPath.lineTo(
                    (xList[i] - chartBarWidth / 2).toFloat(),
                    (height - chartMarginBottom - yHeight).toFloat()
                )
                yPath.arcTo(
                    (xList[i] - chartBarWidth / 2).toFloat(),
                    (height - chartMarginBottom - yHeight - chartBarWidth / 2).toFloat(),
                    (xList[i] + chartBarWidth / 2).toFloat(),
                    (height - chartMarginBottom - yHeight + chartBarWidth / 2).toFloat(),
                    -180f,
                    180f,
                    false
                )
                yPath.lineTo(
                    (xList[i] + chartBarWidth / 2).toFloat(),
                    (height - chartMarginBottom).toFloat()
                )
                yPath.close()
                paint.color = Color.parseColor("#5FC0FF")
                paint.style = Paint.Style.FILL
                canvas.drawPath(yPath, paint)
            }
            if (chartStyle == 1) {
                val y1Path = Path()
                val y1Height = y1List[i]
                if (y1Height > 0) {
                    y1Path.moveTo(
                        (xList[i] - chartBarWidth / 2).toFloat(),
                        (height - chartMarginBottom).toFloat()
                    )
                    y1Path.lineTo(
                        (xList[i] - chartBarWidth / 2).toFloat(),
                        (height - chartMarginBottom - y1Height).toFloat()
                    )
                    y1Path.arcTo(
                        (xList[i] - chartBarWidth / 2).toFloat(),
                        (height - chartMarginBottom - y1Height - chartBarWidth / 2).toFloat(),
                        (xList[i] + chartBarWidth / 2).toFloat(),
                        (height - chartMarginBottom - y1Height + chartBarWidth / 2).toFloat(),
                        -180f,
                        180f,
                        false
                    )
                    y1Path.lineTo(
                        (xList[i] + chartBarWidth / 2).toFloat(),
                        (height - chartMarginBottom).toFloat()
                    )
                    y1Path.close()
                    canvas.drawPath(y1Path, y1Paint)
                }
            }
        }
        if (markPosition != -1) {
            val v = markPaint.measureText("标记")
            val w = markPaint.measureText("标记")
            val markWidth = w + v + markTextSpace + markTextPadding * 2
            val markLeftX: Float
            val xPosition = xList[markPosition]
            markLeftX = if (xPosition < markWidth / 2) {
                //从最左开始画
                0f
            } else if (width - xPosition < markWidth / 2) {
                //从最右开始画
                width - markWidth
            } else {
                //从中心点开始
                xPosition - markWidth / 2
            }
            markPaint.color = Color.parseColor("#FFBF17")
            canvas.drawRoundRect(
                markLeftX,
                0f,
                markWidth + markLeftX,
                markHeight.toFloat(),
                (markHeight / 2).toFloat(),
                (markHeight / 2).toFloat(),
                markPaint
            )
            markPaint.color = Color.parseColor("#4C4242")
            canvas.drawText(
                "标记",
                markLeftX + markTextPadding,
                (markHeight - markTextPadding).toFloat(),
                markPaint
            )
            canvas.drawText(
                "标记",
                markLeftX + markTextPadding + v + markTextSpace,
                (markHeight - markTextPadding).toFloat(),
                markPaint
            )
            val yHeight = yList[markPosition]
        }
    }

}