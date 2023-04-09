package com.example.planifystudyapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.R;
import com.example.planifystudyapp.db.Tasks;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter <TaskListHolder> {
    public final LayoutInflater layoutInflater;

    private List<Tasks> tasks; // Cached copy of jokes

    public TaskListAdapter(Context context){

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new TaskListHolder(itemView);
    }

    @Override
    public void onBindViewHolder( TaskListHolder holder, int position) {
        if(tasks != null){
            Tasks current = tasks.get(position);
            holder.task = current;
            holder.titleView.setText(current.title);
            holder.dueDate.setText(current.date);
            holder.description.setText(current.description);
            int colorResId = current.priority == 0?R.color.colorLPDark
                    :current.priority == 1?R.color.colorMPDark
                    :current.priority ==2?R.color.colorHPDark:null;
            int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
            holder.cardView.setCardBackgroundColor(color);



            if(current.isCompleted)
                holder.isCompleted.setImageResource(R.drawable.check_mark);
            else
                holder.isCompleted.setImageResource(R.drawable.assignment_icon);
        }else{
            holder.titleView.setText(R.string.init);
            holder.isCompleted.setImageResource(R.drawable.assignment_icon);
            holder.dueDate.setText(R.string.init);
            holder.description.setText(R.string.init);
        }
    }

    @Override
    public int getItemCount() {
        if (tasks != null)
            return tasks.size();
        else return 0;
    }
    public void setTasks(List<Tasks> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}







//    public void bind(TaskListHolder holder, int position){
//        if(task != null){
//            Tasks current = tasks.get(position);
//            holder.task = current;
//            holder.titleView.setText(current.title);
//            holder.dueDate.setText(current.date);
//            holder.description.setText(current.description);
//            if(current.isCompleted)
//                holder.isCompleted.setImageResource(R.drawable.check_mark);
//            else
//                holder.isCompleted.setImageResource(R.drawable.assignment_icon);
//        }else{
//            holder.titleView.setText("....INITIALIZING....");
//            holder.isCompleted.setImageResource(R.drawable.assignment_icon);
//            holder.dueDate.setText("....INITIALIZING....");
//            holder.description.setText("....INITIALIZING....");
//        }
//
//    }
//
//    static TaskListHolder create(ViewGroup parent, int position) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
//        return new TaskListHolder(itemView);
//    }
