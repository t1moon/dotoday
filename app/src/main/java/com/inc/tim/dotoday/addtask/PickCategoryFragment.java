package com.inc.tim.dotoday.addtask;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.inc.tim.dotoday.util.ToolbarUtils;

import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.STATUSBAR_MATERIAL_COLORS;

public class PickCategoryFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.category_gridview, null);

        GridView gridView = (GridView) view.findViewById(R.id.category_gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String[] categories = getResources().getStringArray(R.array.categories_array);
                ActionBar actionBar = ((TasksActivity) getActivity()).getSupportActionBar();
                actionBar.setTitle(categories[position]);
                actionBar.setDisplayShowTitleEnabled(true);
//                /* Change color of toolbars */
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                Toolbar toolbar2 = (Toolbar) getActivity().findViewById(R.id.toolbar_2);
                Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner_nav);
                ToolbarUtils.changeAddToolbarColor((AppCompatActivity) getActivity(), position, toolbar,toolbar2, spinner);

                ((CategoryAdapter) parent.getAdapter()).getDialog().dismiss();
                AddTaskFragment addTaskFragment =
                        (AddTaskFragment) getActivity().getSupportFragmentManager().findFragmentByTag("AddTaskFragment");

                addTaskFragment.setSpinner(position);
                addTaskFragment.setCategory(position); // it's for saving into DB
                addTaskFragment.setCroller(position);  // it's for changing color of croller

            }
        });

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Выберите категорию");
        Dialog dialog = builder.create();
        gridView.setAdapter(new CategoryAdapter(getActivity(), dialog));
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);                      // prevent from touch outside

        // onBackpressed will be popFragment
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });
    }




    private class CategoryAdapter extends BaseAdapter {
        private Context context;
        private Dialog dialog;



        public CategoryAdapter(Context c, Dialog d) {
            context = c;
            dialog = d;
        }

        public Dialog getDialog() {
            return dialog;
        }

        @Override
        public int getCount() {
            return MATERIAL_COLORS.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;

            /*  TEXT FOR CATEGORY */
            textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(400, 400));
            textView.setGravity(Gravity.CENTER);
            textView.setText(getResources().getStringArray(R.array.categories_array)[position]);
            textView.setTextSize(24);
            textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            textView.setTextColor(Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                textView.setElevation(8);

            switch (position) {
                case 0: textView.setBackgroundResource(R.drawable.gridview_item_red);
                    break;
                case 1: textView.setBackgroundResource(R.drawable.gridview_item_indigo);
                    break;
                case 2: textView.setBackgroundResource(R.drawable.gridview_item_amber);
                    break;
                case 3: textView.setBackgroundResource(R.drawable.gridview_item_purple);
                    break;
            }
            return textView;
        }

    }
}

