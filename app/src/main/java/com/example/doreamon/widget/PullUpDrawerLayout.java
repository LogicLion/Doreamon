package com.example.doreamon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import com.example.doreamon.R;

/**
 * 可上拉抽屉效果
 * @author wzh
 * @date 2022/10/17
 */
public class PullUpDrawerLayout extends ViewGroup implements View.OnClickListener {

    private static String TAG = "PullUpDrawerLayout";
    private ViewDragHelper mBottomDragHelper;
    private View mContentView;//内容布局
    private View mPullView;//抽屉布局
    private int mCurTop;
    private boolean mIsOpenState = true;//当前状态 打开还是关闭

    private int openHeight = 0;
    private int closeHeight = 0;

    public PullUpDrawerLayout(Context context) {
        super(context);
    }

    public PullUpDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullUpDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeight);

        //测量背景图层的尺寸
        mContentView = getChildAt(0);
        MarginLayoutParams params = (MarginLayoutParams) mContentView.getLayoutParams();
        int childSpecWidth = MeasureSpec.makeMeasureSpec(measureWidth - (params.leftMargin + params.rightMargin), MeasureSpec.EXACTLY);
        int childSpecHeight = MeasureSpec.makeMeasureSpec(measureHeight - (params.topMargin + params.bottomMargin), MeasureSpec.EXACTLY);
        mContentView.measure(childSpecWidth, childSpecHeight);

        //测量上拉布局的尺寸
        mPullView = getChildAt(1);

        MarginLayoutParams pullViewParams = (MarginLayoutParams) mPullView.getLayoutParams();
        int pullViewSpecWidth = MeasureSpec.makeMeasureSpec(measureWidth - (pullViewParams.leftMargin + pullViewParams.rightMargin), MeasureSpec.EXACTLY);
        int pullViewSpecHeight = MeasureSpec.makeMeasureSpec(measureHeight - (pullViewParams.topMargin + pullViewParams.bottomMargin), MeasureSpec.AT_MOST);

        mPullView.measure(pullViewSpecWidth, pullViewSpecHeight);


        //设置监听事件
        mPullView.findViewById(R.id.btnClose).setOnClickListener(this);

    }

    /**
     * left top right bottom  当前ViewGroup相对于其父控件的坐标位置
     *
     * @param isChanged 该参数指出当前ViewGroup的尺寸或者位置是否发生了改变
     * @param left
     * @param top
     * @param right
     * @param bottom
     */

    @Override
    protected void onLayout(boolean isChanged, int left, int top, int right, int bottom) {

        if (isChanged) {
            Log.i(TAG, "onLayout();layout is changed.");
            //测量之后开始布局背景图层
            MarginLayoutParams params = (MarginLayoutParams) mContentView.getLayoutParams();
            mContentView.layout(params.leftMargin, params.topMargin, mContentView.getMeasuredWidth() + params.leftMargin, mContentView.getMeasuredHeight() + params.topMargin);


            //测量之后开始布局上拉的布局
            MarginLayoutParams paramsPullView = (MarginLayoutParams) mPullView.getLayoutParams();


            mPullView.layout(
                    paramsPullView.leftMargin,
                    mCurTop + paramsPullView.topMargin + mContentView.getMeasuredHeight() - mPullView.getMeasuredHeight(),
                    mPullView.getMeasuredWidth() + paramsPullView.leftMargin,
                    mCurTop + mPullView.getMeasuredHeight() + paramsPullView.topMargin + mContentView.getMeasuredHeight() - mPullView.getMeasuredHeight()
            );

            Log.d(TAG, "left:" + paramsPullView.leftMargin + "\n" +
                    "top:" + (mCurTop + paramsPullView.topMargin + mContentView.getMeasuredHeight() - mPullView.getMeasuredHeight())
                    + "\n"
                    + "right:" + (mPullView.getMeasuredWidth() + paramsPullView.leftMargin)
                    + "\n"
                    + "bottom:" + (mCurTop + mPullView.getMeasuredHeight() + paramsPullView.topMargin + mPullView.getMeasuredHeight()
            ));

            Log.d(TAG, "mCurTop:" + mCurTop +
                    "\n" +
                    "paramsPullView.topMargin:" + (paramsPullView.topMargin)
                    + "\n"
                    + "mContentView.getMeasuredHeight():" + (mContentView.getMeasuredHeight())
                    + "\n"
                    + "mPullView.getMeasuredHeight():" + (mPullView.getMeasuredHeight())
            );
        }
    }

    /**
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private void init() {
        //使用静态方法 构造 ViewDragHelper，这个时候传入一个ViewDragHelper.Callback
        mBottomDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack());
        mBottomDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                closeDrawer();
            default:
        }
    }

    //定义一个回调类
    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {
        //返回ture则表示可以捕获该view,手指摸上一瞬间执行
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mPullView;
        }

        /**
         * setEdgeTrackingEnabled设置的边界滑动时触发
         * captureChildView是为了让tryCaptureView返回false依旧生效
         *
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mBottomDragHelper.captureChildView(mPullView, pointerId);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            /**
             * 拖拽时child在垂直方向上移动的位置top，这里return的是真正移动的数值
             * 可在这限制view的拖拽范围
             * 计算child垂直方向的位置，top表示y轴坐标（相对于ViewGroup），默认返回0（如果不复写该方法）。这里，你可以控制垂直方向可移动的范围
             * 如果是向下pull:  y: +value
             * 这里是返回View要被拉到的位置
             */
            int marginTop = mContentView.getHeight() - mPullView.getHeight();
            Log.v(TAG, "top:" + top + "");
            return -Math.max(Math.min(-top, -marginTop), -mContentView.getHeight());
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            //手指释放的时候调用
            float movePrecent = (releasedChild.getHeight() - releasedChild.getTop()) / (float) releasedChild.getHeight();

            Log.d(TAG, "手指松开时yvel:" + yvel + "");
            //yvle为正数：向下
            //yvle为负数：向上
