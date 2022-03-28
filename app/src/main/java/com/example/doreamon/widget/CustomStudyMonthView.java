package com.example.doreamon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.doreamon.R;
import com.example.doreamon.base.App;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;


/**
 * 演示一个变态需求的月视图
 * Created by huanghaibin on 2018/2/9.
 */

public class CustomStudyMonthView extends MonthView {

    private int mRadius;

    /**
     *自定义魅族标记的文本画笔
     */
    private Paint mTextPaint = new Paint();


    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();

    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();

    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();

    private float mSchemeBaseLine;

    public CustomStudyMonthView(Context context) {
        super(context);

        setMinimumHeight(dipToPx(App.getInstance(),50));

        mTextPaint.setTextSize(dipToPx(context, 8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);


        mSolarTermTextPaint.setColor(0xff489dff);
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setColor(Color.parseColor("#8052B4B0"));


        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0xFFeaeaea);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        mCircleRadius = dipToPx(getContext(), 7);

        mPadding = dipToPx(getContext(), 6);

        mPointRadius = dipToPx(context, 2);

        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);

        //兼容硬件加速无效的代码
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
//        //4.0以上硬件加速会导致无效
//        mSelectedPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));
//
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
//        mSchemeBasicPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));

    }

    @Override
    protected void onPreviewHook() {
        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
        int cy = (int) (y + mTextBaseLine / 2);
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

        mPointPaint.setColor(Color.parseColor("#4C4242"));
        Log.v("hashasScheme", "hasScheme");

        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;
//        if (calendar.isCurrentDay() && !isSelected) {
//            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
//        }
        mTextPaint.setColor(mSelectTextPaint.getColor());


        if (hasScheme) {
            if (isSelected) {
                canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius / 2, y + mPadding + mSchemeBaseLine-mCircleRadius / 2 , mCircleRadius, mSchemeBasicPaint);

                canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, y + mPadding + mSchemeBaseLine, mTextPaint);
            }else{

                canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, y + mPadding + mSchemeBaseLine, mTextPaint);
            }

            Log.v("hashasScheme", "hasScheme");
            mPointPaint.setColor(Color.parseColor("#4C4242"));
            canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);


        }

        //当然可以换成其它对应的画笔就不麻烦，
//        if (calendar.isWeekend() && calendar.isCurrentMonth()) {
//            mCurMonthTextPaint.setColor(0xFF489dff);
//            mCurMonthLunarTextPaint.setColor(0xFF489dff);
//            mSchemeTextPaint.setColor(0xFF489dff);
//            mSchemeLunarTextPaint.setColor(0xFF489dff);
//            mOtherMonthLunarTextPaint.setColor(0xFF489dff);
//            mOtherMonthTextPaint.setColor(0xFF489dff);
//        } else {
//            mCurMonthTextPaint.setColor(0xff333333);
//            mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
//            mSchemeTextPaint.setColor(0xff333333);
//            mSchemeLunarTextPaint.setColor(0xffCFCFCF);
//            mOtherMonthTextPaint.setColor(0xFFe1e1e1);
//            mOtherMonthLunarTextPaint.setColor(0xFFe1e1e1);
//        }

        if (isSelected) {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
        } else if (hasScheme) {


            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            mPointPaint.setColor(Color.parseColor("#4C4242"));
            canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);

//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint : mSchemeLunarTextPaint);
        } else {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
//                            calendar.isCurrentMonth() ? !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint  :
//                                    mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
