package com.example.onurb.myvocapp;

import com.example.onurb.myvocapp.AsyncTasks.SaveWordListAsyncTask;
import com.example.onurb.myvocapp.Interfaces.*;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onurb.myvocapp.AsyncTasks.WordListsFetchAsyncTask;
import com.example.onurb.myvocapp.Structures.CustomWordListAdapter;
import com.example.onurb.myvocapp.Structures.WordList;
import com.example.onurb.myvocapp.Structures.WordListPopup;

public class WordListActivity extends AppCompatActivity implements View.OnClickListener, fetchWordListsCompleted {

    private TextView tvUsername, tvYourLists, tvListName, tvAddList;
    private ListView listViewOfWordLists;
    private LinearLayout layoutAddList, layoutDeleteList;
    private ImageView deleteButton;
    private Dialog newListPopup;
    private WordListPopup newWordListPopup;
    private DBHelper dbHelper;
    private Activity context;

    private String username;
    private boolean isTargetPractice, isTargetStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        initializeActivityComponents();
        fetchingExtrasFromPreviousActivity();
        setValues();
        setOnClickFunctions();
        startWordListsFetchTask();
    }

    private void initializeActivityComponents()
    {
        context = WordListActivity.this;
        tvUsername = (TextView) findViewById(R.id.header_username);
        tvYourLists = (TextView) findViewById(R.id.tv_your_lists);
        tvAddList = (TextView) findViewById(R.id.tv_add_list);
        layoutAddList = (LinearLayout) findViewById(R.id.layout_add_list);
        layoutDeleteList = (LinearLayout) findViewById(R.id.layout_list_delete);
        dbHelper = new DBHelper(getApplicationContext());
    }

    private void fetchingExtrasFromPreviousActivity()
    {
        username = getIntent().getStringExtra("username");  // Get username from the prev activity //
        isTargetPractice = getIntent().getBooleanExtra("Practice", false);
        isTargetStatistics = getIntent().getBooleanExtra("Statistics", false);
    }

    private void setValues()
    {
        tvUsername.setText(username + "'s vocapp");  // Set username to text view //
        if(isTargetPractice || isTargetStatistics)
        {
            layoutAddList.setVisibility(View.INVISIBLE);
        }
    }

    private void setOnClickFunctions()
    {
        layoutAddList.setOnClickListener(this);
    }

    private void startWordListsFetchTask()
    {
        WordListsFetchAsyncTask wordListsFetchTask = new WordListsFetchAsyncTask();
        try
        {
            wordListsFetchTask.execute(context, dbHelper);
        }
        catch (Exception e)
        {
            Log.e("Err:WordListsFetch", "Error occured while fethcing the wordlists");
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
    }

    private void launchPracticeActivity(Long idOfList)
    {
        Intent practiceActivity = new Intent(context, StartPracticeActivity.class);
        practiceActivity.putExtra("List ID", idOfList);
        practiceActivity.putExtra("username", username);
        startActivity(practiceActivity);
    }

    private void launchStatisticsActivity(Long idOfList)
    {
        Intent staticsActivity = new Intent(context, StatisticsActivity.class);
        staticsActivity.putExtra("List ID", idOfList);
        staticsActivity.putExtra("username", username);
        startActivity(staticsActivity);
    }

    private void launchWordsActivity(Long idOfList)
    {
        Intent wordsIntent = new Intent(context, WordActivity.class);
        wordsIntent.putExtra("List ID", idOfList);
        wordsIntent.putExtra("Username", username);
        startActivity(wordsIntent);
    }


    // OVERRIDEN METHODS //
    @Override
    public void onFetchWordListsTaskComplete(Cursor result) {
        CustomWordListAdapter adapter = new CustomWordListAdapter(context, result, 0);
        listViewOfWordLists = (ListView) findViewById(R.id.list_view_recorded_lists);
        listViewOfWordLists.setAdapter(adapter);
        listViewOfWordLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isTargetPractice)
                {
                    launchPracticeActivity(l);
                }
                else if(isTargetStatistics)
                {
                    launchStatisticsActivity(l);
                }
                else
                {
                    launchWordsActivity(l);
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        Animation animationObject = new Animation();
        animationObject.bounceAnimation(view);
        Handler wait = new Handler();
        switch(view.getId())
        {
            case R.id.layout_add_list:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNewListPopup();
                    }
                }, 1000);
                break;
            case R.id.iv_list_delete:
                // TODO: Delete List
                break;
            case R.id.confirm_button_word_list:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAndCreateNewList();
                        startWordListsFetchTask();
                        newWordListPopup.dismiss();
                    }
                }, 1000);

        }
    }
}
