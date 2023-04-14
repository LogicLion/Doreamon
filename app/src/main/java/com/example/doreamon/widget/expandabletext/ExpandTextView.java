package com.example.doreamon.widget.expandabletext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author wzh
 * @date 2023/3/22
 */
public class ExpandTextView extends AppCompatTextView {
    /**
     * true：展开，false：收起
     */
    boolean mExpanded;
    /**
     * 状态回调
     */
    Callback mCallback;
    /**
     * 源文字内容
     */
    String mText;
    /**
     * 最多展示的行数
     */
    final int maxLineCount = 3;
    /**
     * 省略文字
     */
    final String ellipsizeText = "...";

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 文字计算辅助工具
        StaticLayout sl = new StaticLayout(mText, getPaint(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
                , Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        // 总计行数
        int lineCount = sl.getLineCount();
        Log.i("ExpandTextView", "ExpandTextView:行数:" + lineCount);
        if (lineCount > maxLineCount) {
            if (mExpanded) {
                setText(mText);
                mCallback.onExpand();
            } else {
                lineCount = maxLineCount;

                // 省略文字的宽度
                float dotWidth = getPaint().measureText(ellipsizeText);

                // 找出第 showLineCount 行的文字
                int start = sl.getLineStart(lineCount - 1);
                int end = sl.getLineEnd(lineCount - 1);
                String lineText = mText.substring(start, end);

                // 将第 showLineCount 行最后的文字替换为 ellipsizeText
                int endIndex = 0;
                for (int i = lineText.length() - 1; i >= 0; i--) {
                    String str = lineText.substring(i, lineText.length());
                    // 找出文字宽度大于 ellipsizeText 的字符
                    if (getPaint().measureText(str) >= dotWidth) {
                        endIndex = i;
                        break;
                    }
                }

                // 新的第 showLineCount 的文字
                String newEndLineText = lineText.substring(0, endIndex) + ellipsizeText;
                // 最终显示的文字
                setText(mText.substring(0, start) + newEndLineText);

                mCallback.onCollapse();
            }
        } else {
            setText(mText);
            mCallback.onLoss();
        }

        // 重新计算高度
        int lineHeight = 0;

        for (int i = 0; i < lineCount; i++) {
            Rect lineBound = new Rect();
            sl.getLineBounds(i, lineBound);
            lineHeight += lineBound.height();
        }
        lineHeight += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(getMeasuredWidth(), lineHeight);


        Log.i("ExpandTextView", "layout:" + getLayout()+",text:"+getText()+",line:"+getLineCount());


    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
//        Log.i("ExpandTextView", "ExpandTextView:line:" + getLayout()+",text:"+getText());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.i("ExpandTextView", "ExpandTextView:line:" + getLayout()+",text:"+getText());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        Log.i("ExpandTextView", "layout:" + getLayout()+",text:"+getText()+",line:"+getLineCount());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    //    public void makeStaticLayout() {
//        StaticLayout.Builder builder = StaticLayout.Builder.obtain(mTransformed,
//                        0, mTransformed.length(), mTextPaint, wantWidth)
//                .setAlignment(alignment)
//                .setTextDirection(mTextDir)
//                .setLineSpacing(mSpacingAdd, mSpacingMult)
//                .setIncludePad(mIncludePad)
//                .setUseLineSpacingFromFallbacks(mUseFallbackLineSpacing)
//                .setBreakStrategy(mBreakStrategy)
//                .setHyphenationFrequency(mHyphenationFrequency)
//                .setJustificationMode(mJustificationMode)
//                .setMaxLines(mMaxMode == LINES ? mMaximum : Integer.MAX_VALUE);
//        if (shouldEllipsize) {
//            builder.setEllipsize(effectiveEllipsize)
//                    .setEllipsizedWidth(ellipsisWidth);
//        }
//        result = builder.build();
//    }

    /**
     * 设置要显示的文字以及状态
     *
     * @param text
     * @param expanded true：展开，false：收起
     * @param callback
     */
    public void setText(String text, boolean expanded, Callback callback) {
        mText = text;
        mExpanded = expanded;
        mCallback = callback;

        // 设置要显示的文字，这一行必须要，否则 onMeasure 宽度测量不正确
        setText(text);
    }

    /**
     * 展开收起状态变化
     *
     * @param expanded
     */
    public void setChanged(boolean expanded) {
        mExpanded = expanded;
        requestLayout();
    }

    public interface Callback {
        /**
         * 展开状态
         */
        void onExpand();

        /**
         * 收起状态
         */
        void onCollapse();

        /**
         * 行数小于最小行数，不满足展开或者收起条件
         */
        void onLoss();
    }
}
