package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R
import com.example.doreamon.entity.ChartData


/**
 * 双折线图
 */
class DoubleBrokenLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    private val paint = Paint()
    private val y1Paint = Paint().apply {
        color = Color.parseColor("#5CA2FF")
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
        isAntiAlias = true
    }

    private val y2Paint = Paint().apply {
        color = Color.parseColor("#FF8080")
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
        isAntiAlias = true
    }


    private val textPaint = Paint().apply {
        this.isAntiAlias = true
        color = Color.parseColor("#333333")
        textAlign = Paint.Align.CENTER
        textSize = 12f.dp
        typeface = Typeface.SANS_SERIF
    }

    private val textY1Paint = Paint().apply {
        this.isAntiAlias = true
        color = Color.parseColor("#5CA2FF")
        textAlign = Paint.Align.CENTER
        textSize = 12f.dp
        typeface = Typeface.SANS_SERIF
    }


    private val textY2Paint = Paint().apply {
        this.isAntiAlias = true
        color = Color.parseColor("#FF8080")
        textAlign = Paint.Align.CENTER
        textSize = 12f.dp
        typeface = Typeface.SANS_SERIF
    }

    private val linePaint1 = Paint().apply {
        color = Color.parseColor("#5CA2FF")
        style = Paint.Style.STROKE
        strokeWidth = 0.8f.dp
        isAntiAlias = true
    }

    private val linePaint2 = Paint().apply {
        color = Color.parseColor("#295CA2FF")
        style = Paint.Style.STROKE
        strokeWidth = 0.3f.dp
        isAntiAlias = true
    }

    private var list: List<ChartData> = ArrayList()

    private var chartMarginBottom = 30f.dp
    private val barMarginTop = 40f.dp
    private val barMarginBottom = 20f.dp

    private val barMarginLeft = 40f.dp

    //图表可绘制区域最高高度
    private var yMaxHeight: Float = 0f

    private var viewWidth: Float = 0f
    private var viewHeight: Float = 0f


    //以左上角为（0,0）点
    //x坐标
    private var xList: MutableList<Float> = ArrayList()

    //y坐标
    private var y1List: MutableList<Float> = ArrayList()

    //y1坐标
    private var y2List: MutableList<Float> = ArrayList()

    //1折线
    private val line1Path = Path()


    //2折线
    private val line2Path = Path()


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
        y1List.clear()
        y2List.clear()

        if (size == 0) return

        val xSpace = (viewWidth - barMarginLeft) / size
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
            val chartData = list[i]
            val data1 = chartData.y1
            if (data1 > maximumY) {
                maximumY = data1
            }
        }
        for (i in 0 until size) {
            val i1 = (xSpace / 2 + xSpace * i) + barMarginLeft
            xList.add(i1)
            val chartData = list[i]
            val data = chartData.y
            if (maximumY > 0) {
                y1List.add(yMaxHeight - availableChartHeight * data / maximumY)
            } else {
                y1List.add(yMaxHeight)
            }

            val data1 = chartData.y1
            if (maximumY > 0) {
                y2List.add(yMaxHeight - availableChartHeight * data1 / maximumY)
            } else {
                y2List.add(yMaxHeight)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(300.dp, widthMeasureSpec)
        val height = resolveSize(150.dp, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (list.isEmpty()) {
            return
        }

        val size = list.size

        //白色背景
        paint.color = Color.parseColor("#F2F8FF")
        canvas.drawRoundRect(
            0f,
            0f,
            viewWidth,
            viewHeight,
            10f.dp,
            10f.dp,
            paint
        )


        line1Path.reset()
        line2Path.reset()

        canvas.drawText("时间", 20f.dp, (viewHeight - 10f.dp), textY1Paint)
        canvas.drawText("答题数", 20f.dp, 20f.dp, textY1Paint)
        canvas.drawText("成绩", viewWidth - 20f.dp, 20f.dp, textY2Paint)
        canvas.drawLine(
            barMarginLeft,
            barMarginTop,
            barMarginLeft,
            viewHeight - chartMarginBottom,
            linePaint1
        )

        canvas.drawLine(
            barMarginLeft,
            viewHeight - chartMarginBottom,
            viewWidth - 5f.dp,
            viewHeight - chartMarginBottom,
            linePaint1
        )


        //画3个等分线
        linePaint2.strokeWidth = 0.5f.dp
        val spaceAverageGuideLine = (viewHeight - barMarginTop - chartMarginBottom) / 4
        for (count in 1..3) {
            canvas.drawLine(
                barMarginLeft,
                barMarginTop + spaceAverageGuideLine * count,
                viewWidth - 5f.dp,
                barMarginTop + spaceAverageGuideLine * count,
                linePaint2
            )
        }

        for (i in 0 until xList.size) {
            canvas.drawLine(
                xList[i],
                barMarginTop,
                xList[i],
                viewHeight - chartMarginBottom,
                linePaint2
            )
        }


        var path1IsLine = false
        var path2IsLine = false
        for (i in 0 until size) {
            textPaint.color = Color.parseColor("#666666")
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

            val currY1 = y1List[i]
            if (i == 0) {
                if (list[i].y != 0) {
                    line1Path.moveTo(currX, currY1)
                    path1IsLine = true
                }
            } else {
                if (list[i - 1].y == 0 && list[i].y != 0) {
                    line1Path.moveTo(currX, currY1)
                    path1IsLine = true
                } else if (list[i].y != 0) {
                    if (path1IsLine) {
                        line1Path.lineTo(currX, currY1)
                    } else {
                        line1Path.moveTo(currX, currY1)
                        path1IsLine = true
                    }
                }
            }

            val currY2 = y2List[i]
            if (i == 0) {
                if (list[i].y1 != 0) {
                    line2Path.moveTo(currX, currY2)
                    path2IsLine = true
                }
            } else {
                if (list[i - 1].y1 == 0 && list[i].y1 != 0) {
                    line2Path.moveTo(currX, currY2)
                    path2IsLine = true
                } else if (list[i].y1 != 0) {
                    if (path2IsLine) {
                        line2Path.lineTo(currX, currY2)
                    } else {
                        line2Path.moveTo(currX, currY2)
                        path2IsLine = true
                    }
                }
            }
        }

        canvas.drawPath(line1Path, y1Paint)
        canvas.drawPath(line2Path, y2Paint)


        for (i in 0 until size) {

            val currY = y1List[i]

            if (list[i].y != 0) {
                paint.color = Color.WHITE
                paint.style = Paint.Style.FILL
                canvas.drawCircle(xList[i], currY, 5f.dp, paint)

                paint.color = Color.parseColor("#C4DDFF")
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 1f.dp
                canvas.drawCircle(xList[i], currY, 5f.dp, paint)

                paint.color = Color.parseColor("#5CA2FF")
                paint.style = Paint.Style.FILL
                canvas.drawCircle(xList[i], currY, 3.6f.dp, paint)
            }
        }


        for (i in 0 until size) {

            val currY = y2List[i]

            if (list[i].y1 != 0) {
                paint.color = Color.WHITE
                paint.style = Paint.Style.FILL
                canvas.drawCircle(xList[i], currY, 5f.dp, paint)

                paint.color = Color.parseColor("#FFD6D6")
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 1f.dp
                canvas.drawCircle(xList[i], currY, 5f.dp, paint)

                paint.color = Color.parseColor("#FF8080")
                paint.style = Paint.Style.FILL
                canvas.drawCircle(xList[i], currY, 3.6f.dp, paint)
            }
        }

    }

}