package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

/**
 *
 * 已验证可行，不过NestedScrollableHost的实现更优雅一些
 * 处理viewpager2作为其他布局的子布局时的滑动冲突，因viewpager2是final的，需包裹一层布局作为中间层处理拦截事件，也就是外层--》中间层--》viewpager2
 *
 *  参考博客：https://mp.weixin.qq.com/s/T3HYKpNHR1zhA1ZOR8BSYw
 *
 *  参考的官方实现：https://github.com/androidx/androidx/blob/androidx-main/viewpager2/integration-tests/testapp/src/main/java/
 *  androidx/viewpager2/integration/testapp/NestedScrollableHost.kt
 *
 */
class ViewPager2Container @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mViewPager2: ViewPager2? = null
    private var disallowParentInterceptDownEvent = true
    private var startX = 0
    private var startY = 0


    private var touchSlop = 0

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //判断直接子类中是否包含ViewPager2
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                mViewPager2 = childView
                break
            }
        }
        if (mViewPager2 == null) {
            throw IllegalStateException("The root child of ViewPager2Container must contains a ViewPager2")
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val doNotNeedIntercept = (!mViewPager2!!.isUserInputEnabled
                || (mViewPager2?.adapter != null
                && mViewPager2?.adapter!!.itemCount <= 1))
        if (doNotNeedIntercept) {

            //无需拦截
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()

                //为true表示请求父view不要拦截
                parent.requestDisallowInterceptTouchEvent(disallowParentInterceptDownEvent)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)

                Log.v("滑动事件","disX:${disX},disY:${disY},touchSlop${touchSlop}")
                if (disX > touchSlop || disY > touchSlop) {
                    if (mViewPager2!!.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                        onVerticalActionMove(endY, disX, disY)
                    } else if (mViewPager2!!.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        onHorizontalActionMove(endX, disX, disY)
                    }
                }

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                Log.v("拦截情况", "ACTION_UP")
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun onHorizontalActionMove(endX: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }
        if (disX > disY) {
            val currentItem = mViewPager2?.currentItem
            val itemCount = mViewPager2?.adapter!!.itemCount

            Log.v("拦截情况", "disX:${disX},disY:${disY}")
            if (currentItem == 0 && endX - startX > 0) {
                Log.v("拦截情况", "父view拦截")
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                val b = (currentItem != itemCount - 1
                        || endX - startX >= 0)
                if (b) {
                    Log.v("拦截情况", "自己处理")
                } else {
                    Log.v("拦截情况", "父view拦截")
                }

                parent.requestDisallowInterceptTouchEvent(b)
            }
        } else if (disY > disX) {
            Log.v("拦截情况", "父view拦截")
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun onVerticalActionMove(endY: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }

        Log.v("拦截情况", "disX:${disX},disY:${disY}")
        val currentItem = mViewPager2?.currentItem
        val itemCount = mViewPager2?.adapter!!.itemCount
        if (disY > disX) {
            if (currentItem == 0 && endY - startY > 0) {
                Log.v("拦截情况", "父view拦截")
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                val b = (currentItem != itemCount - 1
                        || endY - startY >= 0)
                if (b) {
                    Log.v("拦截情况", "自己处理")
                } else {
                    Log.v("拦截情况", "父view拦截")
                }
                parent.requestDisallowInterceptTouchEvent(b)
            }
        } else if (disX > disY) {
            Log.v("拦截情况", "父view拦截")
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    /**
     * 设置是否允许在当前View的{@link MotionEvent#ACTION_DOWN}事件中禁止父View对事件的拦截，该方法
     * 用于解决CoordinatorLayout+CollapsingToolbarLayout在嵌套ViewPager2Container时引起的滑动冲突问题。
     *
     * 设置是否允许在ViewPager2Container的{@link MotionEvent#ACTION_DOWN}事件中禁止父View对事件的拦截，该方法
     * 用于解决CoordinatorLayout+CollapsingToolbarLayout在嵌套ViewPager2Container时引起的滑动冲突问题。
     *
     * @param disallowParentInterceptDownEvent 是否允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}事件中禁止父View拦截事件，默认值为false
     *   true 不允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     *   设置disallowIntercept为true可以解决CoordinatorLayout+CollapsingToolbarLayout的滑动冲突
     *   false 允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     */
    fun disallowParentInterceptDownEvent(disallowParentInterceptDownEvent: Boolean) {
        this.disallowParentInterceptDownEvent = disallowParentInterceptDownEvent
    }
}