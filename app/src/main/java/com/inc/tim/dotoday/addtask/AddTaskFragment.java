package com.inc.tim.dotoday.addtask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.util.ActivityUtils;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {
    private AddTaskContract.Presenter presenter;
    AppBarLayout appBarLayout;
    SeekBar seekBar;

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

        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appbar_layout);

        changeToolbar();

        DialogFragment pickDialog = new PickCategoryFragment();
        pickDialog.show(getActivity().getSupportFragmentManager(), "Dialog");

        seekBar = (SeekBar) view.findViewById(R.id.importance_sb);

        setHasOptionsMenu(true);
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
        unchangeToolbar();
        // TODO: Hide keyboard
    }

    private void changeToolbar() {
        ((TasksActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
        ((TasksActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(0);
        }
    }

    private void unchangeToolbar() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(8);
        }
        ((TasksActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((TasksActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.continue_btn:
                EditText title  = (EditText) getActivity().findViewById(R.id.add_task_title_et);
                EditText description = (EditText) getActivity().findViewById(R.id.add_task_description);
                int importance = seekBar.getProgress();
                presenter.saveTask(
                        title.getText().toString(),
                        description.getText().toString(),
                        importance
                );
        }
        return super.onOptionsItemSelected(item);
    }

}
