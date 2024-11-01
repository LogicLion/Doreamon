package com.example.doreamon.widget.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;

/**
 * @author wzh
 * @date 2024/7/24
 */
public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent3 {

    private ImageView img;
    private TextView tv;
    private MyNestedScrollChild nsc;
    private NestedScrollingParentHelper mParentHelper;
    private int imgHeight;
    private int tvHeight;

    public MyNestedScrollParent(Context context) {
        super(context);
        init();
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        img = (ImageView) getChildAt(0);
        tv = (TextView) getChildAt(1);
        nsc = (MyNestedScrollChild) getChildAt(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        View child = getChildAt(0);
        imgHeight = child.getMeasuredHeight();
    }


    //在此可以判断参数target是哪一个子view以及滚动的方向，然后决定是否要配合其进行嵌套滚动
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof MyNestedScrollChild) {
            return true;
        }
        return false;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (showImg(dy) || hideImg(dy)) {//如果需要显示或隐藏图片，即需要自己(parent)滚动
            scrollBy(0, -dy);//滚动
            consumed[1] = dy;//告诉child我消费了多少
        }
    }

    //后于child滚动
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {

        if (velocityY > 0 && getScrollY() < imgHeight) {
            return true;
        } else if (velocityY < 0 && getScrollY() > imgHeight) {
            return true;
        } else {
            return false;
        }
    }

    //返回值：是否消费了fling
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (velocityY > 0 && getScrollY() < imgHeight) {
            return true;
        } else if (velocityY < 0 && getScrollY() > imgHeight) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }


    //--------------------------------------------------

    //下拉的时候是否要向下滚动以显示图片
    public boolean showImg(int dy) {
        if (dy > 0) {
            Log.v("showImg", "getScrollY:" + getScrollY() + ",nsc.getScrollY:" + nsc.getScrollY());
            if (getScrollY() > 0 && nsc.getScrollY() == 0) {
                return true;
            }
        }

        return false;
    }

    //上拉的时候，是否要向上滚动，隐藏图片
    public boolean hideImg(int dy) {
        if (dy < 0) {
            Log.v("showImg", "getScrollY:" + getScrollY() + ",nsc.getScrollY:" + nsc.getScrollY());
            if (getScrollY() < imgHeight) {
                return true;
            }
        }
        return false;
    }

    //scrollBy内部会调用scrollTo
    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > imgHeight) {
            y = imgHeight;
        }

        super.scrollTo(x, y);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        if (target instanceof MyNestedScrollChild) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        if (showImg(dy) || hideImg(dy)) {//如果需要显示或隐藏图片，即需要自己(parent)滚动
            scrollBy(0, -dy);//滚动
            consumed[1] = dy;//告诉child我消费了多少
        }
    }
}

