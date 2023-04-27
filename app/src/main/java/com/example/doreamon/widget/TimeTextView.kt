package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ReplacementSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 将一个秒值转化成时分秒并按
 * 0h00′00″
 * 格式显示，其中h要缩小一定比例并顶部对齐
 * @author wzh
 */
class TimeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs) {
    fun setTime(seconds: Long) {
        val hours = seconds / 3600
        val minutes = seconds % 3600 / 60
        val remainSeconds = seconds % 60

        val spannableStringBuilder = SpannableStringBuilder()

        // 添加小时部分
        spannableStringBuilder.append(String.format("%dh", hours))
        val hIndex = spannableStringBuilder.length - 1

        // 添加分钟部分
        spannableStringBuilder.append(String.format("%02d", minutes))

        // 添加秒钟部分
        spannableStringBuilder.append("'")
        spannableStringBuilder.append(String.format("%02d\"", remainSeconds))

        spannableStringBuilder.setSpan(
            TopAlignTextSpan(0.6f),
            hIndex,
            hIndex + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableStringBuilder
    }


    class TopAlignTextSpan(private val textSizeScale: Float) :
        ReplacementSpan() {

        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            val textPaint = getCustomTextPaint(paint)
            return (textPaint.measureText(text, start, end)).toInt()
        }

        override fun draw(
            canvas: Canvas, text: CharSequence, start: Int, end: Int,
            x: Float, top: Int, y: Int, bottom: Int, paint: Paint
        ) {
            val textPaint = getCustomTextPaint(paint)
            val metricsInt = textPaint.fontMetricsInt
            val srcMetricsInt = paint.fontMetricsInt
            canvas.drawText(
                text,
                start,
                end,
                x,
                (y + srcMetricsInt.ascent - metricsInt.ascent).toFloat(),
                textPaint
            )
        }

        override fun updateMeasureState(p: TextPaint) {
            p.textSize = p.textSize * textSizeScale
        }

        private fun getCustomTextPaint(srcPaint: Paint): Paint {
            val srcTextSize = srcPaint.textSize
            val paint = Paint(srcPaint)
            paint.textSize = srcTextSize * textSizeScale
            return paint
        }
    }


}