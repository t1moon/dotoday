package com.inc.tim.dotoday.addtask;

import android.content.Context;

import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.taskdetail.DetailContract;

public class AddTaskPresenter implements AddTaskContract.Presenter{
    private final AddTaskContract.View view;
    private final Context context;


    public AddTaskPresenter(AddTaskContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void saveTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().insert(task);
        view.notifyAdded();
    }
}
