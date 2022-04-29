package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import android.widget.Switch

/**
 * @author wzh
 * @date 2022/4/28
 */
class CustomWebView(private val mContext:Context, private val attr: AttributeSet?=null,val  defStyle:Int=-1):WebView(mContext,attr,defStyle) {
    init{

    }


    var startX:Int=0
    var startY:Int=0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN->{
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                //告诉父控件不要拦截
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE->{

                val x = ev.x
                val y = ev.y
            }

        }

        return super.dispatchTouchEvent(ev)
    }

}