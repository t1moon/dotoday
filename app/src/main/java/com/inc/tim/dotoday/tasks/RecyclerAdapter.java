package com.inc.tim.dotoday.tasks;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView description;
        private ImageView icon;
        private TextView icon_text;

        public TaskHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.row_title);
            description = (TextView) v.findViewById(R.id.row_description);
            icon = (ImageView) v.findViewById(R.id.task_icon);
            icon_text = (TextView) v.findViewById(R.id.task_icon_text);
            v.setOnClickListener(this);
        }

        public void bindTask(Task task) {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            // Set importance background
            GradientDrawable bgShape = (GradientDrawable)icon.getBackground();
            //bgShape.setColor(CommonUtils.ColorUtil.getImportanceColor(task.getImportance()));
            bgShape.setColor(CommonUtils.ColorUtil.MATERIAL_COLORS[task.getCategory()]);
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
