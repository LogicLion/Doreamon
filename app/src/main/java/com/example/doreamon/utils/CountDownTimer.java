package com.example.doreamon.utils;

import android.os.Handler;
import android.os.SystemClock;

/**
 * 倒计时实现，自己处理校准和跳秒
 *
 * @author wzh
 * @date 2023/2/6
 */
public class CountDownTimer {

    private int mTimes;
    private int allTimes;
    private final long mCountDownInterval;
    private final Handler mHandler;
    private OnTimerCallBack mCallBack;
    private boolean isStart;
    private long startTime;

    public CountDownTimer(int times, long countDownInterval) {
        this.mTimes = times;
        this.mCountDownInterval = countDownInterval;
        mHandler = new Handler();
    }

    public synchronized void start(OnTimerCallBack callBack) {
        this.mCallBack = callBack;
        if (isStart || mCountDownInterval <= 0) {
            return;
        }

        isStart = true;
        if (callBack != null) {
            callBack.onStart();
        }

        //System.currentTimeMillis()  系统时间，也就是日期时间，可以被系统设置修改，然后值就会发生跳变。
        //uptimeMillis 自开机后，经过的时间，不包括深度睡眠的时间
        //elapsedRealtime自开机后，经过的时间，包括深度睡眠的时间
        startTime = SystemClock.elapsedRealtime();

        if (mTimes <= 0) {
            finishCountDown();
            return;
        }
        allTimes = mTimes;

        mHandler.postDelayed(runnable, mCountDownInterval);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTimes--;
            if (mTimes > 0) {
                if (mCallBack != null) {
                    mCallBack.onTick(mTimes);
                }

                long nowTime = SystemClock.elapsedRealtime();
                //计算延时
                long delay = (nowTime - startTime) - (allTimes - mTimes) * mCountDownInterval;
                // 处理跳秒
                while (delay > mCountDownInterval) {
                    mTimes--;
                    if (mCallBack != null) {
                        mCallBack.onTick(mTimes);
                    }

                    delay -= mCountDownInterval;
                    if (mTimes <= 0) {
                        finishCountDown();
                        return;
                    }
                }

                mHandler.postDelayed(this, 1000 - delay);
            } else {
                finishCountDown();
            }
        }
    };

    private void finishCountDown() {
        if (mCallBack != null) {
            mCallBack.onFinish();
        }
        isStart = false;
    }

    public void cancel() {
        mHandler.removeCallbacksAndMessages(null);
        isStart = false;
    }

    public interface OnTimerCallBack {

        void onStart();

        void onTick(int times);

        void onFinish();

    }

}

