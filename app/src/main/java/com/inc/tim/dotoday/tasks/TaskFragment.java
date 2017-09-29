package com.inc.tim.dotoday.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.addtask.AddTaskFragment;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment implements TasksContract.View {
    private RecyclerAdapter adapter;
    private TasksContract.Presenter presenter;
    private ArrayList<Task> taskList = new ArrayList<>();
    RecyclerView recyclerView;

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
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.tasks_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.addFragment(getActivity().getSupportFragmentManager(), new AddTaskFragment());
            }
        });
        fab.setImageResource(R.drawable.ic_add_24dp);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showTasks(List<Task> tasks) {
        taskList.clear();
        for (Task t: tasks) {
            taskList.add(t);
        }
        adapter.notifyDataSetChanged();
    }
}
