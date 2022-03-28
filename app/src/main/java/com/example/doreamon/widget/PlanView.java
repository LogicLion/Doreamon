package com.example.doreamon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.doreamon.R;
import com.example.doreamon.utils.DensityTools;

/**
 * @author wzh
 * @date 2022/3/10
 */
public class PlanView extends View {

    Paint paint = new Paint();

    private int progressColor;
    private int backgroundColor;

    private int total;
    private int progress;

    private float cornerRadius;

    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);


    /**
     * 0:横向
     * 1:竖向
     */
    private int orientation = 0;

    public PlanView(Context context) {
        super(context);
        init();
    }


    public PlanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }


    public PlanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }


    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PlanView);
        progressColor = attributes.getColor(R.styleable.PlanView_plan_progress_bar_color, Color.RED);
        backgroundColor = attributes.getColor(R.styleable.PlanView_plan_background_color, Color.GRAY);

        total = attributes.getInt(R.styleable.PlanView_plan_total, 100);
        progress = attributes.getInt(R.styleable.PlanView_plan_progress, 0);

        cornerRadius = attributes.getDimension(R.styleable.PlanView_plan_bar_corner_radius, 0);

        orientation = attributes.getInt(R.styleable.PlanView_plan_bar_orientation, 0);

        attributes.recycle();
    }


    private void init() {
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (orientation == 1) {
            paint.setColor(backgroundColor);
            canvas.drawRoundRect(0, 0, width, height,
                    cornerRadius, cornerRadius, paint);

            int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

            canvas.drawRoundRect(0, 0, width, height,
                    cornerRadius, cornerRadius, paint);
            paint.setXfermode(xfermode);
            paint.setColor(progressColor);
            canvas.drawRoundRect(0, height - height * progress / total, width, height + height - height * progress / total,
                    cornerRadius, cornerRadius, paint);
            paint.setXfermode(null);

            canvas.restoreToCount(saved);
        } else {

            paint.setColor(backgroundColor);
            canvas.drawRoundRect(0, 0, width, height,
                    cornerRadius, cornerRadius, paint);

            int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

            canvas.drawRoundRect(0, 0, width, height,
                    cornerRadius, cornerRadius, paint);
            paint.setXfermode(xfermode);
            paint.setColor(progressColor);
            canvas.drawRoundRect(width * progress / total - width, 0, width * progress / total, height,
                    cornerRadius, cornerRadius, paint);
            paint.setXfermode(null);

            canvas.restoreToCount(saved);
        }


    }
}
