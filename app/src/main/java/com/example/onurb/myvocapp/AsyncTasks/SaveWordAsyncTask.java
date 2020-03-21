package com.example.onurb.myvocapp.AsyncTasks;

import android.os.AsyncTask;

import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.Structures.Word;
import com.example.onurb.myvocapp.Structures.WordList;

/**
 * Created by Onurb on 21.3.2020.
 */

public class SaveWordAsyncTask extends AsyncTask<Object, Object, Object> {

    private DBHelper dbHelper;
    private Long idOfList;
    private Word wordObject;

    @Override
    protected Object doInBackground(Object... params) {
        dbHelper = (DBHelper) params[0];
        wordObject = (Word) params[1];
        idOfList = (Long) params[2];
        dbHelper.insertWord(wordObject, idOfList);
        return null;
    }
}
