package com.example.onurb.myvocapp.Structures;

/**
 * Created by Onurb on 20.3.2020.
 */

public class Word {

    private long word_id, id_of_list;
    private String text, translation;
    private int asked_count, correct_count;

    // Constructor //
    public Word(String text, String translation, int asked_count, int correct_count)
    {
        super();
        this.word_id = word_id;
        this.id_of_list = id_of_list;
        this.text = text;
        this.translation = translation;
        this.asked_count = asked_count;
        this.correct_count = correct_count;
    }


    // Getters and Setters //
    public long getWord_id() {
        return word_id;
    }

    public void setWord_id(long word_id) {
        this.word_id = word_id;
    }

    public long getId_of_list() {
        return id_of_list;
    }

    public void setId_of_list(long id_of_list) {
        this.id_of_list = id_of_list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getAsked_count() {
        return asked_count;
    }

    public void setAsked_count(int asked_count) {
        this.asked_count = asked_count;
    }

    public int getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(int correct_count) {
        this.correct_count = correct_count;
    }

}
