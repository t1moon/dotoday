package com.inc.tim.dotoday.tasks;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;

import static android.transition.TransitionSet.ORDERING_TOGETHER;

/**
 * Created by Timur on 22-Sep-17.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskHolder> {

    private static ArrayList<Task> taskList;
    private static TaskFragment taskFragment;

    public RecyclerAdapter(ArrayList<Task> tasks, TaskFragment taskFragment) {
        taskList = tasks;
        this.taskFragment = taskFragment;
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
        private ImageView icon;
        private TextView icon_text;
        public RelativeLayout viewBackgroundDelete, viewBackgroundComplete, viewBackgroundRestore,
                viewBackgroundArchive, viewForeground;
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
            viewBackgroundArchive = (RelativeLayout) v.findViewById(R.id.view_background_archive);
            viewBackgroundComplete = (RelativeLayout) v.findViewById(R.id.view_background_complete);
            viewBackgroundRestore= (RelativeLayout) v.findViewById(R.id.view_background_restore);
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

 //Get access to or create instances to each fragment
//            TaskFragment fragmentOne = TaskFragment.newInstance();
//            TaskDetailFragment fragmentTwo = TaskDetailFragment.newInstance();
//// Check that the device is running lollipop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

// Defines enter transition for all fragment views
                Explode slideTransition = new Explode();
                slideTransition.setDuration(1000);
                fragment.setEnterTransition(slideTransition);

// Defines enter transition only for shared element
                ChangeBounds changeBoundsTransition = new ChangeBounds();
//                changeBoundsTransition.setDuration(1000);
//
                fragment.setSharedElementEnterTransition(changeBoundsTransition);
                fragment.setEnterTransition(slideTransition);
                    fragment.setAllowEnterTransitionOverlap(true);
                    fragment.setAllowReturnTransitionOverlap(true);



                ((TasksActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_base_content, fragment)
                        .addSharedElement(viewForeground, "Task")
                        .commit();


//                fragmentTwo.setSharedElementEnterTransition(new DetailsTransition());
//                fragmentTwo.setEnterTransition(new Explode());
//                taskFragment.setExitTransition(new Explode());
//                fragmentTwo.setSharedElementReturnTransition(new DetailsTransition());

//                TransitionSet tasks = new TransitionSet();
//                tasks.setOrdering(ORDERING_TOGETHER);
//                tasks.addTransition(new ChangeBounds()).
//                        addTransition(new ChangeTransform()).
//                        addTransition(new ChangeImageTransform());
//                fragmentTwo.setSharedElementEnterTransition(tasks);
//                fragmentTwo.setEnterTransition(new Fade());
//                fragmentOne.setExitTransition(new Fade());
//                fragmentTwo.setSharedElementReturnTransition(tasks);
                //ActivityUtils.addFragmentAnimation(((TasksActivity) v.getContext()).getSupportFragmentManager(), fragment, "TaskDetailFragment", viewForeground);
//                // Inflate transitions to apply
//                android.transition.Transition changeTransform = TransitionInflater.from(v.getContext()).
//                        inflateTransition(R.transition.change_item_transform);
//                android.transition.Transition explodeTransform = TransitionInflater.from(v.getContext()).
//                        inflateTransition(android.R.transition.explode);
////
//                // Setup exit transition on first fragment
//                fragmentOne.setSharedElementReturnTransition(changeTransform);
//                fragmentOne.setExitTransition(explodeTransform);
//
//                // Setup enter transition on second fragment
//                //fragmentTwo.setSharedElementEnterTransition(changeTransform);
//                fragmentTwo.setEnterTransition(explodeTransform);
//
//                ActivityUtils.addFragmentAnimation(((TasksActivity)v.getContext()).getSupportFragmentManager(), fragment, "TaskDetailFragment", viewForeground);
//            }
//            else {
//                ActivityUtils.addFragment(((TasksActivity)v.getContext()).getSupportFragmentManager(), fragment, "TaskDetailFragment");
//            }
            }

        }

    }
}
