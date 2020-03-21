package com.example.onurb.myvocapp.Structures;

/**
 * Created by Onurb on 20.3.2020.
 */

public class WordList {

    // Fields that'll be used //
    private long list_id;
    private String name;
    private String username;

    // Constructor //
    public WordList(String name, String username)
    {
        this.name = name;
        this.username = username;
    }

    public long getList_id() {
        return list_id;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