//            if (yvel >= 0 && movePrecent > 0.5f) {
//                mBottomDragHelper.settleCapturedViewAt(releasedChild.getLeft(), releasedChild.getHeight());
//            } else if (yvel < 0 && movePrecent < 0.5f) {
//                mBottomDragHelper.settleCapturedViewAt(releasedChild.getLeft(), 0);
//            }
            if (yvel < -1000) {
                //向上速滑
                mBottomDragHelper.settleCapturedViewAt(releasedChild.getLeft(), 0);
            } else if (yvel > 1000) {
                //向下速滑
                mBottomDragHelper.settleCapturedViewAt(releasedChild.getLeft(), releasedChild.getHeight());
            }else{
                //慢滑
                int finalTop = (movePrecent > 0.5f) ? 0 : releasedChild.getHeight();
                mBottomDragHelper.settleCapturedViewAt(releasedChild.getLeft(), finalTop);
            }
            Log.i(TAG, "precent: " + movePrecent);
            invalidate();
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged:" + top);
            mPullView.setVisibility(changedView.getHeight() + top == 0 ? GONE : VISIBLE);
            mCurTop = top;
            requestLayout();
        }

        // 这个用来控制垂直移动的边界范围，单位是像素
        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            if (mPullView == null) return 0;
            return mPullView == child ? mPullView.getHeight() : 0;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                mIsOpenState = mPullView.getTop() == 0;
            }
        }
    }

    @Override
    public void computeScroll() {
        //在调用settleCapturedViewAt()、flingCapturedView()和smoothSlideViewTo()时，该方法返回true，
        // 一般重写父view的computeScroll方法，进行该方法判断
        if (mBottomDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mBottomDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mBottomDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean ismIsOpenState() {
        return mIsOpenState;
    }

    public void pullDrawer() {
        if (!mIsOpenState) {
            mBottomDragHelper.smoothSlideViewTo(mPullView, mPullView.getLeft(), 0);
            invalidate();
        }
    }

    public void closeDrawer() {
        if (mIsOpenState) {
            mBottomDragHelper.smoothSlideViewTo(mPullView, mPullView.getLeft(), mPullView.getHeight());
            invalidate();
        }
    }
}
