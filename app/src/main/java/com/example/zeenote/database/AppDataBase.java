package com.example.zeenote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;



@TypeConverters(DateConverter.class)

@Database(entities = {NoteEntity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public static final String DATA_BASE_NAME="notedatabase.db";
    public static volatile AppDataBase instance;
    private static final Object LOCK = new Object();
    public abstract DAO dao();

    public static AppDataBase getInstance(Context mContext){
        if (instance == null)
        {
            synchronized (LOCK){
                if (instance==null)
                {
                    instance = Room.databaseBuilder(mContext,
                            AppDataBase.class,DATA_BASE_NAME).build();
                }
            }
        }
        return instance;
    }

}