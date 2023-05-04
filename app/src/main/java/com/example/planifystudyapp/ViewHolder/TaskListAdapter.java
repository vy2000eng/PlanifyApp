package com.example.planifystudyapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planifystudyapp.R;
import com.example.planifystudyapp.db.Tasks;

import java.util.List;
import java.util.Objects;

public class TaskListAdapter extends RecyclerView.Adapter <TaskListHolder> {
    public final LayoutInflater layoutInflater;
    private Context mContext;

    private List<Tasks> tasks; // Cached copy of jokes
    private int colorResId;
    private String mCardScheme;
    private boolean mIsInverted;

    public TaskListAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new TaskListHolder(itemView);
    }

    //    public void setIsInverted(boolean isInverted) {
//        mIsInverted = isInverted;
//    }
    public void setLightOrDark(String cardScheme) {
        mCardScheme = cardScheme;
    }

    @Override
    public void onBindViewHolder(TaskListHolder holder, int position) {
        if (tasks != null) {
            Tasks current = tasks.get(position);

            holder.task = current;

            //String whatPriority = current.priority == 0?"Low Priority"


            holder.titleView.setText(current.title);
            holder.dueDate.setText(current.date);
            holder.description.setText(current.description);
            //holder.priority.setText();
            if (Objects.equals(mCardScheme, "Light")) {
                holder.titleView.setTextColor(Color.DKGRAY);
                holder.dueDate.setTextColor(Color.DKGRAY);
                holder.description.setTextColor(Color.DKGRAY);
                holder.priorityTv.setTextColor(Color.DKGRAY);


            } else if (Objects.equals(mCardScheme, "Dark")) {
                holder.titleView.setTextColor(Color.LTGRAY);
                holder.dueDate.setTextColor(Color.LTGRAY);
                holder.description.setTextColor(Color.LTGRAY);
                holder.priorityTv.setTextColor(Color.LTGRAY);
            }

            colorResId = Objects.equals(mCardScheme, "Light") ? current.priority == 0
                    ? R.color.colorLP
                    : current.priority == 1 ? R.color.colorMP
                    : current.priority == 2 ? R.color.colorHP : null
                    : Objects.equals(mCardScheme, "Dark")
                    ? current.priority == 0 ? R.color.colorLPDark
                    : current.priority == 1 ? R.color.colorMPDark
                    : current.priority == 2 ? R.color.colorHPDark : null : current.priority == 0
                    ? R.color.colorLPDarkInverted
                    : current.priority == 1 ? R.color.colorMPDarkInverted
                    : current.priority == 2 ? R.color.colorHPDarkInverted : null;

            holder.priorityTv.setText(
                    current.priority == 0 ? "Low Priority" :
                            current.priority == 1 ? "Medium Priority" :
                                    current.priority == 2 ? "High Priority" : "");

            int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
            holder.cardView.setCardBackgroundColor(color);

            if (current.isCompleted) {
                holder.isCompleted.setImageResource(R.drawable.check_mark);
            } else {
                holder.isCompleted.setImageResource(R.drawable.assignment_icon);
            }
            if (Objects.equals(mCardScheme, "Dark")) {
                holder.isCompleted.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorTextSecondary), PorterDuff.Mode.SRC_IN);
            }

        } else {
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

    public void setTasks(List<Tasks> tasks) {
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
