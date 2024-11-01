package com.example.doreamon.ui.simple;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doreamon.R;

/**
 * @author wzh
 * @date 2024/2/19
 */
public abstract class AboveInputMethodDialog extends Dialog implements View.OnLayoutChangeListener {

    private int[] decorViewOutLocation = new int[2];
    private InputMethodManager inputMethodManager;

    // 最小偏移量
    private int dialogMinOffset;

    public AboveInputMethodDialog(Context context) {
        super(context, R.style.transparentBackgroundDiaolg);

        setContentView(getContextViewResource());

        updateWindow();

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        clearInputMethodStatusListener();
        if (!hasFocus || getWindow() == null) {
            // 失去焦点就隐藏输入法
            hideInputMethod(getEditText());
        } else {
            // 获得焦点就显示输入法
            View decorView = getWindow().getDecorView();
            decorView.postDelayed(() -> {
                listenInputMethodStatus();
                showInputMethod(getEditText());
            }, 100L);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        //触摸外部弹窗
        if (isOutOfBounds(getContext(), event)) {
            dismiss();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void dismiss() {
        // 因为已经对输入法状态做了监听，隐藏输入法时会自动隐藏对话框。
        // 如果直接隐藏对话框，输入法状态监听不到，下次显示对话框会有异常。
        hideInputMethod(getEditText());
    }

    // 监听输入法状态
    private void listenInputMethodStatus() {
        if (getWindow() != null) {
            View decorView = getWindow().getDecorView();
            decorView.getLocationOnScreen(decorViewOutLocation);
            dialogMinOffset = decorViewOutLocation[1] / 3;
            decorView.addOnLayoutChangeListener(this);
        }
    }

    // 清理输入法状态监听
    private void clearInputMethodStatusListener() {
        if (getWindow() != null) {
            getWindow().getDecorView().removeOnLayoutChangeListener(this);
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        /*
         * 根据编辑框的位置变化确定输入法是否隐藏。
         * 如果编辑框的位置相对于上次的位置向上偏移，说明输入法弹出。否则说明输入法收起。
         */
        int oldY = decorViewOutLocation[1];
        v.getLocationOnScreen(decorViewOutLocation);
        // decorView 向下偏移，且偏移量足够大才认为是输入法隐藏，此时关闭当前对话框
        if (oldY < decorViewOutLocation[1] && decorViewOutLocation[1] - oldY > dialogMinOffset) {
            super.dismiss();
        }
    }

    // 更新弹窗样式
    private void updateWindow() {
        Window window = getWindow();
        if (window != null) {
            //获取对话框当前的参数值
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);

            window.setWindowAnimations(R.style.anim_dialog_slide_from_bottom);
        }
    }

    // 点击位置是否在对话框外部区域
    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        Window window = getWindow();
        if (window == null) return true;
        final View decorView = window.getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    // 显示输入法
    private void showInputMethod(EditText editText) {
        inputMethodManager.showSoftInput(editText, -1);
    }

    // 隐藏输入法
    private void hideInputMethod(EditText editText) {
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 获取内容视图的资源id
     */
    @LayoutRes
    protected abstract int getContextViewResource();

    /**
     * 返回当前 Dialog 中的 EditText。
     */
    @Nullable
    protected abstract EditText getEditText();

}