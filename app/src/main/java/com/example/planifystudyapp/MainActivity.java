package com.example.planifystudyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.ViewHolder.TaskListAdapter;
import com.example.planifystudyapp.db.Tasks;
import com.example.planifystudyapp.db.TasksDatabase;
import com.example.planifystudyapp.db.TasksViewModel;

public class MainActivity extends AppCompatActivity {
    private int task_id;
    private TasksViewModel TasksViewModel;
    private boolean mIsInverted;
    private String mCardScheme;
    private TaskListAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isCompleted = false;

    public TextView typeOfTask;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        typeOfTask = findViewById(R.id.which_tasks);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mIsInverted = sharedPref.getBoolean("tfSetting", false);
        mCardScheme = sharedPref.getString("cardScheme","");
        if (savedInstanceState != null) {
            isCompleted = savedInstanceState.getBoolean("filtered");
        }




        Toast.makeText(MainActivity.this, "The current tf setting value is " + sharedPref.getString("cardScheme", "")
                , Toast.LENGTH_LONG).show();



        recyclerView = findViewById(R.id.lstTasks);
        adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        Log.d("reclerr", "onCreate: ");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setBackgroundColor(Color.DKGRAY);


        TasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        //TasksViewModel.filterIsComplete(isCompleted);
        TasksViewModel.getUnCompletedTasks().observe(this, adapter::setTasks);

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
                return true;
            case R.id.menu_completed:
                isCompleted = !isCompleted;
                if(isCompleted){
                    typeOfTask.setText("Completed Tasks:");
                    item.setIcon(R.drawable.check_mark);
                    TasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
                    TasksViewModel.filterIsComplete(isCompleted);
                    TasksViewModel.getCompletedTasks();
                }else{
                    typeOfTask.setText("To do:");

                    item.setIcon(R.drawable.assignment_icon);
                    TasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
                    TasksViewModel.filterIsComplete(isCompleted);
                    TasksViewModel.getUnCompletedTasks();
                }
                recyclerView = findViewById(R.id.lstTasks);
                adapter = new TaskListAdapter(this);

                recyclerView.setAdapter(adapter);
                adapter.setLightOrDark(mCardScheme);


                TasksViewModel.getAllTasks().observe(this, adapter::setTasks);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("filtered", isCompleted);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the value of the boolean from shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int color = (prefs.getString("colorScheme", "").equals("light")? Color.WHITE:Color.DKGRAY);

        String cardScheme = null;
        if(prefs.getString("cardScheme","").equals("Light")){
            cardScheme = "Light";
        }else if(prefs.getString("cardScheme","").equals("Dark")){
            cardScheme = "Dark";
        }else{
            cardScheme = "Inverted";

        }

        //prefs.getString("cardScheme", "");//prefs.getString("cardScheme", "");;//prefs.getString("cardScheme", "");



        //recyclerView.setBackgroundColor(color);

        //        if(prefs.getString("colorScheme", "true").equals("dark")){
//            recyclerView.setBackgroundColor(Color.DKGRAY);
//
//
//        }
        //boolean isInverted = prefs.getBoolean("tfSetting", false);

        Toast.makeText(MainActivity.this,
                "The current card color value setting value after onResume is " + prefs.getString("cardScheme", "")
                +"\nThe mCardValue  onResume i:" + mCardScheme

                , Toast.LENGTH_SHORT).show();
//        Toast.makeText(MainActivity.this,
//                "The current tf setting value after onResume is " + prefs.getBoolean("tfSetting", false)+
//                        "\n value of isInverted is: " + isInverted+
//                        "\n value of mIsInverted is: " + mIsInverted
//
//                , Toast.LENGTH_LONG).show();

        recyclerView.setBackgroundColor(color);

        // If the value has changed, update the adapter
        //if(!cardScheme.equals(mCardScheme)){
            mCardScheme = cardScheme;
            //cardScheme = mcardScheme;
            adapter.setLightOrDark(cardScheme);
            adapter.notifyDataSetChanged();

        //}

    }
}