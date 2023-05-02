package com.example.planifystudyapp.ViewHolder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.AddActivity;
import com.example.planifystudyapp.R;
import com.example.planifystudyapp.db.Tasks;
import com.example.planifystudyapp.db.TasksDatabase;

import java.util.List;


public class TaskListHolder extends RecyclerView.ViewHolder{
    public TextView titleView;
    public TextView dueDate;
    public TextView description;
    public TextView priorityTv;
    public final ImageView isCompleted;
    public CardView cardView;

    public Tasks task;
   // private List<Tasks> tasks; // cached copy of tasks
    //private final LayoutInflater layoutInflater;
    public TaskListHolder( View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.TitleTextView);
        dueDate = itemView.findViewById(R.id.dueDateTextView);
        description = itemView.findViewById(R.id.DescriptionTextView);
        isCompleted = itemView.findViewById(R.id.isCompletedImageView);
        priorityTv = itemView.findViewById(R.id.priorityTextView);


        cardView = itemView.findViewById(R.id.priorityCardView);

        isCompleted.setOnClickListener(view -> {
            //task.isCompleted= !task.isCompleted;
            if(!task.isCompleted)
                task.isCompleted = true;
            TasksDatabase.update(task);
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

}
