package com.inc.tim.dotoday.tasks;

import com.inc.tim.dotoday.data.Task;

import java.util.List;

/**
 * Created by Timur on 21-Sep-17.
 */

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
        void setIsDeleted(long taskId, boolean is_deleted);
    }

    /* Work with data */
    interface Repository {
        void addTask(Task task);
    }
}
