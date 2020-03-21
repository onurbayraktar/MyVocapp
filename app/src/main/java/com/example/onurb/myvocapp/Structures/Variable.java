package com.example.onurb.myvocapp.Structures;

import android.app.Activity;

import com.example.onurb.myvocapp.DBHelper;

/**
 * Created by Onurb on 21.3.2020.
 */

public class Variable {

    private Activity callerActivity;
    private DBHelper dbHelper;
    private boolean isTargetPractice, isTargetStatistics;

    public Variable() {}

    public Variable(Activity callerActivity, DBHelper dbHelper)
    {
        this.callerActivity = callerActivity;
        this.dbHelper = dbHelper;
    }

    public Activity getCallerActivity()
    {
        return callerActivity;
    }
    public DBHelper getDBHelper()
    {
        return dbHelper;
    }


}
