package com.example.onurb.myvocapp.AsyncTasks;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.Interfaces.fetchWordListsCompleted;
import com.example.onurb.myvocapp.Interfaces.fetchWordsCompleted;
import com.example.onurb.myvocapp.Structures.Component;
import com.example.onurb.myvocapp.Structures.CustomWordAdapter;
import com.example.onurb.myvocapp.Structures.Variable;

/**
 * Created by Onurb on 21.3.2020.
 */

public class WordsFetchAsyncTask extends AsyncTask<Object, Object, Cursor>{

    private fetchWordsCompleted mCallback;
    private DBHelper dbHelper;

    @Override
    protected Cursor doInBackground(Object... params) {
        mCallback = (fetchWordsCompleted) params[0];
        dbHelper = (DBHelper) params[1];
        return dbHelper.getAllWords();
    }

    protected void onPostExecute(Cursor result)
    {
        Log.e("Start to fetch", "Fetched");
        mCallback.onFetchWordsTaskComplete(result);
    }
}
