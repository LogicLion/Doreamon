package com.example.doreamon.dsl

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View

/**
 * @author wzh
 * @date 2023/5/5
 */
class DslSpannableStringBuilderImpl : DslSpannableStringBuilder {
    private val builder = SpannableStringBuilder()

    //记录上次添加文字后最后的索引值
    var lastIndex: Int = 0
    var isClickable = false

    override fun addText(text: String, method: (DslSpanBuilder.() -> Unit)?) {
        val start = lastIndex
        builder.append(text)
        lastIndex += text.length
        val spanBuilder = DslSpanBuilderImpl()
        method?.let { spanBuilder.it() }
        spanBuilder.apply {
            onClickSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                isClickable = true
            }
            if (!useUnderLine) {
                val noUnderlineSpan = NoUnderlineSpan()
                builder.setSpan(noUnderlineSpan, start, lastIndex, Spanned.SPAN_MARK_MARK)
            }

            if (textBold) {
                builder.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            foregroundColorSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            backgroundSpan
        }
    }

    fun build(): SpannableStringBuilder {
        return builder
    }
}

class DslSpanBuilderImpl : DslSpanBuilder {
    var foregroundColorSpan: ForegroundColorSpan? = null
    var backgroundSpan: BackgroundColorSpan? = null
    var onClickSpan: ClickableSpan? = null
    var useUnderLine = true
    var textBold = false


    override fun setColor(color: String) {
        foregroundColorSpan = ForegroundColorSpan(Color.parseColor(color))

    }

    override fun setBold(bold: Boolean) {
        this.textBold = bold
    }


    override fun onClick(useUnderLine: Boolean, onClick: (View) -> Unit) {
        onClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick(widget)
            }
        }
        this.useUnderLine = useUnderLine
    }
}

class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
        ds.bgColor=Color.TRANSPARENT
    }
}


interface DslSpannableStringBuilder {
    //增加一段文字
    fun addText(text: String, method: (DslSpanBuilder.() -> Unit)? = null)
}

interface DslSpanBuilder {
    //设置文字颜色
    fun setColor(color: String)
    //设置点击事件

    fun setBold(bold: Boolean)
    fun onClick(useUnderLine: Boolean = true, onClick: (View) -> Unit)
}