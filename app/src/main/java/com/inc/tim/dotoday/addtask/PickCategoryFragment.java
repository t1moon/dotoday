package com.inc.tim.dotoday.addtask;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.inc.tim.dotoday.R;

public class PickCategoryFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.category_gridview, null);

        GridView gridView = (GridView) view.findViewById(R.id.category_gridview);
        gridView.setAdapter(new CategoryAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            }
        });

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Выберите категорию");
        return builder.create();
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

        // references to our images
        private Integer[] colors = {
                Color.parseColor("#f44336"),    // Red
                Color.parseColor("#3F51B5"),    // Blue
                Color.parseColor("#FFC107"),    // Orange
                Color.parseColor("#9C27B0")     // Purple
        };

        public CategoryAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return colors.length;
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                textView.setElevation(8);

            textView.setBackgroundColor(colors[position]);
            return textView;
        }
    }
}

