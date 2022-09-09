package com.example.doreamon.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.doreamon.R;

/**
 * @author wzh
 * @date 2022/4/14
 */
public class ScratchView extends View {

    private Bitmap mBitmap;//加载资源文件
    private Canvas mForeCanvas;//前景图Canvas
    private Bitmap mForeBitmap;//前景图Bitmap
    private Bitmap mLogoBitmap;//前景图Bitmap


    private Paint mTextPaint;

    //处理图层
    private Paint mForePaint;

    //记录位置
    private int mLastX;
    private int mLastY;
    private Path mPath;

    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);


    //处理文字
    private String mText = "恭喜您中奖啦!!";

    public ScratchView(Context context) {
        super(context);
        init();
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ScratchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPath = new Path();

        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);
//        mTextPaint.getTextBounds(mText, 0, mText.length(), mRect);

        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.GREEN);
        mForePaint.setAlpha(0);
        mForePaint.setStrokeCap(Paint.Cap.ROUND);
        mForePaint.setStrokeJoin(Paint.Join.ROUND);
        mForePaint.setStyle(Paint.Style.STROKE);
        mForePaint.setStrokeWidth(30);


        //通过资源文件创建Bitmap对象
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qishituan);
        mLogoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
        mForeBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //双缓冲,装载画布
        mForeCanvas = new Canvas(mForeBitmap);
        mForeCanvas.drawBitmap(mBitmap, 0, 0, null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBitmap, 0, 0, mForePaint);
        mForePaint.setXfermode(xfermode);

        canvas.drawPath(mPath,mForePaint);
        mForePaint.setXfermode(null);

        canvas.restoreToCount(saved);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.lineTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_UP:
//                new Thread(mRunnable).start();
                break;
            default:
                break;
        }
//        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();
        return true;
    }
}
