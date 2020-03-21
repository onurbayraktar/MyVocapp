package com.example.onurb.myvocapp.AsyncTasks;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.Interfaces.fetchWordListsCompleted;

/**
 * Created by Onurb on 21.3.2020.
 */

public class WordListsFetchAsyncTask extends AsyncTask<Object, Object, Cursor> {

    private fetchWordListsCompleted mCallback;
    private DBHelper dbHelper;

    @Override
    protected Cursor doInBackground(Object... params) {
        mCallback = (fetchWordListsCompleted) params[0];
        dbHelper = (DBHelper) params[1];
        Log.e("Start to fetch", "Start to fetch");
        return dbHelper.getAllWordLists();
    }

    protected void onPostExecute(Cursor result)
    {
        Log.e("Start to fetch", "Fetched");
        mCallback.onFetchWordListsTaskComplete(result);
    }

}
