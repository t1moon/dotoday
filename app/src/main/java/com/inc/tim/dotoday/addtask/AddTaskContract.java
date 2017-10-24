package com.inc.tim.dotoday.addtask;

import java.util.Date;

public interface AddTaskContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void notifyAdded();

    }

    /* User actions */
    interface Presenter {
        void saveTask(String title, String description, int importance, int category, Date created_at);
    }

}
