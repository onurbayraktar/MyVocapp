package com.example.onurb.myvocapp;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Onurb on 20.3.2020.
 */

public class Animation {

    public Animation()
    {

    }

    public void bounceAnimation(View view)
    {
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .repeat(0)
                .playOn(view);
    }
}
