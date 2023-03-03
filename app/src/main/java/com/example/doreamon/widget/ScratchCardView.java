package com.example.doreamon.widget;


/**
 * 刮刮卡(完善版)
 * Create by: chenwei.li
 * Date: 2017/7/22
 * Time: 下午7:25
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.doreamon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 刮刮卡(完善版)
 * Create by: chenwei.li
 * Date: 2017/7/22
 * Time: 下午7:25
 */

public class ScratchCardView extends View {

    private Rect mRect;

    //处理图层
    private Paint mForePaint;
    private Path mPath;

    private Bitmap mBitmap;//加载资源文件
    private Canvas mForeCanvas;//前景图Canvas
    private Bitmap mForeBitmap;//前景图Bitmap

    private List<Path> pathList;

    private int step = -1;

    //记录位置
    private int mLastX;
    private int mLastY;

    private volatile boolean isClear;//标志是否被清除

    private String TAG = "ScratchCardView";


    public ScratchCardView(Context context) {
        this(context, null);
    }

    public ScratchCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        mRect = new Rect();
        mPath = new Path();
        pathList = new ArrayList<>();

        //擦除画笔
        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setAlpha(0);
        mForePaint.setStrokeCap(Paint.Cap.ROUND);
        mForePaint.setStrokeJoin(Paint.Join.ROUND);
        mForePaint.setStyle(Paint.Style.STROKE);
        mForePaint.setStrokeWidth(30);
        mForePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //通过资源文件创建Bitmap对象
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qishituan);
        mForeBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //双缓冲,装载画布
        mForeCanvas = new Canvas(mForeBitmap);
        mForeCanvas.drawBitmap(mBitmap, 0, 0, null);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (!isClear) {
            canvas.drawBitmap(mForeBitmap, 0, 0, null);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.moveTo(mLastX, mLastY);
                mPath = new Path(mPath);
                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.lineTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_UP:
                pathList.add(mPath);
                step++;
                Log.v(TAG, "" + step);
                break;
            default:
                break;
        }

        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();
        return true;
    }


    /**
     * 撤销
     */
    public void rollback() {

        step--;
        if (step < 0) {
            Toast.makeText(getContext(), "没有了", Toast.LENGTH_SHORT).show();
            return;
        }
        mPath = pathList.get(step);

        mBitmap.recycle();
        mForeBitmap.recycle();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qishituan);
        mForeBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mForeCanvas = new Canvas(mForeBitmap);
        mForeCanvas.drawBitmap(mBitmap, 0, 0, null);
        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();
    }

    /**
     * 前进
     */
    public void forward() {

    }
}
