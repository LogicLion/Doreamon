package com.example.doreamon.widget

import android.view.View
import androidx.core.view.NestedScrollingParent3

/**
 * @author wzh
 * @date 2022/9/27
 */
class NestScrollDemoParentView :NestedScrollingParent3 {

    /**
     * 当NestedScrollingChildHelper.startNestedScroll()时候执行,用来接受ChildView#onTouchEvent#DOWN事件
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * 当 onStartNestedScroll() 返回true时候执行,常用来做一些初始化工作
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        TODO("Not yet implemented")
    }

    /**
     * 当NestedScrollingChildHelper.stopNestedScroll()时候执行
     */
    override fun onStopNestedScroll(target: View, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View) {
        TODO("Not yet implemented")
    }


    /**
     * 当NestedScrollingChildHelper.dispatchNestedScroll()时候调用
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        TODO("Not yet implemented")
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * 获取滚动的方向
     */
    override fun getNestedScrollAxes(): Int {
        TODO("Not yet implemented")
    }
}