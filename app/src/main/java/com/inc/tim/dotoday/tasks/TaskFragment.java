package com.inc.tim.dotoday.tasks;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    int category = 0; // default 1st category

    public TaskFragment() {
        // Required empty public constructor
    }
    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TasksPresenter(this, getActivity().getApplicationContext());
    }

    @Override
    public void onResume(){
        super.onResume();
        ((TasksActivity) getActivity()).setBottomBarSelected(true); // access for further selection
        int category = ((TasksActivity) getActivity()).getCurrentCategory();
        bottomBar.selectTabAtPosition(category);                    // select
        presenter.loadCategoryTasks(category);
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

        category = ((TasksActivity)getActivity()).getCurrentCategory();
        adapter = new RecyclerAdapter(taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.SimpleCallback itemTouchHelperLeftCallback = new RecyclerItemTouchHelperLeft(
                0, ItemTouchHelper.LEFT, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperRightCallback = new RecyclerItemTouchHelperRight(
                0, ItemTouchHelper.RIGHT, this);

        new ItemTouchHelper(itemTouchHelperLeftCallback).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchHelperRightCallback).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.addFragment(getActivity().getSupportFragmentManager(), new AddTaskFragment(), "AddTaskFragment");
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
                    ToolbarUtils.changeToolbarColor(activity, position, toolbar);
                    ((TasksActivity) getActivity()).setCurrentCategory(position);
                    presenter.loadCategoryTasks(position);
                }
            }
        });
        setHasOptionsMenu(true);
        return view;
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
                    snackbar = Snackbar.make(getView(), getString(R.string.delete_undo), Snackbar.LENGTH_LONG);
                    presenter.setIsDeleted(taskId, true);
                }
            }
            else {
                // RESTORE
                if (hasAlreadyCompleted) {
                    snackbar = Snackbar.make(getView(), getString(R.string.restore_undo), Snackbar.LENGTH_LONG);
                    presenter.setIsCompleted(taskId, false);
                } else {
                    // COMPLETE
                    snackbar = Snackbar.make(getView(), getString(R.string.complete_undo), Snackbar.LENGTH_LONG);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.filter_active:
                presenter.setFiltering(TasksUtils.Filtering.ACTIVE);
                break;
            case R.id.filter_completed:
                presenter.setFiltering(TasksUtils.Filtering.COMPLETED);
                break;
            case R.id.filter_deleted:
                presenter.setFiltering(TasksUtils.Filtering.DELETED);
                break;
            case R.id.sort_date:
                presenter.setSorting(TasksUtils.Sorting.DATE);
                break;
            case R.id.sort_importance:
                presenter.setSorting(TasksUtils.Sorting.IMPORTANCE);
                break;
            default:
                presenter.setSorting(TasksUtils.Sorting.IMPORTANCE);
                break;
        }
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
