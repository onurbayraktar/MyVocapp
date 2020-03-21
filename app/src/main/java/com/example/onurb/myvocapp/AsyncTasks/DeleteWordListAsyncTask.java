package com.example.onurb.myvocapp.AsyncTasks;

import android.os.AsyncTask;

import com.example.onurb.myvocapp.DBHelper;

/**
 * Created by Onurb on 21.3.2020.
 */

public class DeleteWordListAsyncTask extends AsyncTask<Object, Object, Object> {

    private DBHelper dbHelper;
    private Long idOfList;

    @Override
    protected Object doInBackground(Object... params) {
        dbHelper = (DBHelper) params[0];
        idOfList = (Long) params[1];
        dbHelper.deleteTheList(idOfList);
        return null;
    }
}
