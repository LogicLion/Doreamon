package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.webkit.WebView
import kotlin.math.absoluteValue

/**
 * 解决webView的滑动冲突
 * @author wzh
 * @date 2022/4/28
 */

class CustomWebView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = -1
) : WebView(mContext, attr, defStyle) {
    val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop


    var startX: Int = 0
    var startY: Int = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                //告诉父控件不要拦截
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {

                val x = ev.x
                val y = ev.y

//                parent.requestDisallowInterceptTouchEvent(tr)

                val dx = x - startX
                val dy = y - startY

                val absX = dx.absoluteValue
                val absY = dy.absoluteValue
                if (absX > touchSlop || absY > touchSlop) {
                    if (absX < absY) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }

        }

        return super.dispatchTouchEvent(ev)
    }

}