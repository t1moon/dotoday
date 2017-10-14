package com.inc.tim.dotoday.addtask;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
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
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.inc.tim.dotoday.util.CommonUtils;
import com.inc.tim.dotoday.util.ToolbarUtils;
import com.sdsmdg.harjot.crollerTest.Croller;

import static com.inc.tim.dotoday.R.id.spinner_nav;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {
    private AddTaskContract.Presenter presenter;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    private int importance;
    EditText title;
    EditText description;
    TextInputLayout til_title;
    Croller croller;
    Spinner spinner;

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
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_2);

        spinner = (Spinner) getActivity().findViewById(spinner_nav);
        final String[] categories = getResources().getStringArray(R.array.categories_array);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_dropdown, categories);
        spinner.setAdapter(spinnerAdapter);

        addItemsToSpinner();
        til_title = (TextInputLayout) view.findViewById(R.id.til_title);

        title  = (EditText) view.findViewById(R.id.add_task_title_et);
        description = (EditText) view.findViewById(R.id.add_task_description);


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ToolbarUtils.changeAddToolbar(activity, activity.getSupportActionBar(), toolbar, appBarLayout);

        DialogFragment pickDialog = new PickCategoryFragment();
        pickDialog.show(getActivity().getSupportFragmentManager(), "Dialog");

        croller = (Croller) view.findViewById(R.id.croller);

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
        spinner.setVisibility(Spinner.GONE);
        ToolbarUtils.returnToolbar(appBarLayout, ((TasksActivity) getActivity()).getSupportActionBar());
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
                presenter.saveTask(
                        title.getText().toString(),
                        description.getText().toString(),
                        importance,
                        category
                );
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

    public void setSpinner(int position) {
        spinner.setSelection(position);
        spinner.setVisibility(Spinner.VISIBLE);
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
                    Toolbar toolbar2 = (Toolbar) activity.findViewById(R.id.toolbar_2);
                    ToolbarUtils.changeAddToolbarColor(activity, position, toolbar, toolbar2, spinner, croller);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
