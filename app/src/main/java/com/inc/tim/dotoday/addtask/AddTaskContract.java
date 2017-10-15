package com.inc.tim.dotoday.addtask;

import com.inc.tim.dotoday.data.Task;

import java.util.Date;

/**
 * Created by Timur on 21-Sep-17.
 */

public interface AddTaskContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void notifyAdded();

    }

    /* User actions */
    interface Presenter {
        void saveTask(String title, String description, int importance, int category, Date created_at);
    }

    /* Work with data */
    interface Repository {
        void saveTask(Task task);
    }
}
