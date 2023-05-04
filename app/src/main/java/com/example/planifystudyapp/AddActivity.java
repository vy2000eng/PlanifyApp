package com.example.planifystudyapp;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.planifystudyapp.db.Tasks;
import com.example.planifystudyapp.db.TasksDatabase;

public class AddActivity extends AppCompatActivity {
    private int task_id;
    private boolean isCompleted;
    EditText dueDate;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        Spinner spinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setSupportActionBar(findViewById(R.id.toolbar));

        task_id = getIntent().getIntExtra("task_id", -1);
        if (savedInstanceState == null) {
            if (task_id != -1) {

                TasksDatabase.getTask(task_id, task -> {
                    ((EditText) findViewById(R.id.taskNameEditText)).setText(task.title);
                    ((EditText) findViewById(R.id.dueDateEditText)).setText(task.date);
                    ((EditText) findViewById(R.id.descriptionEditText)).setText(task.description);
                    isCompleted = task.isCompleted;

                });
            }
        } else {
            isCompleted = savedInstanceState.getBoolean("completed");
        }

        EditText mDueDateEditText = findViewById(R.id.dueDateEditText);
        mDueDateEditText.setOnClickListener(v -> {
            showCalenderDialog();
        });

    }

    private void showCalenderDialog() {
        CalendarView calendarView = new CalendarView(this);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String content_of_due_date = (month + 1)+" / " +dayOfMonth +  " / " + year;
            ((EditText) findViewById(R.id.dueDateEditText)).setText(content_of_due_date);

        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(calendarView)
                .setPositiveButton("OK", (dialog, which) -> {
                 Log.d("Date", String.valueOf(calendarView));
                    // do something when positive button is clicked

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // do something when negative button is clicked
                });
        dialog = builder.create();
        dialog.show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add, menu);



        if(task_id == -1) {
            menu.getItem(1).setIcon(R.drawable.cancel_icon);
           // menu.getItem(1).setTitle(R.string.menu_cancel);
            setTitle("Add task");
        }else{
            menu.getItem(1).setTitle("Cancel");
            setTitle("update Task");

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.menu_save:
                updateDb();
                return true;
            case R.id.menu_delete:
                //deleteRecord();
                if (task_id != -1) {
                    ConfirmDeleteDialog confirmDialog = new ConfirmDeleteDialog();
                    confirmDialog.show(getSupportFragmentManager(), "deletionConfirmation");
                }
                else {
                    finish();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteRecord() {
        TasksDatabase.delete(task_id);
    }

    private void updateDb() {
        Spinner priority = findViewById(R.id.prioritySpinner);
        String contentOfPriority = priority.getSelectedItem().toString();
        int whatPriority =
                contentOfPriority.equals("High Priority")?2 :
                        contentOfPriority.equals("Medium Priority")?1:
                                contentOfPriority.equals("Low Priority")?0:-1;



        Tasks task = new Tasks(task_id == -1?0:task_id,
                ((EditText) findViewById(R.id.taskNameEditText)).getText().toString(),
                ((EditText) findViewById(R.id.dueDateEditText)).getText().toString(),
                ((EditText) findViewById(R.id.descriptionEditText)).getText().toString(),
                whatPriority,
                isCompleted

                //Integer.parseInt()
                );
        if (task_id == -1) {
           TasksDatabase.insert(task);
        } else {
            TasksDatabase.update(task);
        }
        finish(); // Quit activity

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCompleted", isCompleted);
    }
    public static class ConfirmDeleteDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

            builder.setTitle("Delete the task?")
                    .setMessage("You will not be able to undo the deletion!")
                    .setPositiveButton("Delete",
                            (dialog,id) -> {
                                ((AddActivity) getActivity()).deleteRecord();
                                getActivity().finish();
                            })
                    .setNegativeButton("Return to task list",
                            (dialog, id) -> getActivity().finish());
            return builder.create();
        }
    }


//    public static class DisplayCalender extends DialogFragment {
//        CalendarView calendarView;
//         DisplayCalender( CalendarView calenderView){
//            this.calendarView = calenderView;
//        }
//
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//
//            builder.setView(calendarView)
//                    .setPositiveButton("Punchline",
//                            (dialog)->{})
//                    .setNegativeButton("Cancel",
//                            (dialog, id) -> {});
//            return builder.create();
//        }
//
//        @Override
//        public void onSaveInstanceState(@NonNull Bundle outState) {
//            super.onSaveInstanceState(outState);
//            outState.putString("JJB", "tester");
//        }
//    }

}
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//
//import com.example.planifystudyapp.db.Tasks;
//import com.example.planifystudyapp.db.TasksDatabase;
//
//public class AddActivity extends AppCompatActivity {
//    private int task_id;
//
//    private boolean isCompleted;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setSupportActionBar(findViewById(R.id.toolbar));
//        task_id =  getIntent().getIntExtra("joke_id", -1);
//
//        if (savedInstanceState == null) {
//            if (task_id != -1) {
//                TasksDatabase.getTask(task_id, task -> {
//                    ((EditText) findViewById(R.id.taskNameEditText)).setText(task.title);
//                    ((EditText) findViewById(R.id.dueDateEditText)).setText(task.date);
//                    ((EditText) findViewById(R.id.descriptionEditText)).setText(task.description);
//                    isCompleted= task.isCompleted;
//                });
//            }
//        }
//        else {
//            isCompleted = savedInstanceState.getBoolean("completed");
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.activity_add,menu);
//        if(task_id == -1){
//            menu.getItem(1).setIcon(R.drawable.cancel_icon);
//            menu.getItem(1).setTitle("Cancel");
//            setTitle("Add Task");
//
//        }
//        else{
//            setTitle("Edit task");
//        }
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_save:
//                updateDatabase();
//                return true;
//            case R.id.menu_delete:
//                if (task_id != -1) {
//                    ConfirmDeleteDialog confirmDialog = new ConfirmDeleteDialog();
//                    confirmDialog.show(getSupportFragmentManager(), "deletionConfirmation");
//                }
//                else {
//                    finish();
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void updateDatabase() {
//        Tasks task = new Tasks(task_id == -1?0:task_id,
//                ((EditText) findViewById(R.id.taskNameEditText)).getText().toString(),
//                ((EditText) findViewById(R.id.dueDateEditText)).getText().toString(),
//                ((EditText) findViewById(R.id.descriptionEditText)).getText().toString(),1,//jus for now
//                isCompleted);
//                //((EditText) Integer.parseInt( findViewById(R.id.priority_of_card)).getText().toString));
//        if (task_id == -1) {
//            TasksDatabase.insert(task);
//        } else {
//            TasksDatabase.update(task);
//        }
//        finish(); // Quit activity
//
//    }
//    public void deleteRecord() {
//        TasksDatabase.delete(task_id);
//    }
//
//    public static class ConfirmDeleteDialog extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//            builder.setTitle("Delete the task?")
//                    .setMessage("You will not be able to undo the deletion!")
//                    .setPositiveButton("Delete",
//                            (dialog,id) -> {
//                                ((AddActivity) getActivity()).deleteRecord();
//                                getActivity().finish();
//                            })
//                    .setNegativeButton("Return to joke list",
//                            (dialog, id) -> getActivity().finish());
//            return builder.create();
//        }
//    }
//
//}
