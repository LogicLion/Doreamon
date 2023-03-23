package com.example.doreamon.widget.expandabletext

import android.content.Context
import android.os.Build
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author wzh
 * @date 2023/3/17
 */
class ExpandFoldTextView @JvmOverloads constructor(
    mContext: Context,
    attr: AttributeSet? = null,
    val defStyle: Int = 0
) : AppCompatTextView(mContext, attr) {
    var viewWidth = 0
    private val foldLine = 3
    private var expandLine = -1

    private var originalText: CharSequence? = null

    private var mExpandable = false
    private var isExpand = false
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        calculateLineCount()
        updateExpandState()
    }


    fun setOriginalText(originalText: CharSequence) {
        this.originalText = originalText
        text = originalText
        calculateLineCount()
        updateExpandState()
    }

    private fun calculateLineCount() {
        if (viewWidth <= 0 || originalText.isNullOrBlank()) {
            return
        }
        mExpandable = false
        val mOpenSpannableStr = SpannableStringBuilder(originalText)

        if (maxLines != -1) {
            val layout = createStaticLayout(mOpenSpannableStr)
            mExpandable = layout.lineCount > foldLine
            expandLine = layout.lineCount
        }
    }


    fun switch() {
        isExpand = !isExpand
        updateExpandState()
    }

    fun expand() {
        if (!isExpand) {
            isExpand = true
            updateExpandState()
        }
    }

    fun fold() {
        if (isExpand) {
            isExpand = false
            updateExpandState()
        }
    }

    private fun updateExpandState() {
        if (expandLine == -1) return
        if (mExpandable) {
            if (isExpand) {
                setLines(expandLine)
            } else {
                setLines(foldLine)
            }
        } else {
            setLines(expandLine)
        }
    }

    private fun createStaticLayout(spannable: SpannableStringBuilder): Layout {
        val contentWidth: Int = viewWidth - paddingLeft - paddingRight
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val builder = StaticLayout.Builder.obtain(
                spannable, 0, spannable.length,
                paint, contentWidth
            )
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL)
            builder.setIncludePad(includeFontPadding)
            builder.setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            builder.build()
        } else {
            StaticLayout(
                spannable, paint, contentWidth, Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier, lineSpacingExtra, includeFontPadding
            )
        }
    }
}