package com.example.doreamon.widget

import android.graphics.PointF
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * 实现一个横向的linearLayoutManager效果
 * @author wzh
 * @date 2022/4/6
 */
class LikeLinearLayoutManager : RecyclerView.LayoutManager(),
    RecyclerView.SmoothScroller.ScrollVectorProvider {


    /**
     * 初始位置
     */
    private var mPendingPosition = RecyclerView.NO_POSITION


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }


    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    /**
     * 布局初始化的方法
     * 键盘弹出或收起会重新回调这个方法
     * scrollToPosition也会，smoothScrollToPosition不会
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)

        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }

        //不支持预测动画，可以直接return
        if (state.isPreLayout) return

        var currentPosition = 0

        //这个是上一次第一个view的滑动距离的左偏移
        var fixOffset = 0

        //处理onLayoutChildren非正常调起的情况，如软键盘调起导致onLayoutChildren
        //当childCount != 0时，证明是已经填充过View的，因为有回收
        //所以直接赋值为第一个child的position就可以
        if (childCount != 0) {
            val firstChild = getChildAt(0)!!
            currentPosition = getPosition(firstChild)
            fixOffset = getDecoratedLeft(firstChild)
        }


        detachAndScrapAttachedViews(recycler)
        var totalSpace = width - paddingRight - fixOffset


        if (mPendingPosition != RecyclerView.NO_POSITION) {
            currentPosition = mPendingPosition
        }
        var left = fixOffset
        var top = 0
        var right: Int
        var bottom: Int

        while (totalSpace > 0 && currentPosition < state.itemCount) {

            val view = recycler.getViewForPosition(currentPosition)
            //1.添加
            addView(view)

            //2.测量，获取尺寸
            measureChild(view, 0, 0)
            right = left + getDecoratedMeasuredWidth(view)
            bottom = top + getDecoratedMeasuredHeight(view)

            //3.布局
            layoutDecorated(view, left, top, right, bottom)

            left += getDecoratedMeasuredWidth(view)
            totalSpace -= getDecoratedMeasuredWidth(view)

            currentPosition++
        }

    }


    /**
     * 会在LayoutManager调用完onLayoutChildren()后调用
     */
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        mPendingPosition = RecyclerView.NO_POSITION
    }

    //scrollHorizontallyBy方法作用是：返回手指在RecyclerView上的移动距离给我们
    //dx>0,从右滑向左，dx<0,从左滑向右
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {
        //填充
        val consumed = fill(dx, recycler)

        //真正移动view的方法
        offsetChildrenHorizontal(-consumed)

        //回收
        recycle(dx, recycler)


        //返回值就是让RecyclerView知道LayoutManager真实的滑动距离，
        //return 0时RecyclerView就会展示overScroll状态以及NestedScrolling的后续处理。
        return consumed
    }

    private fun recycle(dx: Int, recycler: RecyclerView.Recycler) {

        val recyclerViews = hashSetOf<View>()
        if (dx > 0) {
            for (i in 0 until itemCount) {
                val view = getChildAt(i)
                if (view != null) {
                    val right = getDecoratedRight(view)
                    if (right > 0) {
                        break
                    }
                    recyclerViews.add(view)
                }
            }
        }

        if (dx < 0) {
            for (i in childCount - 1 downTo 0) {
                val view = getChildAt(i)
                if (view != null) {
                    val left = getDecoratedLeft(view)
                    if (left < width) {
                        break
                    }
                    recyclerViews.add(view)
                }
            }
        }

        recyclerViews.forEach {
            removeAndRecycleView(it, recycler)
        }

        recyclerViews.clear()

    }


    /**
     * @return 返回实际可滑动距离
     */
    private fun fill(dx: Int, recycler: RecyclerView.Recycler): Int {

        //要填充的位置
        var fillPosition = RecyclerView.NO_POSITION

        //取一个滑动距离的绝对值,方便计算
        val absDelta = kotlin.math.abs(dx)

        var availableSpace = absDelta

        //将要填充的view的左上右下
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0

        if (dx > 0) {
            //当前屏幕显示的最后一个view
            val anchorView = getChildAt(childCount - 1)!!

            //获取当前在recyclerView中的位置
            val position = getPosition(anchorView)
            val anchorRight = getDecoratedRight(anchorView)
            left = anchorRight

            fillPosition = position + 1

            if (anchorRight < width) {
                return 0
            }

            //是最后一个了，并且超出了可滑动范围，修正滑动距离
            if (fillPosition >= itemCount && anchorRight - absDelta < width) {
                val fixScrolled = anchorRight - width
                return fixScrolled
            }

            //当前还不需要填充，直接return
            if (anchorRight - absDelta > width) {
                return dx
            }
        }

        if (dx < 0) {
            ////当前屏幕显示的第一个view
            val anchorView = getChildAt(0)!!

            //获取该view在recyclerview中的position
            val position = getPosition(anchorView)
            val anchorLeft = getDecoratedLeft(anchorView)
            right = anchorLeft

            fillPosition = position - 1

            //是第一个了，并且超出了可滑动范围，修正滑动距离
            if (fillPosition < 0 && anchorLeft + absDelta > 0) {
                val fixScrolled = anchorLeft
                return fixScrolled
            }

            //当前还不需要填充，直接return
            if (anchorLeft + absDelta < 0) {
                return dx
            }
        }


        while (availableSpace > 0 && fillPosition in 0 until itemCount) {
            val targetView = recycler.getViewForPosition(fillPosition)
            //填充尾部
            if (dx > 0) {
                addView(targetView)
            } else {
                addView(targetView, 0)
            }

            measureChild(targetView, 0, 0)

            if (dx > 0) {
                right = left + getDecoratedMeasuredWidth(targetView)
            } else {
                left = right - getDecoratedMeasuredWidth(targetView)
            }

            bottom = top + getDecoratedMeasuredHeight(targetView)

            layoutDecorated(targetView, left, top, right, bottom)

            if (dx > 0) {
                left += getDecoratedMeasuredWidth(targetView)
                fillPosition++
            } else {
                right -= getDecoratedMeasuredHeight(targetView)
                fillPosition--
            }

            if (fillPosition in 0..itemCount) {
                availableSpace -= getDecoratedMeasuredWidth(targetView)
            }
        }

        return dx
    }


    /**
     * 适配scrollToPosition
     */
    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(position)

        if (position < 0 || position >= itemCount) {
            return
        }
        mPendingPosition = position

        //能导致onLayoutChildren的调用
        requestLayout()
    }


    /**
     *适配smoothScrollToPosition，仿照LinearLayoutManager实现
     */
    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        super.smoothScrollToPosition(recyclerView, state, position)

        val linearSmoothScroller =
            LinearSmoothScroller(recyclerView.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        if (childCount == 0) {
            return null
        }
        val firstChildPos = getPosition(getChildAt(0)!!)
        val direction = if (targetPosition < firstChildPos) -1 else 1
        //PointF是个向量，PointF的x代表水平方向，y代表竖直方向。整数代表正向移动，负数代表反向移动
        return PointF(direction.toFloat(), 0f)
    }
}