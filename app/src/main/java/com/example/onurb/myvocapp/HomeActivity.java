package com.example.onurb.myvocapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onurb.myvocapp.AsyncTasks.SaveWordListAsyncTask;
import com.example.onurb.myvocapp.Structures.WordList;
import com.example.onurb.myvocapp.Structures.WordListPopup;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUsername, tvCreateList, tvEditList, tvMakePractice, tvStatistics;
    private EditText etWordListName;
    private ImageView ivLogo;
    private WordListPopup newWordListPopup;
    private Activity context;

    private String username;
    private boolean userHasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeActivityComponents();
        fetchingExtrasFromPreviousActivity();
        setValues();
        setOnClickFunctions();
    }

    private void initializeActivityComponents()
    {
        tvUsername = (TextView)findViewById(R.id.header_username);
        tvCreateList = (TextView)findViewById(R.id.tv_create_list);
        tvEditList = (TextView)findViewById(R.id.tv_edit_list);
        tvMakePractice = (TextView)findViewById(R.id.tv_make_practice);
        tvStatistics = (TextView)findViewById(R.id.tv_statistics);
        ivLogo = (ImageView)findViewById(R.id.header_icon);
        context = HomeActivity.this;
    }

    private void fetchingExtrasFromPreviousActivity()
    {
        username = getIntent().getStringExtra("username");
        userHasList = getIntent().getBooleanExtra("list_check", false);
    }

    private void setValues()
    {
        tvUsername.setText(username + "'s vocapp");
        // Set the view of layouts according to has list variable //
        if(!userHasList)
        {
            tvEditList.setClickable(false);
            tvEditList.getBackground().setAlpha(128);
            tvMakePractice.setClickable(false);
            tvMakePractice.getBackground().setAlpha(128);
            tvStatistics.setClickable(false);
            tvStatistics.getBackground().setAlpha(128);
        }
        else
        {
            tvEditList.setClickable(true);
            tvEditList.getBackground().setAlpha(255);
            tvMakePractice.setClickable(true);
            tvMakePractice.getBackground().setAlpha(255);
            tvStatistics.setClickable(true);
            tvStatistics.getBackground().setAlpha(255);
        }
    }

    private void setOnClickFunctions()
    {
        // Setting onClick functions //
        tvCreateList.setOnClickListener(this);
        tvEditList.setOnClickListener(this);
        tvMakePractice.setOnClickListener(this);
        tvStatistics.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
    }

    private void launchWordListIntent(Intent wordListIntent)
    {
        try
        {
            wordListIntent.putExtra("username", username);
            startActivity(wordListIntent);
        }
        catch (Exception e)
        {
            Log.e("Error", "" + e);
        }

    }

    private void launchPracticeIntent(Intent wordListIntent)
    {
        try
        {
            wordListIntent.putExtra("username", username);
            wordListIntent.putExtra("Practice", true);
            startActivity(wordListIntent);
        }
        catch (Exception e)
        {
            Log.e("Error", "" + e);
        }
    }

    private void launchStatisticsIntent(Intent wordListIntent)
    {
        try
        {
            wordListIntent.putExtra("username", username);
            wordListIntent.putExtra("Statistics", true);
            startActivity(wordListIntent);
        }
        catch (Exception e)
        {
            Log.e("Error", "" + e);
        }
    }

    private void createNewListPopup()
    {
        newWordListPopup = new WordListPopup(context);
        newWordListPopup.show();
        newWordListPopup.getConfirmButton().setOnClickListener(this);
    }

    private void checkAndCreateNewList()
    {
        boolean listNameEntered = false;
        try
        {
            listNameEntered = newWordListPopup.checkListNameEnteredOrNot();
        }
        catch (Exception e)
        {
            Log.e("Err:ListNameCheck", "Error While Checking The EditText For List Name");
        }

        if(listNameEntered)
        {
            try
            {
                startSaveWordListTask();
            }
            catch (Exception e)
            {
                Log.e("Err:ListSave", "Error While Saving The WordList");
            }
        }

    }

    private void startSaveWordListTask()
    {
        String wordListName = newWordListPopup.getEditTextWordListName().getText().toString();
        WordList wordListObject = new WordList(wordListName, username);
        DBHelper dbHelper = new DBHelper(context);
        SaveWordListAsyncTask saveWordListTask = new SaveWordListAsyncTask();
        saveWordListTask.execute(dbHelper, wordListObject);
        newWordListPopup.dismiss();
        userHasList = true;
        tvEditList.setClickable(true);
        tvEditList.getBackground().setAlpha(255);
        tvMakePractice.setClickable(true);
        tvMakePractice.getBackground().setAlpha(255);
        tvStatistics.setClickable(true);
        tvStatistics.getBackground().setAlpha(255);
    }

    @Override
    public void onClick(View view) {
        final Intent wordListIntent = new Intent(HomeActivity.this, WordListActivity.class);
        Animation animationObject = new Animation();
        animationObject.bounceAnimation(view);
        Handler wait = new Handler();
        switch (view.getId())
        {
            case R.id.tv_create_list:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNewListPopup();
                    }
                }, 1000);
                break;

            case R.id.tv_edit_list:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchWordListIntent(wordListIntent);
                    }
                }, 1000);
                break;

            case R.id.tv_make_practice:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchPracticeIntent(wordListIntent);
                    }
                }, 1000);
                break;

            case R.id.tv_statistics:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchStatisticsIntent(wordListIntent);
                    }
                }, 1000);
                break;

            case R.id.confirm_button_word_list:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAndCreateNewList();
                    }
                }, 1000);
                break;
        }
    }
}
