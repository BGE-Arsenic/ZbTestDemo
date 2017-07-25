package com.example.apple.zbtestdemo.view.pop;

import android.animation.TypeEvaluator;

/**
 * Created by apple on 2017/6/29.
 */

public class ClickBackAnimation implements TypeEvaluator<Float>{

    private final float s = 1.70158f;
    float mDuration = 0f;

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        float a = mDuration*fraction;
        float b = startValue.floatValue();
        float c = endValue.floatValue() - startValue.floatValue();
        float d = mDuration;
        float result = caculate(a,b,c,d);
        return result;
    }

    public Float caculate(float a,float b,float c,float d){
        return c * ((a = a / d - 1) * a * ((s + 1) * a + s) + 1) + b;
    }

    public void setDuration(float duration) {
        mDuration = duration;
    }
}
