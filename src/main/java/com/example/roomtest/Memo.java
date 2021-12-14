package com.example.roomtest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "memo")
public class Memo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int no;

    @ColumnInfo(name="nicName")
    public String nicName;
}
