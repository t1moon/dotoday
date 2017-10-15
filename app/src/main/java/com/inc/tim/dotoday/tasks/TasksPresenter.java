package com.inc.tim.dotoday.tasks;

import android.content.Context;

import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.data.TaskDao;

import java.util.ArrayList;
import java.util.List;

public class TasksPresenter implements TasksContract.Presenter{
    private final TasksContract.View view;
    private final Context context;

    public TasksPresenter(TasksContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void loadCategoryTasks(int category) {
        List<Task> tasks = ((TasksApplication) context.getApplicationContext())
                .getDaoSession().getTaskDao().queryBuilder()
                .where(TaskDao.Properties.Is_deleted.eq(false),
                        TaskDao.Properties.Is_completed.eq(false),
                        TaskDao.Properties.Category.eq(category))
                .orderDesc(TaskDao.Properties.Created).list();
        if (!tasks.isEmpty()) {
            view.showTasks(tasks);
        } else {
            view.showEmpty();
        }
    }

    @Override
    public void setIsCompleted(long taskId, boolean is_completed) {
        Task dbTask = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(taskId);
        dbTask.setIs_completed(is_completed);
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().update(dbTask);
    }

    @Override
    public void setIsDeleted(long taskId, boolean is_deleted) {
        Task dbTask = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(taskId);
        dbTask.setIs_deleted(is_deleted);
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().update(dbTask);
    }
}
