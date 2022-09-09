package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.doreamon.R
import kotlin.math.max

/**
 * 指示点view
 * @author wzh
 * @date 2022/8/19
 */
class IndicatorView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : View(mContext, attr) {

    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val unSelectedPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 选中指示点颜色
     */
    private var selectedColor = 0

    /**
     * 未选中指示点颜色
     */
    private var unSelectedColor = 0

    /**
     * 指示点总数
     */
    private var indicatorSum = 0

    /**
     * 指示点选中位置
     */
    private var selectIndex = 0

    /**
     * 选中指示点宽度
     */
    private var selectedIndicatorWidth = 0f

    /**
     * 选中指示点高度
     */
    private var selectedIndicatorHeight = 0f

    /**
     * 选中指示点圆角半径
     */
    private var selectedIndicatorRadius = 0f

    /**
     * 未选中指示点宽度
     */
    private var unSelectedIndicatorWidth = 0f

    /**
     * 未选中指示点高度
     */
    private var unSelectedIndicatorHeight = 0f

    /**
     * 未选中指示点圆角半径
     */
    private var unSelectedIndicatorRadius = 0f

    /**
     * 指示点间隔距离
     */
    private var indicatorPadding = 0f


    init {
        initAttrs(context, attr)
        selectedPaint.color = selectedColor
        unSelectedPaint.color = unSelectedColor
    }

    private fun initAttrs(context: Context, attr: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attr, R.styleable.IndicatorView)
        selectedColor = attributes.getColor(
            R.styleable.IndicatorView_indicator_selected_color,
            Color.RED
        )

        unSelectedColor = attributes.getColor(
            R.styleable.IndicatorView_indicator_unselected_color,
            Color.GRAY
        )

        indicatorSum = attributes.getInt(R.styleable.IndicatorView_indicator_sum, 0)
        selectIndex = attributes.getInt(R.styleable.IndicatorView_indicator_select_index, 0)
        selectedIndicatorWidth =
            attributes.getDimension(R.styleable.IndicatorView_indicator_selected_width, 0f)
        selectedIndicatorHeight =
            attributes.getDimension(R.styleable.IndicatorView_indicator_selected_height, 0f)
        selectedIndicatorRadius =
            attributes.getDimension(R.styleable.IndicatorView_indicator_selected_radius, 0f)

        unSelectedIndicatorWidth =
            attributes.getDimension(R.styleable.IndicatorView_indicator_unselected_width, 0f)
        unSelectedIndicatorHeight =
            attributes.getDimension(R.styleable.IndicatorView_indicator_unselected_height, 0f)
        unSelectedIndicatorRadius =
            attributes.getDimension(R.styleable.IndicatorView_indicator_unselected_radius, 0f)

        indicatorPadding =
            attributes.getDimension(R.styleable.IndicatorView_indicator_padding, 0f)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (indicatorSum <= 0) {
            return
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            selectedIndicatorWidth + (indicatorSum - 1) * (unSelectedIndicatorWidth + indicatorPadding)
        }

        val measureHeight = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            max(unSelectedIndicatorHeight, selectedIndicatorHeight)
        }

        setMeasuredDimension(measuredWidth.toInt(), measureHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (indicatorSum <= 0) return


        var x = 0f

        val selectIndicatorTop = (height - selectedIndicatorHeight) / 2
        val selectIndicatorBottom = (height + selectedIndicatorHeight) / 2
        val unSelectIndicatorTop = (height - unSelectedIndicatorHeight) / 2
        val unSelectIndicatorBottom = (height + unSelectedIndicatorHeight) / 2
        for (i in 0..indicatorSum) {
            if (i == selectIndex) {
                canvas.drawRoundRect(
                    x,
                    selectIndicatorTop,
                    x + selectedIndicatorWidth,
                    selectIndicatorBottom,
                    selectedIndicatorRadius,
                    selectedIndicatorRadius,
                    selectedPaint
                )
                x += selectedIndicatorWidth
            } else {
                canvas.drawRoundRect(
                    x,
                    unSelectIndicatorTop,
                    x + unSelectedIndicatorWidth,
                    unSelectIndicatorBottom,
                    unSelectedIndicatorRadius,
                    unSelectedIndicatorRadius,
                    unSelectedPaint
                )

                x += unSelectedIndicatorWidth
            }

            if (i < indicatorSum - 1) {
                x += indicatorPadding
            }

        }
    }

    fun setSelectPosition(position: Int) {
        selectIndex = position
        if (selectIndex >= indicatorSum) throw IndexOutOfBoundsException("超出指示点选中范围")
        requestLayout()
        invalidate()
    }

    fun setIndicatorSum(sum: Int) {
        indicatorSum = sum
        requestLayout()
        invalidate()
    }
}