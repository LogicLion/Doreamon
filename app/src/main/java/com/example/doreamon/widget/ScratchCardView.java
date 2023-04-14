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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.doreamon.R;

import java.util.Stack;

/**
 * 写一个橡皮檫功能，可回退、前进
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


    /**
     * “回退”栈
     */
    private Stack<Path> pathRollbackStack;

    /**
     * “前进”栈
     */
    private Stack<Path> pathForwardStack;

    private boolean isRollBackEnable = false;
    private boolean isForwardEnable = false;
    private boolean isWriting = false;

    //记录位置
    private int mLastX;
    private int mLastY;

    private int viewWidth;
    private int viewHeight;


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

        pathRollbackStack = new Stack<>();
        pathForwardStack = new Stack<>();
        mRect = new Rect();
        mPath = new Path();

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
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = w;
        viewHeight = h;

        resetOriginCanvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mForeBitmap, 0, 0, null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isWriting = true;
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.moveTo(mLastX, mLastY);

                if (!pathForwardStack.empty()) {
                    pathForwardStack.clear();
                }
                mPath = new Path(mPath);

                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.lineTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_UP:
                pathRollbackStack.push(mPath);
                checkStackEnable();
                isWriting = false;
                break;
            default:
                break;
        }

        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();
        return true;
    }


    /**
     * 回退
     */
    public void rollback() {
        if (isWriting) return;
        if (pathRollbackStack.empty()) {
            Toast.makeText(getContext(), "没有了", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pathRollbackStack.size() > 0) {
            pathForwardStack.push(pathRollbackStack.pop());
        }

        if (pathRollbackStack.size() > 0) {
            mPath = pathRollbackStack.peek();
        } else {
            mPath = new Path();
            if (stackStateChangeListener != null && isRollBackEnable) {
                isRollBackEnable = false;
                stackStateChangeListener.onRollBackEnable(false);
            }
        }

        resetOriginCanvas();
        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();

        checkStackEnable();
    }

    /**
     * 前进
     */
    public void forward() {
        if (isWriting) return;
        if (pathForwardStack.empty()) {
            Toast.makeText(getContext(), "没有了", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pathForwardStack.size() > 0) {
            Path path = pathForwardStack.pop();
            pathRollbackStack.push(path);
            mPath = path;
        }


        resetOriginCanvas();
        mForeCanvas.drawPath(mPath, mForePaint);
        invalidate();

        checkStackEnable();
    }

    /**
     * 重置成最初的画板
     */
    private void resetOriginCanvas() {
        if (mForeBitmap != null) {
            mForeBitmap.recycle();
        }
        //创建一个空bitmap对象,此bitmap对象为mForeCanvas上的操作对象
        mForeBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //双缓冲,装载画布
        mForeCanvas = new Canvas(mForeBitmap);

        mForeCanvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight())
                , new Rect(0, 0, viewWidth, viewHeight), null);
    }

    private StackStateListener stackStateChangeListener;

    public void setStackStateChangeListener(StackStateListener listener) {
        stackStateChangeListener = listener;
    }

    public interface StackStateListener {
        void onRollBackEnable(Boolean enable);

        void onForwardEnable(Boolean enable);
    }


    /**
     * 校验当前栈状态
     */
    private void checkStackEnable() {
        if (pathRollbackStack.empty() == isRollBackEnable) {
            isRollBackEnable = !isRollBackEnable;
            if (stackStateChangeListener != null) {
                stackStateChangeListener.onRollBackEnable(isRollBackEnable);
            }
        }

        if (pathForwardStack.empty() == isForwardEnable) {
            isForwardEnable = !isForwardEnable;
            if (stackStateChangeListener != null) {
                stackStateChangeListener.onForwardEnable(isForwardEnable);
            }
        }
    }
}
