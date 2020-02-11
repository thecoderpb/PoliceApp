package com.ksp.donut.uca.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksp.donut.uca.R;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String TAG = TaskAdapter.class.getSimpleName() + "Logs";

    private LayoutInflater mInflater;
    private Context mContext;
    private boolean isUserSP;

    private List<TaskDetails> mDMCards;

    TaskAdapter(Context ct, List<TaskDetails> tasksList,boolean isUserSP) {
        mContext = ct;
        mInflater = LayoutInflater.from(ct);
        mDMCards = tasksList;
        this.isUserSP = isUserSP;
    }

    void setCards(List<TaskDetails> eventCards)
    {
        mDMCards = eventCards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView;
        if(isUserSP)
             mItemView = mInflater.inflate(R.layout.task_rv_sp, parent, false);
        else
             mItemView = mInflater.inflate(R.layout.task_rv, parent, false);

        return new TaskViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {
        if (mDMCards != null) {
            final TaskDetails current = mDMCards.get(position);

            holder.taskName.setText(current.getTaskName());
            holder.deadLine.setText(current.getTaskDeadline());

            if(isUserSP){

                holder.assignedTask.setText("Assigned task to : " + current.getAssignedToName());
            }


        }
    }

    @Override
    public int getItemCount() {
        return mDMCards == null ? 0 : mDMCards.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView deadLine;
        TextView assignedTask;


        TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name_rv);
            deadLine = itemView.findViewById(R.id.task_deadline_rv);

            if(isUserSP){
                assignedTask = itemView.findViewById(R.id.assigned_to_rv);
            }

        }

    }

}
