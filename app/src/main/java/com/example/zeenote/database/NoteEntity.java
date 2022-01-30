package com.example.zeenote.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Date date;
    private String data;

    @Ignore
    public NoteEntity() {
    }

    @Ignore
    public NoteEntity(String title, Date date, String data) {
        this.title = title;
        this.date = date;
        this.data = data;
    }

    public NoteEntity(int id, String title, Date date, String data) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}