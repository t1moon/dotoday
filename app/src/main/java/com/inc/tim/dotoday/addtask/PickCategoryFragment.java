package com.inc.tim.dotoday.addtask;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TasksActivity;

import java.util.Arrays;
import java.util.List;

public class PickCategoryFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_category)
                .setItems(R.array.categories_array, new DialogInterface.OnClickListener() {
                    // The 'which' argument contains the index position
                    // of the selected item
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), Integer.toString(which), Toast.LENGTH_SHORT).show();
                        ((TasksActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
                        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.categories_array));
                        switch (which) {
                            case 0:
                                ((TasksActivity) getActivity()).getSupportActionBar().setTitle(categories.get(0));
                                break;
                            case 1:
                                ((TasksActivity) getActivity()).getSupportActionBar().setTitle(categories.get(0));
                                break;
                        }
                    }
                });
        // Create the AlertDialog object and return it
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
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                getActivity().onBackPressed();
            }
        });
    }


}

