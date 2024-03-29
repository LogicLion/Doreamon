package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.doreamon.treasure.ext.dp
import java.lang.ref.WeakReference

/**
 * 舒尔特方格
 * @author wzh
 * @date 2023/8/9
 */
class CustomGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val paint = Paint()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var numRows = 3
    private var numCols = 3
    private val originTextList = mutableListOf<String>()
    private val shuffledTextList = mutableListOf<String>()
    private var cellWidth = 0f
    private var cellHeight = 0f
    private var gridListener: OnGridListener? = null

    private var clickErrorPosition: Int = -1
    private val disturbanceTermText = "干扰项"


    //记录已完成的位置
    private val clickedPositionSet = HashSet<Int>()


    // 下一个预期的字符
    private var expectedCharacterIndex = 0

    private val mHandler: MyHandler = MyHandler(this)


    private var correctPaint = Paint().apply {
        color = Color.parseColor("#5CA2FF")
        style = Paint.Style.FILL
    }

    private var wrongPaint = Paint().apply {
        color = Color.parseColor("#FF5454")
        style = Paint.Style.FILL
    }

    private class MyHandler(view: CustomGridView) : Handler(Looper.getMainLooper()) {
        private val weakReference: WeakReference<CustomGridView> = WeakReference(view)

        override fun handleMessage(msg: Message) {
            val outerClass = weakReference.get()
            if (outerClass != null) {
                outerClass.clickErrorPosition = -1
                outerClass.invalidate()
            }
        }
    }

    private val gestureDetector = GestureDetectorCompat(context, MyGestureListener())

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 0.5f.dp
        paint.style = Paint.Style.STROKE
        textPaint.color = Color.BLACK
        textPaint.textSize = 30f.dp
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(heightSize, heightSize)
        }

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, widthSize)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cellWidth = w / numCols.toFloat()
        cellHeight = h / numRows.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val metricsInt = textPaint.fontMetricsInt
        for (i in shuffledTextList.indices) {

            val col = i % numCols
            val row = i / numCols

            if (clickedPositionSet.contains(i)) {
                canvas.drawRect(
                    col * cellWidth,
                    row * cellHeight,
                    col * cellWidth + cellWidth,
                    row * cellHeight + cellHeight,
                    correctPaint
                )
            }

            if (clickErrorPosition == i) {
                canvas.drawRect(
                    col * cellWidth,
                    row * cellHeight,
                    col * cellWidth + cellWidth,
                    row * cellHeight + cellHeight,
                    wrongPaint
                )
            }

            if (shuffledTextList[i] != disturbanceTermText) {
                val centerX = col * cellWidth + cellWidth / 2f
                val centerY =
                    row * cellHeight + cellHeight / 2f - (metricsInt.descent + metricsInt.ascent) / 2

                canvas.drawText(shuffledTextList[i], centerX, centerY, textPaint)
            }

        }

        for (i in 0 until numRows + 1) {
            val y = i * cellHeight
            canvas.drawLine(0f, y, width.toFloat(), y, paint)
        }
        for (i in 0 until numCols + 1) {
            val x = i * cellWidth
            canvas.drawLine(x, 0f, x, height.toFloat(), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


    fun setGridListener(gridListener: OnGridListener) {
        this.gridListener = gridListener
    }


    fun setTextList(texts: List<String>, rows: Int, cols: Int) {
        numRows = rows
        numCols = cols
        this.originTextList.clear()
        this.originTextList.addAll(texts)

        val copyList = texts.toMutableList()

        if (originTextList.size < numRows * numCols) {
            val extraCount = numRows * numCols - originTextList.size
            for (i in 0 until extraCount) {
                copyList.add(disturbanceTermText)
            }
        }

        copyList.shuffle()
        shuffledTextList.clear()
        shuffledTextList.addAll(copyList)

        invalidate()
    }

    interface OnGridListener {
        fun onClick(position: Int)

        fun onFinish()

    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (expectedCharacterIndex < originTextList.size) {
                val col = (e.x / cellWidth).toInt()
                val row = (e.y / cellHeight).toInt()
                val position = row * numCols + col
                gridListener?.onClick(position)

                if (shuffledTextList[position] == originTextList[expectedCharacterIndex]) {
                    clickedPositionSet.add(position)
                    expectedCharacterIndex++
                    if (expectedCharacterIndex == originTextList.size) {
                        gridListener?.onFinish()
                    }
                    invalidate()
                } else if (shuffledTextList[position] != disturbanceTermText
                    && !clickedPositionSet.contains(
                        position
                    )
                ) {
                    clickErrorPosition = position
                    invalidate()

                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.sendEmptyMessageDelayed(position, 100)
                }
            }

            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }
}