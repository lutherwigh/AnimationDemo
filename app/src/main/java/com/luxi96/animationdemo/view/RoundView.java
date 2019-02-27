package com.luxi96.animationdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class RoundView extends View {

    Paint paint = new Paint();
    Paint textPaint = new Paint();

    PointF position = new PointF();

    float centerX,centerY;

    int color = 0xffff0000;

    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        initPaint();

        canvas.drawCircle(centerX + position.x,centerY + position.y,200,paint);
//        canvas.drawText("颜色变化",250,50,textPaint);
    }

    void initPaint(){

        // 图像
        // 抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(getColor());

        // 文字
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public void discoloration(){
        ObjectAnimator animator = ObjectAnimator.ofArgb(this,"color",0xffff0000, 0xff00ff00);
        animator.setDuration(2000);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setRepeatCount(100);
        animator.start();
    }

}
