package com.example.roomtest;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Memo.class},version = 1,exportSchema = false)
public abstract class MemoDB extends RoomDatabase {
    private static MemoDB database;

    public abstract MemoDao memoDao();

    public synchronized static MemoDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    MemoDB.class,"memo.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
