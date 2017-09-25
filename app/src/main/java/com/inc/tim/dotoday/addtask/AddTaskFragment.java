package com.inc.tim.dotoday.addtask;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TaskFragment;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.tasks.TasksContract;
import com.inc.tim.dotoday.tasks.TasksPresenter;
import com.inc.tim.dotoday.util.ActivityUtils;

import java.util.List;

public class AddTaskFragment extends Fragment implements AddTaskContract.View {
    private AddTaskContract.Presenter presenter;
    CoordinatorLayout.LayoutParams lp;
    FloatingActionButton fab;

    public AddTaskFragment() {
        // Required empty public constructor
    }
    public static AddTaskFragment newInstance() {
        return new AddTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddTaskPresenter(this, getActivity().getApplicationContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        lp.setAnchorId(R.id.toolbar_2);
        lp.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT | Gravity.END;
        fab.setLayoutParams(lp);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                EditText title = (EditText) view.findViewById(R.id.add_task_title_et);
                task.setTitle(title.getText().toString());
                presenter.saveTask(task);
            }
        });
        /* Hide my action bar */
        ((TasksActivity) getActivity()).getSupportActionBar().hide();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void notifyAdded() {
        if (getView() != null) {
            Snackbar.make(getView(), "Task created", Snackbar.LENGTH_SHORT).show();
        }
        ActivityUtils.popFragment(getActivity().getSupportFragmentManager());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
         /* Show again */
        ((TasksActivity) getActivity()).getSupportActionBar().show();

        lp.setAnchorId(R.id.activity_base_content);
        lp.anchorGravity = Gravity.BOTTOM | Gravity.END;
        fab.setLayoutParams(lp);


        // TODO: Hide keyboard
    }
}
