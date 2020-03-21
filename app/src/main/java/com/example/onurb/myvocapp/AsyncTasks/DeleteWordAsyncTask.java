package com.example.onurb.myvocapp.AsyncTasks;

import android.os.AsyncTask;

import com.example.onurb.myvocapp.DBHelper;

/**
 * Created by Onurb on 21.3.2020.
 */

public class DeleteWordAsyncTask extends AsyncTask<Object, Object, Object>{

    private DBHelper dbHelper;
    private String wordToDelete;

    @Override
    protected Object doInBackground(Object... params) {
        dbHelper = (DBHelper) params[0];
        wordToDelete = (String) params[1];
        dbHelper.deleteWord(wordToDelete);
        return null;
    }
}
