package com.example.zeenote.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long toTimeStamp(Date date)
    {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public  static Date toDate(Long toDate){
        return toDate == null ? null : new Date(toDate);
    }
}
