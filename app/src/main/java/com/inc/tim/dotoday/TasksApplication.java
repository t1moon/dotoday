package com.inc.tim.dotoday;

import android.app.Application;

import com.inc.tim.dotoday.data.DaoMaster;
import com.inc.tim.dotoday.data.DaoSession;
import com.inc.tim.dotoday.data.DbOpenHelper;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.data.TaskDao;

import java.util.Calendar;

/**
 * Created by Timur on 22-Sep-17.
 */

public class TasksApplication extends Application {

    private static TasksApplication INSTANCE;
    private DaoSession daoSession;


    public static synchronized TasksApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        daoSession = new DaoMaster(new DbOpenHelper(this, "task_db.db").getWritableDb()).newSession();
        INSTANCE = this;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
