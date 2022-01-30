package com.example.zeenote;

import static com.example.zeenote.ConstentKeys.ConstentKeys.IS_EDIT_KEY;
import static com.example.zeenote.ConstentKeys.ConstentKeys.NOTE_KEY;

import android.content.Intent;
import android.os.Bundle;

import com.example.zeenote.database.NoteEntity;
import com.example.zeenote.viewModel.EditViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeenote.databinding.ActivityEditorBinding;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    private ActivityEditorBinding binding;
    EditText mEditText,mTitle;
    private boolean isNewNote = false;
    private boolean isEditNote = false;
    Bundle bundle;
    public EditViewModel mEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_check_24);
        if (savedInstanceState != null){
            isEditNote = savedInstanceState.getBoolean(IS_EDIT_KEY);
        }
        mEditText = findViewById(R.id.editNote);
        mTitle = findViewById(R.id.title);

        initViewModel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_EDIT_KEY,true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        mEditViewModel = ViewModelProviders.of(this)
                .get(EditViewModel.class);

        Observer<NoteEntity> mObserver = noteEntity -> {
           if (!isEditNote){
               mTitle.setText(Objects.requireNonNull(noteEntity.getTitle()));
               mEditText.setText(Objects.requireNonNull(noteEntity.getData()));
           }
        };

            mEditViewModel.mMutableLiveData.observe(this,mObserver);

            bundle = getIntent().getExtras();
            if (bundle == null){
                isNewNote = true;

            }
            else
            {
                isNewNote = false;
                int id = bundle.getInt(NOTE_KEY);
                mTitle.setText("Edit note");
                mEditViewModel.loadDataById(id);

            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isNewNote){
            getMenuInflater().inflate(R.menu.menu_editor,menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.closebtn,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            // saveData methods
            saveAndExit();
            startActivity(new Intent(EditorActivity.this,MainActivity.class));
            finish();
            Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.deleteNote)
        {
            // delete Note Method
            deleteNote();
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditorActivity.this,MainActivity.class));
            finish();
        }

        else if(item.getItemId() == R.id.close)
        {
            mTitle.setText("");
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        mEditViewModel.deleteNote();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(mEditText.getText().toString().trim()))
        {
            saveAndExit();
            Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(EditorActivity.this,MainActivity.class));
        finish();
    }

    private void saveAndExit() {
            mEditViewModel.saveAndExit(mTitle.getText().toString().trim(),mEditText.getText().toString().trim());
    }
}