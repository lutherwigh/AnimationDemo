package com.luxi96.animationdemo.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointFEvaluator implements TypeEvaluator<PointF> {

    PointF mPointF;

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        float x = startValue.x + (fraction * (endValue.x  - startValue.x));
        float y = startValue.y + (fraction * (endValue.y - startValue.y));

        mPointF.set(x,y);
        return mPointF;
    }
}
