package com.inc.tim.dotoday.tasks;

import android.content.Context;

import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.data.Task;

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
    public void loadTasks() {
        List<Task> tasks = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().loadAll();
        if (!tasks.isEmpty()) {
            view.showTasks(tasks);
        }
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void start() {
        loadTasks();
    }
}
