package com.inc.tim.dotoday.taskdetail;

import com.inc.tim.dotoday.commons.BasePresenter;
import com.inc.tim.dotoday.data.Task;

import java.util.List;

/**
 * Created by Timur on 21-Sep-17.
 */

public interface DetailContract {

    /* UI communication. It's what activity or fragment will implement */
    interface View {
        void showTask(Task task);

    }

    /* User actions */
    interface Presenter {
        void loadTask(long id);
        void saveTask(Task task);
    }

    /* Work with data */
    interface Repository {
        void saveTask(Task task);
    }
}
