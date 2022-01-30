package com.example.zeenote.adapters;

import static com.example.zeenote.ConstentKeys.ConstentKeys.NOTE_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeenote.EditorActivity;
import com.example.zeenote.R;
import com.example.zeenote.database.NoteEntity;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myAdapter> {

    public MyAdapter(Context mContext, List<NoteEntity> mDataNote) {
        this.mContext = mContext;
        this.mDataNote = mDataNote;
    }

    private final Context mContext;
    List<NoteEntity> mDataNote;

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.singleline,parent,false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, @SuppressLint("RecyclerView") int position) {
            holder.mtitle.setText(mDataNote.get(position).getTitle());
            holder.mdata.setText(mDataNote.get(position).getData());
            holder.mdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    Intent intent = new Intent(mContext, EditorActivity.class);
                    intent.putExtra(NOTE_KEY,mDataNote.get(position).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);

                }
            });

    }

    @Override
    public int getItemCount() {
        return mDataNote.size();
    }

    static class myAdapter extends RecyclerView.ViewHolder {
        TextView mtitle,mdata;
        ImageView mMenu;
        public myAdapter(@NonNull View itemView) {
            super(itemView);

            mtitle = itemView.findViewById(R.id.titles);
            mdata = itemView.findViewById(R.id.data);

        }
    }
}
