package com.inc.tim.dotoday.addtask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TasksContract;
import com.inc.tim.dotoday.tasks.TasksPresenter;

import java.util.List;

public class AddTaskFragment extends Fragment implements TasksContract.View {
    private TasksContract.Presenter presenter;

    public AddTaskFragment() {
        // Required empty public constructor
    }
    public static AddTaskFragment newInstance() {
        return new AddTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TasksPresenter(this, getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showTasks(List<Task> tasks) {

    }
}
