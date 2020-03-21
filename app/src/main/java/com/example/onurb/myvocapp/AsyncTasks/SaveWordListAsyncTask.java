package com.example.onurb.myvocapp.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.Structures.WordList;

/**
 * Created by Onurb on 20.3.2020.
 */

public class SaveWordListAsyncTask extends AsyncTask<Object, Object, Object> {

    DBHelper dbHelper;
    WordList wordListObject;

    @Override
    protected Object doInBackground(Object... params) {
        dbHelper = (DBHelper) params[0];
        wordListObject = (WordList) params[1];
        dbHelper.insertWordList(wordListObject);
        return null;
    }
}
