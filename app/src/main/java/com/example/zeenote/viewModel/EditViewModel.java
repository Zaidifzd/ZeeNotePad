package com.example.zeenote.viewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.zeenote.database.AppRepository;
import com.example.zeenote.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditViewModel extends AndroidViewModel {
    public MutableLiveData<NoteEntity> mMutableLiveData = new MutableLiveData<>();
    private final AppRepository mRepository;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public EditViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void saveAndExit(String title, String noteText) {
        NoteEntity  noteEntity = mMutableLiveData.getValue();
        if (noteEntity == null){
            if (TextUtils.isEmpty(noteText.trim())){
                return;
            }
            else {
                noteEntity = new NoteEntity(title.trim(),new Date(),noteText.trim());
            }
        }
        else {
            noteEntity.setTitle(title);
            noteEntity.setData(noteText);
            noteEntity.setDate(new Date());
        }
        mRepository.updateNote(noteEntity);
    }


    public void loadDataById(int id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity noteEntity = mRepository.loadDataById(id);
                mMutableLiveData.postValue(noteEntity);
            }
        });
    }

    public void deleteNote() {
        mRepository.deleteNote(mMutableLiveData.getValue());
    }
}
