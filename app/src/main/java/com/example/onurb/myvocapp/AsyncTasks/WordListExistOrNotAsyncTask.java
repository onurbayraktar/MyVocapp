package com.example.onurb.myvocapp.AsyncTasks;

import com.example.onurb.myvocapp.Interfaces.*;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.onurb.myvocapp.DBHelper;

/**
 * Created by Onurb on 20.3.2020.
 */

public class WordListExistOrNotAsyncTask extends AsyncTask<Object, Object, Boolean> {

    private checkWordListExistCompleted mCallback;
    private DBHelper dbHelper;
    private Cursor result;

    @Override
    protected Boolean doInBackground(Object... params) {

        mCallback = (checkWordListExistCompleted) params[0];
        dbHelper = (DBHelper) params[1];
        result = dbHelper.getAllWordLists();
        Boolean listExists = false;

        // Means user created lists before //
        listExists = (result != null) && (result.getCount() > 0);
        return (Boolean) listExists;
    }

    protected void onPostExecute(Boolean result)
    {
        mCallback.onTaskComplete(result);
    }

}
