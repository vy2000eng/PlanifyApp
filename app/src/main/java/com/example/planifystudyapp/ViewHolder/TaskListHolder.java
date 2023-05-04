package com.example.planifystudyapp.ViewHolder;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.AddActivity;
import com.example.planifystudyapp.MainActivity;
import com.example.planifystudyapp.R;
import com.example.planifystudyapp.db.Tasks;
import com.example.planifystudyapp.db.TasksDatabase;


public class TaskListHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public TextView dueDate;
    public TextView description;
    public TextView priorityTv;
    public final ImageView isCompleted;
    public CardView cardView;

    public Tasks task;
   // private List<Tasks> tasks; // cached copy of tasks
    //private final LayoutInflater layoutInflater;
    public TaskListHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.TitleTextView);
        dueDate = itemView.findViewById(R.id.dueDateTextView);
        description = itemView.findViewById(R.id.DescriptionTextView);
        isCompleted = itemView.findViewById(R.id.isCompletedImageView);
        priorityTv = itemView.findViewById(R.id.priorityTextView);


        cardView = itemView.findViewById(R.id.priorityCardView);
        //this.fragmentManager = fragmentManager;

        isCompleted.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Confirm Completion")
                    .setMessage(task.isCompleted ? "Are you sure you want to mark this task as incomplete?" : "Are you sure you want to mark this task as completed?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        task.isCompleted = !task.isCompleted;
                        TasksDatabase.update(task);
                        if(task.isCompleted){
                            Toast.makeText(itemView.getContext(), "The task is now in the Completed Tasks", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(itemView.getContext(), "The task is now in the todo_list Tasks", Toast.LENGTH_LONG).show();


                        }
                    })
                    .setNegativeButton("Return to task list", (dialog, which) -> {
                        dialog.dismiss(); // dismiss the dialog
                        // finish the activity to go back to the previous one
                        //((Activity)itemView.getContext()).finish();
                    })
                    .create()
                    .show();

//            task.isCompleted= !task.isCompleted;
//            if(!task.isCompleted)
//                task.isCompleted = true;
//            TasksDatabase.update(task);
//            ConfirmCompleteDialog completeDialog = new ConfirmCompleteDialog();
//            completeDialog.show(completeDialog.getParentFragmentManager(), "complete dialog");
        });
        itemView.setOnLongClickListener(view -> {
            // Note that we need a reference to the MainActivity instance
            Intent intent = new Intent(itemView.getContext() , AddActivity.class);
            // Note getItemId will return the database identifier
            intent.putExtra("task_id", task.id);
            // Note that we are calling a method of the MainActivity object
            itemView.getContext().startActivity(intent);

            return true;
        });




    }


//    public class ConfirmCompleteDialog extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
//
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//
//            builder.setTitle("Are you finished with this task?")
//                    .setMessage("this task will be put in completed tasks!")
//                    .setPositiveButton("Complete",
//                            (dialog,id) -> {
//                                task.isCompleted= !task.isCompleted;
//                                if(!task.isCompleted)
//                                    task.isCompleted = true;
//                                TasksDatabase.update(task);
//                               // TasksDatabase.update(task);
//
//                                //((AddActivity) getActivity()).updateTask();
//                                getActivity().finish();
//                            })
//                    .setNegativeButton("Return to task list",
//                            (dialog, id) -> getActivity().finish());
//            return builder.create();
//        }
//    }

}
