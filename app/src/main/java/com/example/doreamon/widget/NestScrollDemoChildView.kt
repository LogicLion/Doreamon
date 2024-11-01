package com.example.doreamon.widget

import androidx.core.view.NestedScrollingChild3

/**
 * @author wzh
 * @date 2022/9/27
 */
class NestScrollDemoChildView :NestedScrollingChild3{

    /**
     * 开启/关闭嵌套滑动
     */
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    /**
     * 返回是否开启/关闭嵌套滑动
     */
    override fun isNestedScrollingEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * 开启滚动时候时候调用,用来通知parentView开始滚动,常在TouchEvent.ACTION_DOWN事件中调用
     * @param axes: 滚动方向
    SCROLL_AXIS_HORIZONTAL 水平
    SCROLL_AXIS_VERTICAL 垂直
    SCROLL_AXIS_NONE 没有方向
     */
    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun startNestedScroll(axes: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * 停止滚动时候调用,用来通知parentView停止滚动,常在TouchEvent.ACTION_UP / ACTION_CANCLE 中调用
     */
    override fun stopNestedScroll(type: Int) {
        TODO("Not yet implemented")
    }

    override fun stopNestedScroll() {
        TODO("Not yet implemented")
    }

    /**
     * 判断当前view是否有嵌套滑动的parentView正在接受事件
    tips:代理给 NestedScrollingChildHelper.hasNestedScrollingParent()即可
     */
    override fun hasNestedScrollingParent(type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasNestedScrollingParent(): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * 当前view消费滚动距离后调用该方法,吧剩下的滚动距离传递给parentView,
    如果当前没有发生嵌套滚动,或者不支持嵌套滚动,那么该方法就没啥用.. 常在TouchEvent.ACTION_MOVE中调用
    tips:代理给NestedScrollingChildHelper.dispatchNestedScroll()即可

    @param dxConsumed: 已经消费的水平(x)方向距离
    @param dyConsumed: 已经消费的垂直方(y)向距离
    @param dxUnconsumed: 未消费过的水平(x)方向距离
    @param dyUnconsumed: 未消费过的垂直(y)方向距离
    @param offsetInWindow:  滑动之前和滑动之后的偏移量
    if(offsetInWindow != null){
    x = offsetInWindow[0]
    y = offsetInWindow[1]
    }
    return true: 有嵌套滚动(parentView extents NestedScrollingParent)

     */
    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        TODO("Not yet implemented")
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * 将事件分发给 parentView,如果 parentView 消费则返回true
    常在TouchEvent.ACTION_MOVE中调用
    tips:代理给 NestedScrollingChildhelper.dispatchNestedPreScroll()即可

    @param dx:水平(x)滚动的距离(以像素为单位)
    @param dy:垂直(y)滚动的距离(以像素为单位)
    @param consumed: 主要用来父容器消费封装,并且通知子容器 x = consumed[0]; y = consumed[1];
    @param offsetInWindow:滑动之前和滑动之后的偏移量
    return true: 表示父容器消费了事件

     */
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * 用来处理惯性滑动
    tips:代理给 NestedScrollingChildhelper.dispatchNestedFling()即可

    @param velocityX: 用来处理x轴惯性滑动
    @param velocityY: 用来处理y轴惯性滑动
    @param consumed: 当前view是否消费了事件
    return true: 有嵌套滚动(parentView extents NestedScrollingParent)

     */
    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * 分发fling事件给parentView
    tips:代理给 NestedScrollingChildhelper.dispatchNestedPreFling()即可

    @param velocityX: 用来处理x轴惯性滑动
    @param velocityY: 用来处理y轴惯性滑动
    return true: 父容器消费了事件

     */
    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }
}