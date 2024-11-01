package com.example.doreamon.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R
import com.doreamon.treasure.utils.dip2px

/**
 * 星星等级控件
 * @author wzh
 * @date 2022/5/23
 */
public class LevelStarView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : View(mContext, attr) {

    var bitmap1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_difficulty)
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var starLevel: Int = 3

    val starSize = 11.dp
    val starMargin = 3.dp


    /**
     * 设置等级
     */
    fun setLevel(level: Int) {
        starLevel = level

        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (starLevel <= 0) {
            return
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            starLevel * starSize + (starLevel - 1) * starMargin
        }

        val measureHeight = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            starSize
        }

        setMeasuredDimension(measuredWidth, measureHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0..starLevel) {
            if (i == 0) {
                canvas.drawBitmap(bitmap1, 0f, 0f, paint)
            } else {
                canvas.drawBitmap(bitmap1, i * (starMargin + starSize).toFloat(), 0f, paint)
            }
        }
    }

}