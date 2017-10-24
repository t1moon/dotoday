package com.inc.tim.dotoday.tasks;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.addtask.AddTaskFragment;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.CommonUtils;
import com.inc.tim.dotoday.util.DividerItemDecoration;
import com.inc.tim.dotoday.util.RecyclerItemTouchHelperLeft;
import com.inc.tim.dotoday.util.RecyclerItemTouchHelperRight;
import com.inc.tim.dotoday.util.TasksUtils;
import com.inc.tim.dotoday.util.ToolbarUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment implements TasksContract.View,
        RecyclerItemTouchHelperLeft.RecyclerItemTouchHelperListener,
        RecyclerItemTouchHelperRight.RecyclerItemTouchHelperListener {
    private RecyclerAdapter adapter;
    private TasksContract.Presenter presenter;
    private ArrayList<Task> taskList = new ArrayList<>();
    RecyclerView recyclerView;
    BottomBar bottomBar;
    TextView no_task_tv;

    public TaskFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TasksPresenter(this, getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        no_task_tv = (TextView) view.findViewById(R.id.no_task_tv);
        recyclerView = (RecyclerView) view.findViewById(R.id.tasks_list);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Init swipe
        ItemTouchHelper.SimpleCallback itemTouchHelperLeftCallback = new RecyclerItemTouchHelperLeft(
                0, ItemTouchHelper.LEFT, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperRightCallback = new RecyclerItemTouchHelperRight(
                0, ItemTouchHelper.RIGHT, this);

        new ItemTouchHelper(itemTouchHelperLeftCallback).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchHelperRightCallback).attachToRecyclerView(recyclerView);

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change fragment with animation
                AddTaskFragment fragment = new AddTaskFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explodeTransition = new Explode();
                    explodeTransition.setDuration(1000);
                    fragment.setEnterTransition(explodeTransition);
                    Fade fadeTransition = new Fade();
                    fadeTransition.setDuration(10);
                    fragment.setReturnTransition(fadeTransition);
                }
                ActivityUtils.addFragment(getActivity().getSupportFragmentManager(), fragment, "AddTaskFragment");
            }
        });
        fab.setImageResource(R.drawable.ic_add_24dp);

        bottomBar = (BottomBar) getActivity().findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                TasksActivity activity = (TasksActivity) getActivity();

                if (activity != null && activity.isBottomBarSelected()) {
                    Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                    int position = 0;
                    switch (tabId) {
                        case R.id.tab_focus:
                            position = 0;
                            break;
                        case R.id.tab_goal:
                            position = 1;
                            break;
                        case R.id.tab_delegate:
                            position = 2;
                            break;
                        case R.id.tab_throw:
                            position = 3;
                            break;
                    }
                    fab.setBackgroundTintList(ColorStateList.valueOf(CommonUtils.ColorUtil.MATERIAL_COLORS[position]));
                    ToolbarUtils.changeToolbarColor(activity, position, toolbar);
                    ((TasksActivity) getActivity()).setCurrentCategory(position);
                    presenter.loadCategoryTasks(position);
                }
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        // SET FILTERING TITLE
        if (!((TasksActivity) getActivity()).isBottomBarSelected())
            presenter.setFiltering(TasksUtils.Filtering.ACTIVE);
        setToolbarTitle();
        ((TasksActivity) getActivity()).setBottomBarSelected(true); // access for further selection
        int category = ((TasksActivity) getActivity()).getCurrentCategory();
        bottomBar.selectTabAtPosition(category);// select in right category
        presenter.loadCategoryTasks(category);
    }

    private void setToolbarTitle() {
        switch (presenter.getFiltering()) {
            case ACTIVE:
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_active);
                break;
            case COMPLETED:
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_completed);
                break;
            case DELETED:
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_deleted);
                break;
        }
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, final int direction, int position) {
        if (viewHolder instanceof RecyclerAdapter.TaskHolder) {
            // backup of removed item for undo purpose
            final Task deletedItem = taskList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = null;

            final long taskId  = deletedItem.getId();
            final boolean hasAlreadyCompleted = presenter.getIsCompleted(taskId);
            final boolean hasAlreadyDeleted = presenter.getIsDeleted(taskId);

            if (direction == ItemTouchHelper.LEFT){
                // DELETE COMPLETELY
                if (hasAlreadyDeleted || hasAlreadyCompleted) {
                    presenter.deleteCompletely(taskId);
                } else {
                    // DELETE
                    snackbar = Snackbar.make(getView(), getString(R.string.delete_undo), Snackbar.LENGTH_SHORT);
                    presenter.setIsDeleted(taskId, true);
                }
            }
            else {
                // RESTORE
                if (hasAlreadyCompleted) {
                    snackbar = Snackbar.make(getView(), getString(R.string.restore_undo), Snackbar.LENGTH_SHORT);
                    presenter.setIsCompleted(taskId, false);
                } else {
                    // COMPLETE
                    snackbar = Snackbar.make(getView(), getString(R.string.complete_undo), Snackbar.LENGTH_SHORT);
                    presenter.setIsCompleted(taskId, true);
                }
            }

            // UNDO
            if (snackbar != null) {
                snackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item
                        adapter.restoreItem(deletedItem, deletedIndex);
                        if (direction == ItemTouchHelper.LEFT) {
                            // ONLY FROM ACTIVE
                            presenter.setIsDeleted(taskId, false);
                        }
                        else {
                            if (hasAlreadyCompleted)
                                // FROM COMPLETED
                                presenter.setIsCompleted(taskId, true);
                            else
                                presenter.setIsCompleted(taskId, false);
                        }
                    }
                });
                snackbar.setActionTextColor(Color.parseColor("#FFC107"));
                snackbar.show();
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showTasks(List<Task> tasks) {
        taskList.clear();
        no_task_tv.setVisibility(View.GONE);
        for (Task t: tasks) {
            taskList.add(t);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        taskList.clear();
        no_task_tv.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.filter_active:
                presenter.setFiltering(TasksUtils.Filtering.ACTIVE);
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_active);
                break;
            case R.id.filter_completed:
                presenter.setFiltering(TasksUtils.Filtering.COMPLETED);
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_completed);
                break;
            case R.id.filter_deleted:
                presenter.setFiltering(TasksUtils.Filtering.DELETED);
                ((TextView) (getActivity().findViewById(R.id.toolbar_title))).setText(R.string.filter_deleted);
                break;
            case R.id.sort_date:
                presenter.setSorting(TasksUtils.Sorting.DATE);
                break;
            case R.id.sort_importance:
                presenter.setSorting(TasksUtils.Sorting.IMPORTANCE);
                break;
        }
        int category = ((TasksActivity) getActivity()).getCurrentCategory();
        presenter.loadCategoryTasks(category);
        return super.onOptionsItemSelected(item);
    }

    public TasksContract.Presenter getPresenter() {
        return presenter;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}
