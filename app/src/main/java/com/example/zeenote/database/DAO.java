package com.example.zeenote.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Query("SELECT * FROM Notes WHERE Id = :id")
    NoteEntity getNoteById(int id);

    @Query("SELECT * FROM Notes ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAllNotes();


}
