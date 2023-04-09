package com.example.planifystudyapp.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.R;
import com.example.planifystudyapp.db.Tasks;

import java.util.List;


public class TaskListHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public TextView dueDate;
    public TextView description;
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
        cardView = itemView.findViewById(R.id.priorityCardView);
    }

}
