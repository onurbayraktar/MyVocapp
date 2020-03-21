package com.example.onurb.myvocapp;

import com.example.onurb.myvocapp.AsyncTasks.*;
import com.example.onurb.myvocapp.Interfaces.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, checkWordListExistCompleted {

    private TextView tvUsername;
    private EditText etUsername;
    private ImageView ivIconConfirm;
    private String username;
    private Boolean listExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean checkResult = checkSharedPreferencesForUsername();
        if(checkResult)
        {
            launchHomeIntent();
        }
        initializeActivityComponents();
    }

    private void initializeActivityComponents()
    {
        // Targetting the componenets //
        tvUsername = (TextView)findViewById(R.id.tv_your_username);
        etUsername = (EditText)findViewById(R.id.et_username);
        ivIconConfirm = (ImageView)findViewById(R.id.iv_icon);
        listExists = false;

        ivIconConfirm.setOnClickListener(this);
    }

    private boolean checkSharedPreferencesForUsername()
    {
        Context context = MainActivity.this;
        boolean usernameSavedOrNot;
        String sharedPreferenceKey = getString(R.string.preference_file_key);

        SharedPreferences sharedPrefObject = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE);
        usernameSavedOrNot = checkUsernameSavedOrNot(sharedPrefObject);

        return usernameSavedOrNot;
    }

    private boolean checkUsernameSavedOrNot(SharedPreferences sharedPrefObject)
    {
        String savedUsername = sharedPrefObject.getString(getString(R.string.saved_username), "");
        if(!savedUsername.equals(""))
        {
            username = savedUsername;
            return true;
        }
        return false;
    }

    private boolean checkUsernameEnteredOrNot()
    {
        String enteredUsername = etUsername.getText().toString();
        if(! enteredUsername.matches(""))
        {
            username = enteredUsername;
            return true;
        }
        else
        {
            return false;
        }
    }

    private void saveSharedPreferences(String name)
    {
        // Open sharedPreferences file //
        Context context = MainActivity.this;
        SharedPreferences shared_pref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = shared_pref.edit();
        editor.putString(getString(R.string.saved_username), name);
        editor.commit();
    }

    private void launchHomeIntent()
    {
        startWordListAsyncTask();
    }


    private void startWordListAsyncTask()
    {
        WordListExistOrNotAsyncTask wordListTask = new WordListExistOrNotAsyncTask();
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        wordListTask.execute(MainActivity.this, dbHelper);
    }

    @Override
    public void onClick(View view) {
        Animation animationObject = new Animation();
        animationObject.bounceAnimation(view);
        boolean userNameEntered = checkUsernameEnteredOrNot();
        if(userNameEntered)
        {
            // Delay for animation //
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    saveSharedPreferences(username);
                    launchHomeIntent();
                }
            }, 1600);
        }
        else
        {
            etUsername.setHint(R.string.warning_username_empty);
        }
    }

    @Override
    public void onTaskComplete(Boolean result) {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        homeIntent.putExtra("username", username);
        homeIntent.putExtra("list_check", result);
        startActivity(homeIntent);
    }
}
