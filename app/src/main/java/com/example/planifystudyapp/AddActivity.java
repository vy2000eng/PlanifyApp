//package com.example.planifystudyapp;
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