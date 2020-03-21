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

import com.example.onurb.myvocapp.R;

/**
 * Created by Onurb on 20.3.2020.
 */

public class WordListPopup extends Dialog{

    private Activity callerActivity;
    private EditText editTextWordListName;
    private ImageView confirmButton;

    public WordListPopup(Activity activity) {
        super(activity);
        this.callerActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list_layout);
        editTextWordListName = (EditText)findViewById(R.id.nw_list_name);
        confirmButton = (ImageView)findViewById(R.id.confirm_button_word_list);
        assignWidthAndHeight();
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

    public boolean checkListNameEnteredOrNot()
    {
        String wordListName = editTextWordListName.getText().toString();
        if(! wordListName.equals(""))
        {
            return true;
        }
        editTextWordListName.setHint(R.string.warning_listname_empty);
        editTextWordListName.setHintTextColor(Resources.getSystem().getColor(R.color.option_wrong_bg));
        return false;
    }

    public ImageView getConfirmButton()
    {
        return confirmButton;
    }

    public EditText getEditTextWordListName()
    {
        return editTextWordListName;
    }

    /*
    public void createNewList()
    {
        boolean listNameEntered = checkListNameEnteredOrNot();
        if(listNameEntered)
        {
            try
            {
                startSaveWordListTask();
            }
            catch (Exception e)
            {
                Log.e("Save Failed", "Error While Saving The WordList");
            }
        }
    }
    */
/*
    private void startSaveWordListTask()
    {
        String wordListName = editTextWordListName.getText().toString();
        WordList wordListObject = new WordList(wordListName, "onurbayrktr");
        SaveWordListAsyncTask saveWordListTask = new SaveWordListAsyncTask();
        DBHelper dbHelper = new DBHelper(callerActivity);
        try
        {
            saveWordListTask.execute(callerActivity, dbHelper, wordListObject);
        }
        catch (Exception e)
        {
            Log.e("Error", e.toString());
        }
    }
    */
}
