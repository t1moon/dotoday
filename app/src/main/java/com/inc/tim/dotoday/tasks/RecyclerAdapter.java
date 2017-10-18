package com.inc.tim.dotoday.tasks;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.taskdetail.DetailsTransition;
import com.inc.tim.dotoday.taskdetail.TaskDetailFragment;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.transition.TransitionSet.ORDERING_TOGETHER;

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
        holder.bindTask(itemTask, holder);
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
        private TextView date;
        private ImageView icon;
        private TextView icon_text;
        Locale ruLocale = new Locale("ru","RU");
        public RelativeLayout viewBackgroundDelete, viewBackgroundComplete, viewBackgroundRestore,
                viewBackgroundArchive, viewForeground;

        public TaskHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.row_title);
            description = (TextView) v.findViewById(R.id.row_description);
            date = (TextView) v.findViewById(R.id.row_date);
            icon = (ImageView) v.findViewById(R.id.task_icon);
            icon_text = (TextView) v.findViewById(R.id.task_icon_text);

            title.setOnClickListener(this);
            description.setOnClickListener(this);
            date.setOnClickListener(this);
            icon.setOnClickListener(this);
            icon_text.setOnClickListener(this);

            viewBackgroundDelete = (RelativeLayout) v.findViewById(R.id.view_background_delete);
            viewBackgroundArchive = (RelativeLayout) v.findViewById(R.id.view_background_archive);
            viewBackgroundComplete = (RelativeLayout) v.findViewById(R.id.view_background_complete);
            viewBackgroundRestore = (RelativeLayout) v.findViewById(R.id.view_background_restore);
            viewForeground = (RelativeLayout) v.findViewById(R.id.view_foreground);
            viewForeground.setOnClickListener(this);
        }

        public void bindTask(Task task, RecyclerAdapter.TaskHolder holder) {
            title.setText(task.getTitle());
            if (task.getIs_completed()) {
                holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.title.setPaintFlags(holder.title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
            description.setText(task.getDescription());

            String oldDate = task.getCreated().toString();
            SimpleDateFormat src = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
            SimpleDateFormat dest = new SimpleDateFormat("EEE, d MMM", ruLocale);
            try {
                Date newDate = src.parse(oldDate);
                date.setText(dest.format(newDate));
                date.setTextColor(CommonUtils.ColorUtil.MATERIAL_COLORS[task.getCategory()]);
            } catch (ParseException e) {
                Log.d("Exception",e.getMessage());
            }

            // Set importance background
            GradientDrawable bgShape = (GradientDrawable) icon.getBackground();
            bgShape.setColor(CommonUtils.ColorUtil.getImportanceColor(task.getImportance(), task.getCategory()));
            icon_text.setText(task.getTitle().substring(0, 1).toUpperCase());
        }

        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putLong("taskId", taskList.get(getAdapterPosition()).getId());
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(args);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Explode explodeTransition = new Explode();
                explodeTransition.setDuration(1000);
                fragment.setEnterTransition(explodeTransition);
                Fade fadeTransition = new Fade();
                fadeTransition.setDuration(10);
                fragment.setReturnTransition(fadeTransition);

            }

            ActivityUtils.addFragment(((TasksActivity) v.getContext()).getSupportFragmentManager(), fragment, "TaskDetailFragment");

        }

    }
}
