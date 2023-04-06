package com.example.planifystudyapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Tasks {

    public Tasks(int id,String title, String date, String description ,int priority, boolean isCompleted){
        this.id = id;
        this.title = title;
        this.date= date;
        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "rowid" )
    public int id;
    @ColumnInfo(name = "date" )
    public String date;
    @ColumnInfo(name = "description" )
    public String description;

    @ColumnInfo(name = "priority" )
    public int priority;

    @ColumnInfo(name = "isCompleted" )
    public boolean isCompleted;


}
