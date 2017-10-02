package com.inc.tim.dotoday.addtask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.util.ActivityUtils;

import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.STATUSBAR_MATERIAL_COLORS;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {
    private AddTaskContract.Presenter presenter;
    AppBarLayout appBarLayout;
    SeekBar seekBar;
    private int category;

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

         /* Change color of toolbars */
        ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[1]); //indigo
        ((TasksActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(toolbarColor);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_2);
        toolbar.setBackground(toolbarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(STATUSBAR_MATERIAL_COLORS[1]);
        }
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
                        importance,
                        category
                );
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }
}
