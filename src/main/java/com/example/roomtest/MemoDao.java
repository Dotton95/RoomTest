package com.example.roomtest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MemoDao {
    @Insert(onConflict = REPLACE)
    void insert(Memo memo);

    @Delete
    void delete(Memo memo);

    @Query("UPDATE memo SET nicName = :nicName WHERE 'no' = :no")
    void update(int no, String nicName);


    @Query("SELECT * FROM memo")
    List<Memo> getAll();
}
