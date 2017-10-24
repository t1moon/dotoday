package com.inc.tim.dotoday.taskdetail;

import com.inc.tim.dotoday.data.Task;


public interface DetailContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void showTask(Task task);
        void notifyEdited();
    }

    /* User actions */
    interface Presenter {
        void loadTask(long id);
        void editTask(String title, String description, int importance, int category, long id);
    }

}
