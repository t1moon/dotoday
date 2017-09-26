package com.inc.tim.dotoday.taskdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.TasksApplication;
import com.inc.tim.dotoday.addtask.AddTaskFragment;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TaskFragment;
import com.inc.tim.dotoday.tasks.TasksContract;
import com.inc.tim.dotoday.tasks.TasksPresenter;
import com.inc.tim.dotoday.util.ActivityUtils;

import java.util.List;

public class TaskDetailFragment extends Fragment implements DetailContract.View{
    private long taskId;
    private DetailContract.Presenter presenter;

    private EditText title_et;

    public TaskDetailFragment() {
        // Required empty public constructor
    }
    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenter(this, getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadTask(taskId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        taskId = getArguments().getLong("taskId");
        title_et = (EditText) view.findViewById(R.id.detail_title_et);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                task.setTitle(title_et.getText().toString());
                presenter.editTask(task, taskId);
            }
        });
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showTask(Task task) {
        title_et.setText(task.getTitle());
    }

    @Override
    public void notifyEdited() {
        if (getView() != null) {
            Snackbar.make(getView(), "Task edited", Snackbar.LENGTH_SHORT).show();
        }
        ActivityUtils.popFragment(getActivity().getSupportFragmentManager());
    }


}
