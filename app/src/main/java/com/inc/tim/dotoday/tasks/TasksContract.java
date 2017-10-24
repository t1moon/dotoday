package com.inc.tim.dotoday.tasks;

import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.util.TasksUtils;

import java.util.List;


public interface TasksContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void showTasks(List<Task> tasks);
        void showEmpty();

    }

    /* User actions */
    interface Presenter{
        void loadCategoryTasks(int category);
        void setIsCompleted(long taskId, boolean is_completed);
        boolean getIsCompleted(long taskId);
        void setIsDeleted(long taskId, boolean is_deleted);
        void deleteCompletely(long taskId);
        boolean getIsDeleted(long taskId);
        void setSorting(TasksUtils.Sorting sorting);
        void setFiltering(TasksUtils.Filtering filtering);
        TasksUtils.Filtering getFiltering();
        void setCategory(int category);
        int getCategory();
    }

}
