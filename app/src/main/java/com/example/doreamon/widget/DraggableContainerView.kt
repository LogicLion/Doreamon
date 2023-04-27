package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.customview.widget.ViewDragHelper
import com.doreamon.treasure.ext.toast
import kotlin.math.max
import kotlin.math.min

/**
 * 可任意拖拽view，释放自动贴边，并处理上层滑动和下层滑动的滑动冲突
 * 目标view加上“tag == "draggable"即可被捕获
 * @author wzh
 * @date 2022/10/25
 */
class DraggableContainerView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : ConstraintLayout(mContext, attr) {

    private val TAG = "DraggableContainerView"
    private lateinit var childView: View
    var viewDragHelper: ViewDragHelper

    /**
     * 是否产生了拖拽
     */
    private var isScroll: Boolean = false



    private var mXOffset: Int = -1
    private var mYOffset: Int = -1

    init {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, ViewDragCallBack())
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        Log.v(TAG, "onFinishInflate")

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.tag == "draggable") {
                childView = child
                return
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //让viewDragHelper接管滑动事件
        viewDragHelper.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        //当子view中含有recyclerView时，将执行不到onTouchEvent，方案之一是手动调用
//        viewDragHelper.processTouchEvent(ev)

        //方案二是处理一下拦截逻辑,判断在点击范围内，直接拦截，交由自己的onTouchEvent
        val shouldInterceptTouchEvent = viewDragHelper.shouldInterceptTouchEvent(ev)
        return if (findTopChildUnder(ev.x.toInt(), ev.y.toInt())) {

            //请求上层控件不要拦截
            requestDisallowInterceptTouchEvent(true)

            //true，点击范围内有可捕获控件时，拦截掉点击事件，这样onTouchEvent就能调用，否则下层控件可能会拿走
            true
        } else {
            shouldInterceptTouchEvent
        }
    }


    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            Log.v(TAG, "continueSettling")
            invalidate()
        } else {
            //这里记录最终拖动后view的偏移量
            mXOffset = childView.left
            mYOffset = childView.top
            Log.v(TAG, "mXOffset:${mXOffset}")
            Log.v(TAG, "mYOffset:${mYOffset}")
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.v(TAG, "onLayout")

        //在使用ViewDragHelper时，在更新ViewDragHelper内部的view状态时候，都会走requestLayout（），
        //导致viewroot树重新 mesure/onlayout/draw ，在layout的时候会把ViewDragHelper的view重新排版
        //所以必须初始化上次拖动后view的偏移位置

        Log.v(TAG, "onLayout:mXOffset:${mXOffset}")
        Log.v(TAG, "onLayout:mYOffset:${mYOffset}")
//        childView.offsetLeftAndRight(mXOffset)
//        childView.offsetTopAndBottom(mYOffset)
        if (mXOffset == -1) {
            mXOffset = childView.left
        }
        if (mYOffset == -1) {
            mYOffset = childView.top
        }
        childView.layout(
            mXOffset,
            mYOffset,
            mXOffset + childView.width,
            mYOffset + childView.height
        )

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
                childView.callOnClick()
//                return
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