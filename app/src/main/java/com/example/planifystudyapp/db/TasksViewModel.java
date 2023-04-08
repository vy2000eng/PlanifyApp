package com.example.planifystudyapp.db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    private LiveData<List<Tasks>> tasks;



    public TasksViewModel(Application application){
        super(application);
        tasks = TasksDatabase.getDatabase(getApplication()).taskDao().getAll();
    }
    public void filterTasks(boolean isCompleted) {
        if (isCompleted)
            tasks = TasksDatabase.getDatabase(getApplication()).taskDao().getUnCompleted(true);
        else
            tasks = TasksDatabase.getDatabase(getApplication()).taskDao().getAll();
    }


    public LiveData<List<Tasks>> getAllTasks() {
        return tasks;
    }

}
