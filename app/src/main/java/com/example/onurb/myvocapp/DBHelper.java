package com.example.onurb.myvocapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.onurb.myvocapp.Structures.Word;
import com.example.onurb.myvocapp.Structures.WordList;

/**
 * Created by Onurb on 20.3.2020.
 */

public class DBHelper extends SQLiteOpenHelper {

    // DB and Table Names //
    private static final String DB_NAME = "RecordedDB";
    private static final String TBL_WORDS = "Words";
    private static final String TBL_LISTS = "WordLists";

    // Constructor //
    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,1);
    }

    // DB onCreate and onUpgrade methods //
    @Override
    public void onCreate(SQLiteDatabase db) {
        // The function where the database is created //
        // SQL DB creation statement //

        String list_sql = "CREATE TABLE IF NOT EXISTS " + TBL_LISTS +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Username TEXT NOT NULL)";

        String word_sql = "CREATE TABLE IF NOT EXISTS " + TBL_WORDS +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "list_of_id INTEGER NOT NULL, " +
                "Text TEXT NOT NULL, " +
                "Translation TEXT NOT NULL, " +
                "AskedCount INT NOT NULL, " +
                "CorrectCount INT NOT NULL, " +
                "FOREIGN KEY(list_of_id) REFERENCES " + TBL_LISTS + "(_id) ON DELETE CASCADE)";

        // DB exec method to create database really //
        db.execSQL(list_sql);
        db.execSQL(word_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        // The function where the database is upgraded //
        // It will dropped and recreated. //
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WORDS);
        onCreate(db);
    }

    // Function to add new words to the database //
    public void insertWord(Word word, long list_id)
    {
        // Open connection to the database //
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues object will keep the values to be added to database //
        ContentValues values = new ContentValues();
        values.put("list_of_id", list_id);
        values.put("Text", word.getText());
        values.put("Translation", word.getTranslation());
        values.put("AskedCount", word.getAsked_count());
        values.put("CorrectCount", word.getCorrect_count());

        // Insert command and closing the connection //
        db.insert(TBL_WORDS, null, values);
        db.close();
    }   // End of insert function //


    // Function to add new word list to the database //
    public void insertWordList(WordList wordList)
    {
        // Open connection to the database //
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues object will keep the values to be added to database //
        ContentValues values = new ContentValues();
        values.put("Name", wordList.getName());
        values.put("Username", wordList.getUsername());

        // Insert command and closing the connection //
        db.insert(TBL_LISTS, null, values);
        db.close();
    }


    // Function to update existing words to the database //
    public void updateWord(String old_word, String new_word, String new_translation)
    {
        // ContentValues object will keep the values to be added to database //
        ContentValues values = new ContentValues();
        values.put("Text", new_word);
        values.put("Translation", new_translation);

        // Open connection to the database //
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TBL_WORDS, values, "Text=" + "'" + old_word + "'", null);
        db.close();
    }   // End of update function //

    // Function to update existing words to the database //
    public void updateWordCounts(String word_to_update)
    {
        String sql = "UPDATE " + TBL_WORDS + " SET AskedCount=AskedCount+1 AND CorrectCount=CorrectCount+1" + " WHERE Text=" + "'" + word_to_update + "'";
        // Open connection to the database //
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        Log.e("Word Counts", "Word Counts Are Updated");
        db.close();
    }   // End of update function //

    // Function to update existing words to the database //
    public void updateWordAskedCount(String word_to_update)
    {
        String sql = "UPDATE " + TBL_WORDS + " SET AskedCount=AskedCount+1" + " WHERE Text=" + "'" + word_to_update + "'";
        // Open connection to the database //
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        Log.e("Asked Count", "Asked Count Is Updated");
        db.close();
    }   // End of update function //

    // Function to get all words //
    public Cursor getAllWords()
    {
        // Column names we will fetch from the database //
        String[] columns_to_be_fetched = new String[]{"_id", "Text", "Translation", "AskedCount", "CorrectCount"};

        // Open connection and query //
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_WORDS, columns_to_be_fetched, null, null, null, null, "Text"+" ASC");
        // Close connection //

        return cursor;
    }   // End of getAllWords function


    // Function to get all word lists //
    public Cursor getAllWordLists()
    {
        // Column names we will fetch from the database //
        String[] columns_to_be_fetched = new String[]{"_id", "Name"};

        // Open connection and query //
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_LISTS, columns_to_be_fetched, null, null, null, null, "Name"+" ASC");
        // Close connection //
        return cursor;
    }   // End of getAllWordLists function //


    // Function to get all the words from a particular list //
    public Cursor getWordsFromList(Long list_id)
    {
        // Column names we will fetch from the database //
        String[] columns_to_be_fetched = new String[]{"_id", "Text", "Translation", "AskedCount", "CorrectCount"};

        Log.e("Id of list", "List ID is: "+list_id);

        // Open connection and query //
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_WORDS, columns_to_be_fetched, "list_of_id=?", new String[]{Long.toString(list_id)}, null ,null, "Text"+" ASC");

        // Close connection //
        return cursor;

    }   // End of getWordsFromList function //


    // Function to get one word //
    public Cursor getOneWord(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_WORDS, null, "_id=" + id, null, null, null, null);

        return cursor;
    } // End of getOneWord function

    // Function to check list name //
    public Boolean checkListName(String list_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_LISTS, null, "Name=" + "'" + list_name + "'", null, null, null, null);

        if(cursor.getCount() > 0)   // Means that there exists a list with the same name
        {
            return true;
        }
        else
        {
            return false;           // Means that there is no such a list //
        }
    }
    // End of checkListName function //

    // Function to check word //
    public Boolean checkWord(String word_text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TBL_WORDS, null, "Text=" + "'" + word_text + "'", null, null, null, null);

        if(cursor.getCount() > 0)   // Means that there exists a list with the same name
        {
            return true;
        }
        else
        {
            return false;           // Means that there is no such a list //
        }
    }
    // End of checkListName function //


    // Function to delete a word //
    public void deleteWord(String word)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TBL_WORDS, "Text=" + word, null);
        db.execSQL("DELETE FROM " + TBL_WORDS + " WHERE Text='" +word+"'");
        db.close();
    } // End of delete word function


    // Function to delete a list //
    public void deleteList(String list)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TBL_LISTS + " WHERE Name='" +list+"'");
        db.close();
    }

    public void deleteTheList(Long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TBL_LISTS + " WHERE _id=" +id+"");
        db.close();
    }

}
