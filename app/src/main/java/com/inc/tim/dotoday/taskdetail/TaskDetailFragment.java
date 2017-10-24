package com.inc.tim.dotoday.taskdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.data.Task;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.CommonUtils;
import com.inc.tim.dotoday.util.ToolbarUtils;
import com.sdsmdg.harjot.crollerTest.Croller;

import static com.inc.tim.dotoday.R.id.spinner_nav;

public class TaskDetailFragment extends Fragment implements DetailContract.View{
    private long taskId;
    private DetailContract.Presenter presenter;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    Toolbar toolbar1;
    Croller croller;
    Spinner spinner;
    private int importance;
    private int category;
    EditText title;
    EditText description;
    TextInputLayout til_title;

    public TaskDetailFragment() {
        // Required empty public constructor
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

        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appbar_layout);
        croller = (Croller) view.findViewById(R.id.detail_croller);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar1 = (Toolbar) view. findViewById(R.id.detail_toolbar_2);
        til_title = (TextInputLayout) view.findViewById(R.id.detail_til_title);
        title  = (EditText) view.findViewById(R.id.detail_task_title_et);
        description = (EditText) view.findViewById(R.id.detail_task_description);
        spinner = (Spinner) getActivity().findViewById(spinner_nav);

        addItemsToSpinner();

        croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                // use the progress
                importance = progress;
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isValid = isValid();
        if (!isValid)
            return false;

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.continue_btn:
                int category =((TasksActivity) getActivity()).getCurrentCategory();
                ((TasksActivity) getActivity()).setBottomBarSelected(false);
                presenter.editTask(title.getText().toString(),
                        description.getText().toString(),
                        importance,
                        category,
                        taskId);
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isValid() {
        if( title.getText().toString().length() == 0 ) {
            til_title.setError(getString(R.string.field_required));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spinner.setVisibility(Spinner.GONE);
        ToolbarUtils.returnToolbar(appBarLayout, ((TasksActivity) getActivity()).getSupportActionBar());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showTask(Task task) {
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        category = task.getCategory();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ToolbarUtils.changeDetailToolbar(activity, activity.getSupportActionBar(), toolbar,
                toolbar1, appBarLayout);

        spinner.setSelection(category);
        spinner.setVisibility(Spinner.VISIBLE);

        croller.setProgress(task.getImportance());
        croller.setIndicatorColor(CommonUtils.ColorUtil.MATERIAL_COLORS[category]);
        croller.setProgressPrimaryColor(CommonUtils.ColorUtil.MATERIAL_COLORS[category]);
        croller.setBackCircleColor(CommonUtils.ColorUtil.MATERIAL_COLORS_LIGHT[category]);
    }

    @Override
    public void notifyEdited() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.task_edited), Snackbar.LENGTH_SHORT).show();
        }
        ActivityUtils.popFragment(getActivity().getSupportFragmentManager());
    }

    private void addItemsToSpinner() {
        final String[] categories = getResources().getStringArray(R.array.categories_array);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_dropdown, categories);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() != null) {
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                    Toolbar toolbar2 = (Toolbar) activity.findViewById(R.id.detail_toolbar_2);
                    ToolbarUtils.changeAddToolbarColor(activity, position, toolbar, toolbar2, spinner, croller);
                    ((TasksActivity) getActivity()).setCurrentCategory(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
