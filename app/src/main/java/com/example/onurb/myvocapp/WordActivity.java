package com.example.onurb.myvocapp;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onurb.myvocapp.AsyncTasks.SaveWordAsyncTask;
import com.example.onurb.myvocapp.Interfaces.fetchWordsCompleted;
import com.example.onurb.myvocapp.AsyncTasks.WordsFetchAsyncTask;
import com.example.onurb.myvocapp.Structures.Component;
import com.example.onurb.myvocapp.Structures.CustomWordAdapter;
import com.example.onurb.myvocapp.Structures.Variable;
import com.example.onurb.myvocapp.Structures.Word;
import com.example.onurb.myvocapp.Structures.WordPopup;

public class WordActivity extends AppCompatActivity implements View.OnClickListener,fetchWordsCompleted{

    private LinearLayout addLayout;
    private ListView listViewOfWords;
    private TextView tvUsername, tvAddWord, tvYourWords, tvAddWordHeader, tvEmptyWord;
    private TextView tvWordItself, tvWordTranslation;
    private ImageView deleteButton, updateButton;
    private WordPopup newWordPopup;
    private DBHelper dbHelper;
    private Activity context;

    private Long listOfID;
    private String username;
    private final static String statusInsert = "INSERT";
    static final int INITIAL_ASKED_COUNT = 0;
    static final int INITIAL_CORRECT_COUN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        initializeActivityComponents();
        fetchExtrasFromPreviousActivity();
        setValues();
        setOnClickFunctions();
        startWordsFetchActivity();
    }

    private void initializeActivityComponents()
    {
        // Targeting the plus icon //
        addLayout = (LinearLayout) findViewById(R.id.layout_add_word);
        tvAddWord = (TextView) findViewById(R.id.tv_add_word);
        tvUsername = (TextView) findViewById(R.id.header_username);
        tvYourWords = (TextView) findViewById(R.id.tv_your_words);
        tvEmptyWord = (TextView) findViewById(R.id.tv_empty_word);
        dbHelper = new DBHelper(getApplicationContext());
        context = WordActivity.this;
    }

    private void fetchExtrasFromPreviousActivity()
    {
        listOfID = getIntent().getExtras().getLong("List ID");
        username = getIntent().getStringExtra("Username");  // Get username from the prev activity //
    }

    private void setValues()
    {
        tvUsername.setText(username + "'s vocapp");  // Set username to text view //
    }

    private void setOnClickFunctions()
    {
        addLayout.setOnClickListener(this);
    }

    private void startWordsFetchActivity()
    {
        WordsFetchAsyncTask wordsFetchTask = new WordsFetchAsyncTask();
        try
        {
            wordsFetchTask.execute(context, dbHelper);
        }
        catch (Exception e)
        {
            Log.e("Error Fetch List", "There's an Error While Fetching WordLists");
        }
    }

    private void createNewWordPopup() {
        newWordPopup = new WordPopup(context, listOfID, statusInsert);
        newWordPopup.show();
        newWordPopup.getConfirmButton().setOnClickListener(this);
    }

    private void checkAndCreateWord()
    {
        boolean wordFieldsFilled = false;
        try
        {
            wordFieldsFilled = newWordPopup.checkWordAndTranslationEnteredOrNot();
        }
        catch (Exception e)
        {
            Log.e("Err:ListNameCheck", "Error While Checking The EditText For List Name");
        }

        if(wordFieldsFilled)
        {
            try
            {
                startSaveWordTask();
            }
            catch (Exception e)
            {
                Log.e("Err:ListSave", "Error While Saving The WordList");
            }
        }

    }

    private void startSaveWordTask() {
        String word = newWordPopup.getEditTextWordItself().getText().toString();
        String translation = newWordPopup.getEditTextWordTranslation().getText().toString();
        Word wordObject = new Word(word, translation, INITIAL_ASKED_COUNT, INITIAL_CORRECT_COUN);
        SaveWordAsyncTask saveWordTask = new SaveWordAsyncTask();
        try
        {
            saveWordTask.execute(dbHelper, wordObject, listOfID);
        }
        catch (Exception e)
        {
            Log.e("Error", e.toString());
        }
        finally {
            newWordPopup.dismiss();
        }
    }


    @Override
    public void onFetchWordsTaskComplete(Cursor result) {
        Component componentObject = new Component(tvWordItself, tvWordTranslation, deleteButton, updateButton);
        Variable variableObject = new Variable(context, dbHelper);
        CustomWordAdapter adapter = new CustomWordAdapter(context, result, 0, componentObject, variableObject);
        listViewOfWords = (ListView) findViewById(R.id.list_view_recorded);
        if(result.getCount() == 0)
        {   // If there's no saved words //
            listViewOfWords.setEmptyView(findViewById(R.id.tv_empty_word));
        }
        else
        {   // If there are words saved before //
            listViewOfWords.setAdapter(adapter);
        }

    }
    @Override
    public void onClick(View view) {
        Animation animationObject = new Animation();
        animationObject.bounceAnimation(view);
        Handler wait = new Handler();
        switch(view.getId())
        {
            case R.id.layout_add_word:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNewWordPopup();
                    }
                }, 1000);
                break;
            case R.id.confirm_button_new_word:
                wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO : ACCORDING TO STATUS CREATEWORD OR UPDATE WORD
                        checkAndCreateWord();
                        startWordsFetchActivity();
                        newWordPopup.dismiss();
                    }
                },1000);
        }
    }
}
