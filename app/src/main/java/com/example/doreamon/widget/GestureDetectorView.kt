package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.doreamon.treasure.ext.toast

/**
 * @author wzh
 * @date 2022/9/19
 */
class GestureDetectorView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : View(mContext, attr) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    val gestureListener = object : GestureDetector.SimpleOnGestureListener(){
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)

            "长按".toast()
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }
    }

    val GestureDetector = GestureDetector(mContext, gestureListener)



}