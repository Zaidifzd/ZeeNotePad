package com.example.zeenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zeenote.adapters.MyAdapter;
import com.example.zeenote.database.NoteEntity;
import com.example.zeenote.viewModel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_Note;
    List<NoteEntity> mNoteList = new ArrayList<>();
    ListViewModel mViewModel;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
        addNewNote();
        initViewModel();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }


    private void initViewModel() {
        @SuppressLint("NotifyDataSetChanged") Observer<List<NoteEntity>> notesObserver = noteEntities -> {
            mNoteList.clear();
            mNoteList.addAll(noteEntities);
            if (myAdapter == null)
            {
                myAdapter = new MyAdapter(MainActivity.this,mNoteList);
                recyclerView.setAdapter(myAdapter);
            }
            else{
                myAdapter.notifyDataSetChanged();
            }
        };
        mViewModel = ViewModelProviders.of(this)
                .get(ListViewModel.class);
        mViewModel.mNoteList.observe(MainActivity.this,notesObserver);
    }

    private void addNewNote() {
        add_Note.setOnClickListener(view -> {
            //
            Intent intent = new Intent(MainActivity.this,EditorActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initialView() {
        recyclerView = findViewById(R.id.recyclerView);
        add_Note = findViewById(R.id.floatingActionButton);
    }
}