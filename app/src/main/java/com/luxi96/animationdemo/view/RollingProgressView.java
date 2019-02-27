package com.luxi96.animationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RollingProgressView extends View {

    Paint paint = new Paint();
    Paint textPaint = new Paint();

    float progress;

    public RollingProgressView(Context context) {
        super(context);
    }

    public RollingProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RollingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RollingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = centerX - 15;
        initPaint();

        RectF rectF = new RectF( centerX - radius, centerY - radius,
                centerX + radius, centerY + radius);

        canvas.drawArc(rectF,135,progress * 2.7f,false,paint);
        canvas.drawText((int) progress + "%",centerX,centerY,textPaint);
    }

    void initPaint(){

        // 图像
        // 抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(0x801B88EE);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        // 文字
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
