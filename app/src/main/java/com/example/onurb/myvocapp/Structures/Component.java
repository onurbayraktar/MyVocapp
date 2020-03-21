package com.example.onurb.myvocapp.Structures;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Onurb on 21.3.2020.
 */

public class Component {

    private TextView tvWordItself, tvWordTranslation;
    private ImageView deleteButton, updateButton;

    public Component() {}

    public Component(TextView tvWordItself, TextView tvWordTranslation, ImageView deleteButton, ImageView updateButton)
    {
        this.tvWordItself = tvWordItself;
        this.tvWordTranslation = tvWordTranslation;
        this.deleteButton = deleteButton;
        this.updateButton = updateButton;
    }
    public TextView getTvWordItself(){ return tvWordItself; }
    public TextView getTvWordTranslation(){ return tvWordTranslation; }
    public ImageView getDeleteButton()
    {
        return deleteButton;
    }
    public ImageView getUpdateButton(){ return updateButton; }
}
