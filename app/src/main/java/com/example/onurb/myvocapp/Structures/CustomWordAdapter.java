package com.example.onurb.myvocapp.Structures;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onurb.myvocapp.Animation;
import com.example.onurb.myvocapp.AsyncTasks.DeleteWordAsyncTask;
import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.R;

/**
 * Created by Onurb on 21.3.2020.
 */

public class CustomWordAdapter extends CursorAdapter{

    private LayoutInflater cursorInflater;
    private TextView tvWordItself;
    private TextView tvWordTranslation;
    private ImageView deleteButton;
    private ImageView updateButton;
    private Activity callerActivity;
    private DBHelper dbHelper;
    private static String statusUpdate = "UPDATE";

    public CustomWordAdapter(Context context, Cursor c, int flags, Component componentObjet, Variable variableObject) {
        super(context, c, flags);
        tvWordItself = componentObjet.getTvWordItself();
        tvWordTranslation = componentObjet.getTvWordTranslation();
        deleteButton = componentObjet.getDeleteButton();
        updateButton = componentObjet.getUpdateButton();
        callerActivity = variableObject.getCallerActivity();
        dbHelper = new DBHelper(callerActivity);
        this.cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.single_word_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView tvWordItself = (TextView) view.findViewById(R.id.tv_word);
        tvWordItself.setText(cursor.getString(cursor.getColumnIndex("Text")));
        final TextView tvWordTranslation = (TextView) view.findViewById(R.id.tv_translation);
        tvWordTranslation.setText(cursor.getString(cursor.getColumnIndex("Translation")));
        deleteButton = view.findViewById(R.id.delete_icon);
        updateButton = view.findViewById(R.id.update_icon);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Animation animationObject = new Animation();
                animationObject.bounceAnimation(view);
                Handler wait = new Handler();
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customOnClick(view, tvWordItself, tvWordTranslation);
                    }
                }, 1000);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Animation animationObject = new Animation();
                animationObject.bounceAnimation(view);
                Handler wait = new Handler();
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customOnClick(view, tvWordItself, tvWordTranslation);
                    }
                }, 1000);
            }
        });
    }

    public void customOnClick(final View view, final TextView tv, final TextView tv_translate) {
        if(view.getId() == R.id.update_icon)
        {
            String wordToUpdate = tv.getText().toString();
            String translationToUpdate = tv_translate.getText().toString();
            updateWordPopup(wordToUpdate, translationToUpdate);
        }
        else if(view.getId() == R.id.delete_icon)
        {
            String wordToDelete = tv.getText().toString();
            startDeleteWordTask(wordToDelete);
        }
    }

    public void startDeleteWordTask(String wordToDelete)
    {
        DeleteWordAsyncTask deleteWordTask = new DeleteWordAsyncTask();
        deleteWordTask.execute(dbHelper, wordToDelete);
    }

    public void updateWordPopup(String wordToUpdate, String translationToUpdate)
    {
        WordPopup newWordPopup = new WordPopup(callerActivity, statusUpdate, wordToUpdate, translationToUpdate);
        newWordPopup.show();
    }
}
