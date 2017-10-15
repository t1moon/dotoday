package com.inc.tim.dotoday.tasks;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.taskdetail.TaskDetailFragment;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Timur on 22-Sep-17.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskHolder> {

    private static ArrayList<Task> taskList;

    public RecyclerAdapter(ArrayList<Task> tasks) {
        taskList = tasks;
    }

    @Override
    public RecyclerAdapter.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_v2, parent, false);
        return new TaskHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.TaskHolder holder, int position) {
        Task itemTask = taskList.get(position);
        holder.bindTask(itemTask);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public void removeItem(int position) {
        taskList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Task task, int position) {
        taskList.add(position, task);
        // notify item added by position
        notifyItemInserted(position);
    }

    public static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView description;
        private ImageView icon;
        private TextView icon_text;
        public RelativeLayout viewBackgroundDelete, viewBackgroundComplete, viewForeground;
        public TaskHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.row_title);
            description = (TextView) v.findViewById(R.id.row_description);
            icon = (ImageView) v.findViewById(R.id.task_icon);
            icon_text = (TextView) v.findViewById(R.id.task_icon_text);

            title.setOnClickListener(this);
            description.setOnClickListener(this);
            icon.setOnClickListener(this);
            icon_text.setOnClickListener(this);

            viewBackgroundDelete = (RelativeLayout) v.findViewById(R.id.view_background_delete);
            viewBackgroundComplete = (RelativeLayout) v.findViewById(R.id.view_background_complete);
            viewForeground = (RelativeLayout) v.findViewById(R.id.view_foreground);
            viewForeground.setOnClickListener(this);
        }

        public void bindTask(Task task) {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            // Set importance background
            GradientDrawable bgShape = (GradientDrawable)icon.getBackground();
            bgShape.setColor(CommonUtils.ColorUtil.getImportanceColor(task.getImportance(),task.getCategory()));
            icon_text.setText(task.getTitle().substring(0, 1).toUpperCase());
        }
        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putLong("taskId", taskList.get(getAdapterPosition()).getId());
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(args);
            ActivityUtils.addFragment(((TasksActivity)v.getContext()).getSupportFragmentManager(), fragment, "TaskDetailFragment");
        }

    }
}
