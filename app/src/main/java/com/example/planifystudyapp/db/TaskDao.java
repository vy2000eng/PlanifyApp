package com.example.planifystudyapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks WHERE isCompleted = :onlyUncompleted " +
            "ORDER BY title COLLATE NOCASE, rowid")
    LiveData<List<Tasks>> getUnCompleted(boolean onlyUncompleted);

    @Query("SELECT * FROM tasks ORDER BY title COLLATE NOCASE, rowid")
    LiveData<List<Tasks>> getAll();

    @Query("SELECT * FROM tasks WHERE rowid = :taskId")
    Tasks getById(int taskId);

    @Insert
    void insert(Tasks... tasks);

    @Update
    void update(Tasks... task);

    @Delete
    void delete(Tasks... user);

    @Query("DELETE FROM tasks WHERE rowid = :taskId")
    void delete(int taskId);

}
