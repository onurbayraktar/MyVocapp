package com.example.onurb.myvocapp.Structures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onurb.myvocapp.Animation;
import com.example.onurb.myvocapp.AsyncTasks.DeleteWordListAsyncTask;
import com.example.onurb.myvocapp.AsyncTasks.WordListsFetchAsyncTask;
import com.example.onurb.myvocapp.DBHelper;
import com.example.onurb.myvocapp.R;

/**
 * Created by Onurb on 21.3.2020.
 */

public class CustomWordListAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    private TextView tvListName;
    private ImageView deleteButton;
    private Long idOfList;
    private Activity callerActivity;

    public CustomWordListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        callerActivity = (Activity) context;
        this.cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.single_wordlist_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tvListName = (TextView) view.findViewById(R.id.tv_list_name);
        tvListName.setText(cursor.getString(cursor.getColumnIndex("Name")));
        idOfList = cursor.getLong(cursor.getColumnIndex("_id"));
        final Long id = idOfList;
        deleteButton = view.findViewById(R.id.iv_list_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Animation animationObject = new Animation();
                animationObject.bounceAnimation(view);
                Handler wait = new Handler();
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customOnClick(id);
                    }
                }, 1000);
            }
        });
    }

    public void customOnClick(final Long id)
    {
        startDeleteWordListTask(id);
    }

    private void startDeleteWordListTask(Long id)
    {
        DBHelper dbHelper = new DBHelper(callerActivity);
        DeleteWordListAsyncTask deleteWordListTask = new DeleteWordListAsyncTask();
        try
        {
            deleteWordListTask.execute(dbHelper, id);
        }
        catch (Exception e)
        {
            Log.e("Err:DeleteList", "Error while deleting the list");
        }
        finally
        {
            startWordListsFetchTask();
        }
    }

    private void startWordListsFetchTask()
    {
        DBHelper dbHelper = new DBHelper(callerActivity);
        WordListsFetchAsyncTask wordListsFetchTask = new WordListsFetchAsyncTask();
        try
        {
            wordListsFetchTask.execute(callerActivity, dbHelper);
        }
        catch (Exception e)
        {
            Log.e("Err:WordListsFetch", "Error occured while fethcing the wordlists");
        }
    }
}