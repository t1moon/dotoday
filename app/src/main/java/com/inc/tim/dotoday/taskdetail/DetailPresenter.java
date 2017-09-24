package com.inc.tim.dotoday.taskdetail;

import android.content.Context;

import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TasksContract;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenter implements DetailContract.Presenter{
    private final DetailContract.View view;
    private final Context context;


    public DetailPresenter(DetailContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadTask(long id) {
        Task task = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(id);
        if (task != null) {
            view.showTask(task);
        }
    }

    @Override
    public void editTask(Task editedTask, long id) {
        Task dbTask = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(id);
        dbTask.setTitle(editedTask.getTitle());
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().update(dbTask);
        view.notifyEdited();
    }
}
