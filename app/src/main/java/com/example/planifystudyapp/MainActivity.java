package com.example.planifystudyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
    private boolean mIsInverted;
    private TaskListAdapter adapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mIsInverted = sharedPref.getBoolean("tfSetting", false);

//        Toast.makeText(MainActivity.this, "The current tf setting value is " + sharedPref.getBoolean("tfSetting", true)
//                , Toast.LENGTH_LONG).show();



        RecyclerView recyclerView = findViewById(R.id.lstTasks);
        adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        Log.d("reclerr", "onCreate: ");

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
            case R.id.menu_settings:


                startActivity(new Intent(MainActivity.this, SettingsActivity.class));


            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    protected void onResume() {
        super.onResume();

        // Get the value of the boolean from shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isInverted = prefs.getBoolean("tfSetting", true);
        Toast.makeText(MainActivity.this,
                "The current tf setting value after onResume is " + prefs.getBoolean("tfSetting", false)+
                        "\n value of isInverted is: " + isInverted+
                        "\n value of mIsInverted is: " + mIsInverted

                , Toast.LENGTH_LONG).show();


        // If the value has changed, update the adapter
        if (isInverted != mIsInverted) {
            mIsInverted = isInverted;
            adapter.setIsInverted(mIsInverted);
            adapter.notifyDataSetChanged();

        }
    }
}