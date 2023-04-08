package com.example.planifystudyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.ViewHolder.TaskListAdapter;
import com.example.planifystudyapp.db.TasksDatabase;
import com.example.planifystudyapp.db.TasksViewModel;

public class MainActivity extends AppCompatActivity {
    private int task_id;
    private TasksViewModel TasksViewModel;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));


        RecyclerView recyclerView = findViewById(R.id.lstTasks);
        TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        TasksViewModel.getAllTasks().observe(this, adapter::setTasks);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main,menu);
       // menu.getItem(1).setIcon(R.drawable.)
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}