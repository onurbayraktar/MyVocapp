package com.example.onurb.myvocapp.Structures;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.onurb.myvocapp.AsyncTasks.SaveWordAsyncTask;
import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.R;

/**
 * Created by Onurb on 21.3.2020.
 */

public class WordPopup extends Dialog{

    private Activity callerActivity;
    private DBHelper dbHelper;
    private EditText editTextWordItself;
    private EditText editTextWordTranslation;
    private ImageView confirmButton;
    private Long idOfList;
    private String statusDefinition, wordToUpdate, translationToUpdate;

    public WordPopup(Activity activity, Long idOfList, String statusDefinition) {
        super(activity);
        this.callerActivity = activity;
        this.idOfList = idOfList;
        this.statusDefinition = statusDefinition;

    }

    public WordPopup(Activity activity, String statusDefinition, String wordToUpdate, String translationToUpdate)
    {
        super(activity);
        this.callerActivity = activity;
        this.statusDefinition = statusDefinition;
        this.wordToUpdate = wordToUpdate;
        this.translationToUpdate = translationToUpdate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_layout);
        assignWidthAndHeight();
        editTextWordItself = (EditText)findViewById(R.id.nw_word_text);
        editTextWordTranslation = (EditText)findViewById(R.id.nw_translation_text);
        setWordAndTranslationForUpdateScreen();
        confirmButton = (ImageView)findViewById(R.id.confirm_button_new_word);
    }

    public void assignWidthAndHeight()
    {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setWordAndTranslationForUpdateScreen()
    {
        if(statusDefinition.equals("UPDATE"))
        {
            editTextWordItself.setText(wordToUpdate);
            editTextWordTranslation.setText(translationToUpdate);
        }
    }

    public boolean checkWordAndTranslationEnteredOrNot()
    {
        String wordItself = editTextWordItself.getText().toString();
        String wordTranslation = editTextWordTranslation.getText().toString();

        if( (wordItself.equals("")) && (wordTranslation.equals("")))
        {
            setHintsOfEditTexts(-1);
        } // End if //

        else if( (wordItself.equals("")) )
        {
            setHintsOfEditTexts(0);
        } // End else if //

        else if( (wordTranslation.equals("")) )
        {
            setHintsOfEditTexts(1);
        } // End else if //
        else
        {
            return true;
        }
        return false;
    }

    public void setHintsOfEditTexts(int status)
    {
        switch (status)
        {
            case -1:    // Both of edit texts are not appropriate //
                editTextWordItself.setHint(R.string.word_hint_error);
                editTextWordItself.setHintTextColor(callerActivity.getResources().getColor(R.color.colorTertiary));
                editTextWordTranslation.setHint(R.string.translation_hint_error);
                editTextWordTranslation.setHintTextColor(callerActivity.getResources().getColor(R.color.colorTertiary));
                break;

            case 0:     // Just word edit text is not appropriate //
                editTextWordItself.setHint(R.string.word_hint_error);
                editTextWordItself.setHintTextColor(callerActivity.getResources().getColor(R.color.colorTertiary));
                break;

            case 1:     // Just translation edit text is not appropriate //
                editTextWordTranslation.setHint(R.string.translation_hint_error);
                editTextWordTranslation.setHintTextColor(callerActivity.getResources().getColor(R.color.colorTertiary));
                break;
        }
    }

    public ImageView getConfirmButton()
    {
        return confirmButton;
    }

    public EditText getEditTextWordItself()
    {
        return editTextWordItself;
    }
    public EditText getEditTextWordTranslation()
    {
        return editTextWordTranslation;
    }

}
