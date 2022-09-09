package com.example.doreamon.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.doreamon.treasure.utils.DensityTools;
import com.example.doreamon.R;
import com.example.doreamon.entity.VideoLearnData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzh
 * @date 2022/3/21
 */
public class StudyChartView extends View {

    Paint paint = new Paint();
    Paint markPaint = new Paint();
    Paint linePaint = new Paint();

    Bitmap bitmap;

    List<VideoLearnData> list;

    int bgHeight;
    int chartMarginBottom;
    int chartMarginTop;
    int textWidth;
    int textHeight;
    int textSize;
    int chartBarWidth;
    int halfChartBarWidth;

    //定位线宽度
    int positionLineWidth;


    int markHeight;
    int markTextPadding;
    int markTextSpace;

    int markPosition = -1;

    //x坐标
    List<Integer> xList = new ArrayList<>();
    //y坐标
    List<Integer> yList = new ArrayList<>();

    public StudyChartView(Context context) {
        super(context);

        init();
    }


    public StudyChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public StudyChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public StudyChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

        bgHeight = (int) DensityTools.dip2px(120);
        chartMarginBottom = (int) DensityTools.dip2px(50);
        chartMarginTop = (int) DensityTools.dip2px(30);

        textWidth = (int) DensityTools.dip2px(16);
        textHeight = (int) DensityTools.dip2px(20);
        textSize = (int) DensityTools.sp2px(12);
        chartBarWidth = (int) DensityTools.dip2px(12);
        halfChartBarWidth = (int) DensityTools.dip2px(6);

        int markTextSize = (int) DensityTools.sp2px(10);
        markHeight = (int) DensityTools.dip2px(18);
        markTextPadding = (int) DensityTools.dip2px(5);
        markTextSpace = (int) DensityTools.dip2px(50);
        positionLineWidth = (int) DensityTools.dip2px(1);


        paint.setAntiAlias(true);

        markPaint.setAntiAlias(true);
        markPaint.setTextSize(markTextSize);
        markPaint.setTypeface(Typeface.SANS_SERIF);

        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#A7E3FF"));

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_chart);
    }

    public void setChartList(List<VideoLearnData> list) {
        this.list = list;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();


        Rect rect = new Rect(0, height - bgHeight - chartMarginBottom, width, height - chartMarginBottom);

        canvas.drawBitmap(bitmap, null, rect, null);


        int size = list.size();
        int xSpace = width / (size + 1);

        int maxChartHeight = height - chartMarginBottom - chartMarginTop;
        int maxData = 0;
        for (int i = 0; i < size; i++) {
            VideoLearnData chartData = list.get(i);
            int data = chartData.getData();
            if (data > maxData) {
                maxData = data;
            }
        }

        for (int i = 0; i < size; i++) {
            int i1 = (xSpace + xSpace * i);
            xList.add(i1);

            VideoLearnData chartData = list.get(i);
            int data = chartData.getData();
            yList.add(maxChartHeight * data / maxData);
        }


        paint.setTextSize(textSize);

        for (int i = 0; i < size; i++) {
            paint.setColor(Color.parseColor("#333333"));
            canvas.drawText(list.get(i).getTime(), xList.get(i) - textWidth, height - textHeight, paint);

            Path path = new Path();

            Integer yHeight = yList.get(i);
            if(yHeight>0){
                path.moveTo(xList.get(i) - chartBarWidth / 2, height - chartMarginBottom);
                path.lineTo(xList.get(i) - chartBarWidth / 2, height - chartMarginBottom - yHeight);
                path.arcTo(xList.get(i) - chartBarWidth / 2, height - chartMarginBottom - yHeight - chartBarWidth / 2, xList.get(i) + chartBarWidth / 2, height - chartMarginBottom - yHeight + chartBarWidth / 2, -180f, 180f, false);
                path.lineTo(xList.get(i) + chartBarWidth / 2, height - chartMarginBottom);
                path.close();

                paint.setColor(Color.parseColor("#FD6418"));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(path, paint);
            }

        }


        float v = markPaint.measureText("08：00-08:05");
        float w = markPaint.measureText("观看视频5分钟");

        float markWidth = w + v + markTextSpace + markTextPadding * 2;

        if (markPosition != -1) {
            float markLeftX;
            Integer xPosition = xList.get(markPosition);
            if (xPosition < markWidth / 2) {
                //从最左开始画

                markLeftX = 0;
            } else if (width - xPosition < markWidth / 2) {
                //从最右开始画
                markLeftX = width - markWidth;
            } else {
                //从中心点开始
                markLeftX = xPosition - markWidth / 2;
            }

            markPaint.setColor(Color.parseColor("#FFBF17"));
            canvas.drawRoundRect(markLeftX, 0, markWidth + markLeftX, markHeight, markHeight / 2, markHeight / 2, markPaint);
            markPaint.setColor(Color.parseColor("#4C4242"));
            canvas.drawText("08：00-08:05", markLeftX + markTextPadding, markHeight - markTextPadding, markPaint);
            canvas.drawText("观看视频5分钟", markLeftX + markTextPadding + v + markTextSpace, markHeight - markTextPadding, markPaint);

            Integer yHeight = yList.get(markPosition);
//            canvas.drawLine(xPosition,markHeight,xPosition,height - chartMarginBottom - yHeight - chartBarWidth / 2,linePaint);

            canvas.drawRect(xPosition-positionLineWidth/2,markHeight,xPosition+positionLineWidth/2,height - chartMarginBottom - yHeight - chartBarWidth / 2,linePaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.v("X:", x + "");
        Log.v("Y:", y + "");


        for (int i = 0; i < xList.size(); i++) {
            Integer integer = xList.get(i);
            if (x > integer - halfChartBarWidth && x < integer + halfChartBarWidth) {
                if (markPosition != i) {
                    markPosition = i;
                    invalidate();
                }

                break;
            }
        }
        return true;
    }
}
