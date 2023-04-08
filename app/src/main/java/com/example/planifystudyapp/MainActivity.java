package com.example.planifystudyapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.ViewHolder.TaskListAdapter;

public class MainActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        RecyclerView recyclerView = findViewById(R.id.lstTasks);
        TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);


    }

}