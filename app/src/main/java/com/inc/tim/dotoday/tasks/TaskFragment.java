package com.inc.tim.dotoday.tasks;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.addtask.AddTaskFragment;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.DividerItemDecoration;
import com.inc.tim.dotoday.util.RecyclerItemTouchHelperLeft;
import com.inc.tim.dotoday.util.RecyclerItemTouchHelperRight;
import com.inc.tim.dotoday.util.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

import static com.inc.tim.dotoday.R.id.spinner_nav;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS_LIGHT;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.STATUSBAR_MATERIAL_COLORS;

public class TaskFragment extends Fragment implements TasksContract.View,
        RecyclerItemTouchHelperLeft.RecyclerItemTouchHelperListener,
        RecyclerItemTouchHelperRight.RecyclerItemTouchHelperListener {
    private RecyclerAdapter adapter;
    private TasksContract.Presenter presenter;
    private ArrayList<Task> taskList = new ArrayList<>();
    RecyclerView recyclerView;
    Spinner spinner;
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
    public void onResume() {
        super.onResume();
        category = ((TasksActivity) getActivity()).getCreatedCategory();
        spinner.setSelection(category);
        presenter.loadCategoryTasks(category);
    }

    @Override
    public void onPause() {
        super.onPause();
        spinner.setVisibility(Spinner.GONE);
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

        spinner = (Spinner) getActivity().findViewById(spinner_nav);
        category = ((TasksActivity)getActivity()).getCreatedCategory();
        spinner.setSelection(category);
        spinner.setVisibility(Spinner.VISIBLE);

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

        addItemsToSpinner();
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

            Snackbar snackbar;

            final long taskId  = deletedItem.getId();
            if (direction == ItemTouchHelper.LEFT){
                snackbar = Snackbar.make(getView(), getString(R.string.delete_undo), Snackbar.LENGTH_LONG);
                presenter.setIsDeleted(taskId, true);
            }
            else {
                snackbar = Snackbar.make(getView(), getString(R.string.complete_undo), Snackbar.LENGTH_LONG);
                presenter.setIsCompleted(taskId, true);
            }
            snackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    if (direction == ItemTouchHelper.LEFT) {
                        presenter.setIsDeleted(taskId, false);
                    }
                    else {
                        presenter.setIsCompleted(taskId, false);
                    }
                }
            });
            snackbar.setActionTextColor(Color.parseColor("#FFC107"));
            snackbar.show();
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

    private void addItemsToSpinner() {

        final String[] categories = getResources().getStringArray(R.array.categories_array);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_dropdown, categories);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appbar_layout);
                Integer colorFrom = MATERIAL_COLORS[0];
                Integer colorTo = MATERIAL_COLORS[1];
                Integer colorStatusFrom = STATUSBAR_MATERIAL_COLORS[2];
                Integer colorStatusTo = STATUSBAR_MATERIAL_COLORS[3];
                ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                ValueAnimator colorStatusAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStatusFrom, colorStatusTo);

                final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                });
                colorStatusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
                        }
//                        toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                });

                ((TasksActivity)getActivity()).setCreatedCategory(position);
                colorAnimator.setDuration(800);
                colorAnimator.setStartDelay(0);
                colorAnimator.start();
                colorStatusAnimator.setDuration(800);
                colorStatusAnimator.setStartDelay(0);
                colorStatusAnimator.start();
                ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[category]);
                spinner.setPopupBackgroundDrawable(toolbarColor);
//                ToolbarUtils.setToolbarColor((AppCompatActivity) getActivity(),
//                        ((TasksActivity) getActivity()).getSupportActionBar(), appBarLayout);


                presenter.loadCategoryTasks(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
