package com.inc.tim.dotoday.util;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.RecyclerAdapter;
import com.inc.tim.dotoday.tasks.TaskFragment;

import java.util.ArrayList;

/**
 * Created by Timur on 04-Oct-17.
 */

public class RecyclerItemTouchHelperLeft extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelperLeft(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((RecyclerAdapter.TaskHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);

            ArrayList<Task> taskList = ((TaskFragment)listener).getTaskList();
            long taskId = taskList.get(viewHolder.getAdapterPosition()).getId();
            boolean hasAlreadyArchived = ((TaskFragment)listener).getPresenter().getIsDeleted(taskId);
            boolean hasAlreadyCompleted = ((TaskFragment)listener).getPresenter().getIsCompleted(taskId);
            /* change background depend on action */

            if (hasAlreadyArchived || hasAlreadyCompleted) {
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundDelete.setVisibility(View.VISIBLE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundDelete.invalidate();

                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundComplete.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundComplete.invalidate();
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundRestore.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundRestore.invalidate();
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundArchive.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundArchive.invalidate();
            } else {
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundArchive.setVisibility(View.VISIBLE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundArchive.invalidate();

                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundDelete.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundDelete.invalidate();
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundComplete.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundComplete.invalidate();
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundRestore.setVisibility(View.GONE);
                ((RecyclerAdapter.TaskHolder) viewHolder).viewBackgroundRestore.invalidate();
            }

        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerAdapter.TaskHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((RecyclerAdapter.TaskHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerAdapter.TaskHolder) viewHolder).viewForeground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
