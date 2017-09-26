package com.inc.tim.dotoday.addtask;

import com.inc.tim.dotoday.data.Task;

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
        void saveTask(Task task);
    }

    /* Work with data */
    interface Repository {
        void saveTask(Task task);
    }
}
