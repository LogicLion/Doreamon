package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ScrollView

/**
 * 监听scrollView滑动到底部
 * @author wzh
 * @date 2022/11/23
 */
class CustomScrollView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : ScrollView(mContext, attr) {

    private val TAG = "CustomScrollView"
    private var scrollRange: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        scrollRange = getMaxScrollY()
    }


    private fun getMaxScrollY(): Int {
        var scrollRange = 0
        if (childCount > 0) {
            val child = getChildAt(0)
            scrollRange = Math.max(
                0,
                child.measuredHeight - (height - paddingBottom - paddingTop)
            )
        }
        return scrollRange
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.v(TAG, "t:" + t + ",oldt:" + oldt)

        if (scrollRange == t) {
            scrollBottom.invoke()
        }
    }

    /**
     * 滑动到底部时调用
     */
    var scrollBottom: () -> Unit = {}


}