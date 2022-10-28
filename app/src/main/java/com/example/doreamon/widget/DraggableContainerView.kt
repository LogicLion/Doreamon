package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import com.example.doreamon.ext.toast
import kotlin.math.max
import kotlin.math.min

/**
 * 可任意拖拽view，释放自动贴边
 * @author wzh
 * @date 2022/10/25
 */
class DraggableContainerView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : FrameLayout(mContext, attr) {

    private val TAG = "DraggableContainerView"
    private lateinit var childView: View
    var viewDragHelper: ViewDragHelper

    private var isScroll: Boolean = false

    init {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, ViewDragCallBack())
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.tag == "draggable") {
                childView = child
                return
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        //当子view中含有recyclerView时，将执行不到onTouchEvent，方案之一是手动调用
//        viewDragHelper.processTouchEvent(ev)

        //方案二是处理一下拦截逻辑,判断在点击范围内，直接拦截，交由onTouchEvent
        val shouldInterceptTouchEvent = viewDragHelper.shouldInterceptTouchEvent(ev)
        if (findTopChildUnder(ev.x.toInt(), ev.y.toInt())) {
            //点击范围内有可捕获控件时，拦截掉点击事件，这样onTouchEvent就能调用
            return true
        } else {
            return shouldInterceptTouchEvent
        }
    }


    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.v(TAG, "onLayout")
    }


    inner class ViewDragCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            Log.v(TAG, "tryCaptureView")

            isScroll = false
            return child == childView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            Log.v(TAG, "clampViewPositionHorizontal,left:${left},dx:${dx}")
            return max(min(left, width - childView.width), 0)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            Log.v(TAG, "clampViewPositionVertical,top:${top},dy:${dy}")
            return max(min(top, height - childView.height), 0)
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)

            isScroll = true
            Log.v(TAG, "onViewPositionChanged:left:${left},top:${top},dx:${dx},dy:${dy}")
        }

        override fun onViewDragStateChanged(state: Int) {
            when (state) {
                ViewDragHelper.STATE_DRAGGING -> {
                    Log.v(TAG, "onViewDragStateChanged:STATE_DRAGGING")
                }

                ViewDragHelper.STATE_IDLE -> {
                    Log.v(TAG, "onViewDragStateChanged:STATE_IDLE")
                }

                ViewDragHelper.STATE_SETTLING -> {
                    Log.v(TAG, "onViewDragStateChanged:STATE_SETTLING")
                }
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            Log.v(TAG, "onViewReleased")
            if (!isScroll) {
                "这是点击事件".toast()
                return
            }

            if (releasedChild.left < (width - childView.width) / 2) {

                viewDragHelper.smoothSlideViewTo(releasedChild, 0, releasedChild.top)
            } else {
                viewDragHelper.smoothSlideViewTo(
                    releasedChild,
                    width - childView.width,
                    releasedChild.top
                )
            }

            //重绘、触发computeScroll
            invalidate()
        }

    }

    /**
     *判断可捕获view是否在点击范围内
     */
    private fun findTopChildUnder(x: Int, y: Int): Boolean {
        if (x > childView.left && x < childView.right
            && y > childView.top && y < childView.bottom
        ) {

            return true
        }

        return false
    }
}