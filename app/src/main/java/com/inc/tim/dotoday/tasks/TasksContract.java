package com.inc.tim.dotoday.tasks;

import com.inc.tim.dotoday.commons.BasePresenter;
import com.inc.tim.dotoday.commons.BaseView;
import com.inc.tim.dotoday.data.Task;

import java.util.List;

/**
 * Created by Timur on 21-Sep-17.
 */

public interface TasksContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void showTasks(List<Task> tasks);

    }

    /* User actions */
    interface Presenter extends BasePresenter {
        void loadTasks();
        void addTask(Task task);
    }

    /* Work with data */
    interface Repository {
        void addTask(Task task);
    }
}
