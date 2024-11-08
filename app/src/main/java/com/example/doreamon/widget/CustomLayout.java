package com.example.doreamon.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author wzh
 * @date 2024/11/5
 */
public class CustomLayout extends FrameLayout {

    private View centerView;
    private float initialX, initialY;
    private int initialWidth, initialHeight;
    private float startCenterX, startCenterY;

    public CustomLayout(Context context) {
        super(context);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化中心的View
        centerView = new View(getContext());
        centerView.setBackgroundColor(Color.RED); // 设置颜色方便查看
        LayoutParams params = new LayoutParams(200, 200);
        centerView.setLayoutParams(params);
        addView(centerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录初始位置
                initialX = event.getRawX();
                initialY = event.getRawY();
                initialWidth = getWidth();
                initialHeight = getHeight();

                // 记录 centerView 的初始位置
                startCenterX = (initialWidth - centerView.getWidth()) / 2f;
                startCenterY = (initialHeight - centerView.getHeight()) / 2f;
                break;

            case MotionEvent.ACTION_MOVE:
                // 获取当前手指的位置
                float deltaX = event.getRawX() - initialX;
                float deltaY = event.getRawY() - initialY;

                // 计算新的宽度和高度
                int newWidth = (int) (initialWidth + deltaX);
                int newHeight = (int) (initialHeight - deltaY); // Y方向需要反转，向下时高度减小，向上时高度增大

                // 保证宽高不小于最小值
                newWidth = Math.max(newWidth, 400); // 最小宽度
                newHeight = Math.max(newHeight, 400); // 最小高度

                // 更新父布局的尺寸
                LayoutParams layoutParams = (LayoutParams) getLayoutParams();
                layoutParams.width = newWidth;
                layoutParams.height = newHeight;
                setLayoutParams(layoutParams);

                // 更新中心View的位置，确保它始终跟随手指
                float newCenterX = startCenterX + deltaX;
                float newCenterY = startCenterY + deltaY;

                // 更新 centerView 的位置
                centerView.layout(
                        (newWidth - centerView.getWidth()) / 2,
                        (newHeight - centerView.getHeight()) / 2,
                        (newWidth + centerView.getWidth()) / 2,
                        (newHeight + centerView.getHeight()) / 2
                );
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 每次布局时，确保 centerView 始终居中
        if (centerView != null) {
            int centerX = (r - l - centerView.getMeasuredWidth()) / 2;
            int centerY = (b - t - centerView.getMeasuredHeight()) / 2;
            centerView.layout(centerX, centerY, centerX + centerView.getMeasuredWidth(), centerY + centerView.getMeasuredHeight());
        }
    }
}


