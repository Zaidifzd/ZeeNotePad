package com.example.zeenote.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.zeenote.database.AppRepository;
import com.example.zeenote.database.NoteEntity;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    public LiveData<List<NoteEntity>> mNoteList;
    private final AppRepository mRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNoteList = mRepository.mNoteEntity;
    }
}
