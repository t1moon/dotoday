package com.inc.tim.dotoday.tasks;

import android.content.Context;

import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.data.TaskDao;
import com.inc.tim.dotoday.util.TasksUtils;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

public class TasksPresenter implements TasksContract.Presenter {
    private final TasksContract.View view;
    private final Context context;
    private TasksUtils.Sorting currentSorting = TasksUtils.Sorting.IMPORTANCE;
    private TasksUtils.Filtering currentFiltering = TasksUtils.Filtering.ACTIVE;
    private int currentCategory = 0;

    public TasksPresenter(TasksContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void loadCategoryTasks(int category) {
        Property sorting = TaskDao.Properties.Importance;
        switch (currentSorting) {
            case DATE:
                sorting = TaskDao.Properties.Created;
                break;
            case IMPORTANCE:
                sorting = TaskDao.Properties.Importance;
                break;
        }
        boolean is_deleted = false;
        boolean is_completed = false;

        switch (currentFiltering) {
            case COMPLETED:
                is_completed = true;
                break;
            case DELETED:
                is_deleted = true;
                break;
        }

        List<Task> tasks = ((TasksApplication) context.getApplicationContext())
                .getDaoSession().getTaskDao().queryBuilder()
                .where(TaskDao.Properties.Is_deleted.eq(is_deleted),
                        TaskDao.Properties.Is_completed.eq(is_completed),
                        TaskDao.Properties.Category.eq(category))
                .orderDesc(sorting).list();
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
    public boolean getIsCompleted(long taskId) {
        return ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(taskId).getIs_completed();
    }

    @Override
    public void setIsDeleted(long taskId, boolean is_deleted) {
        Task dbTask = ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(taskId);
        dbTask.setIs_deleted(is_deleted);
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().update(dbTask);
    }

    @Override
    public void deleteCompletely(long taskId) {
        ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().deleteByKey(taskId);
    }

    @Override
    public boolean getIsDeleted(long taskId) {
        return ((TasksApplication) context.getApplicationContext()).getDaoSession().getTaskDao().load(taskId).getIs_deleted();
    }

    @Override
    public void setSorting(TasksUtils.Sorting sorting) {
        currentSorting = sorting;
    }

    @Override
    public void setFiltering(TasksUtils.Filtering filtering) {
        currentFiltering = filtering;
    }

    @Override
    public TasksUtils.Filtering getFiltering() {
        return currentFiltering;
    }

    @Override
    public void setCategory(int category) {
        currentCategory = category;
    }

    @Override
    public int getCategory() {
        return currentCategory;
    }
}
