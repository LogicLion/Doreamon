package com.example.doreamon.widget.nest

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild3

/**
 * @author wzh
 * @date 2022/10/12
 */
class NestedScrollingWebView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) :WebView(mContext, attr){

    var  velocityTracker:VelocityTracker = VelocityTracker.obtain()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                velocityTracker.addMovement(event)
            }
        }
        return super.onTouchEvent(event)
    }
}