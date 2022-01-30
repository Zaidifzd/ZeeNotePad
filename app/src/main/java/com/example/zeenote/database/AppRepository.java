package com.example.zeenote.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    public static AppRepository ourInstance;
    public LiveData<List<NoteEntity>> mNoteEntity;
    private final AppDataBase mDataBase;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context){
        return ourInstance= new AppRepository(context);
    }
    private AppRepository(Context context)
    {
        mDataBase = AppDataBase.getInstance(context);
        mNoteEntity = getAllNotes();
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDataBase.dao().getAllNotes();
    }

    public void updateNote(NoteEntity noteEntity) {
        mExecutor.execute(() -> mDataBase.dao().insertNote(noteEntity));
    }

    public NoteEntity loadDataById(int id) {
        return mDataBase.dao().getNoteById(id);
    }

    public void deleteNote(NoteEntity value) {
        mExecutor.execute(() -> mDataBase.dao().deleteNote(value));
    }
}
