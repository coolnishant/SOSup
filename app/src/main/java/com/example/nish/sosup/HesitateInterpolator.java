package com.example.nish.sosup;

import android.view.animation.Interpolator;

/**
 * Created by Nish on 09-10-2016.
 */
public class HesitateInterpolator implements Interpolator {

    public HesitateInterpolator(){}
    @Override
    public float getInterpolation(float t) {
        float x = 2.0f*t-1.0f;

        return 0.5f*(x*x*x + 1.0f);
    }
}
