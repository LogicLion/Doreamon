package com.example.doreamon.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doreamon.R;


public class ViewLoadingFix extends Dialog {

    private static ViewLoadingFix loadDialog;
    private boolean canNotCancel;
    private final Animation animation;

     public ViewLoadingFix(Context context , String content , boolean canNotCancel) {
        super(context, R.style.LoadingDialog);
        this.canNotCancel = canNotCancel;
        //https://blog.csdn.net/ldxlz224/article/details/103508730
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //加载布局
        if(content!=null && content.length()>0){
            setContentView(R.layout.layout_dialog_loading);
            TextView message = findViewById(R.id.message);
            message.setText(content);
        }
        ImageView progressImageView = findViewById(R.id.iv_image);
        //创建旋转动画
        animation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        //设置不能取消
        this.setCancelable(false);
        //动画的重复次数
        animation.setRepeatCount(-1);
        //设置为true，动画转化结束后被应用
        animation.setFillAfter(true);
        //开始动画
        progressImageView.startAnimation(animation);
        // 设置Dialog参数
        Window window = getWindow();
        if(window!=null){
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount =0f;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window!=null){
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
    }


    @Override
    protected void onStop() {
        super.onStart();
        Window window = getWindow();
        if (window!=null){
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //注意需要销毁动画
        if (animation!=null){
            animation.cancel();
        }
        //onDetachedFromWindow  比 dismiss 方法中的 finally 先执行
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!canNotCancel) {
                dismiss();
                return true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void show(Context context) {
        show(context,"");
    }

    public static void show(Context context, String message) {
        show(context, message,false);
    }

    /**
     * 展示加载窗
     * @param context               上下文
     * @param message               内容
     * @param isCancel              是否可以取消
     */
    public static void show(Context context, String message, boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new ViewLoadingFix(context, message ,isCancel);
        loadDialog.show();
    }


    /**
     * 销毁加载窗
     * 注意在try …… catch中，关于return和finally的执行顺序，看这篇博客：
     * https://github.com/yangchong211/YCBlogs
     * @param context               上下文
     */
    public static void dismiss(Context context) {
        try {
//            if (context instanceof Activity) {
//                if (((Activity) context).isFinishing()) {
//                    loadDialog = null;
//                    Log.v("loading弹窗",context.getClass().getSimpleName()+" isFinishing");
//                    return;
//                }
//            }
            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
//                if (loadContext instanceof Activity){
//                    if (((Activity) loadContext).isFinishing()) {
//                        loadDialog = null;
//                        Log.v("loading弹窗",context.getClass().getSimpleName()+" isFinishing");
//                        return;
//                    }
//                }
                //loadDialog.cancel();
                //建议用dismiss方法销毁弹窗，如果没有调用setOnCancelListener方法，两个方法作用是一样的
                //具体可以看：https://github.com/yangchong211/YCBlogs
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Log.e("ViewLoading","finally");
            loadDialog = null;
        }
    }

}
