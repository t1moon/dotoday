package com.inc.tim.dotoday.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TaskFragment;

/**
 * Created by Timur on 23-Sep-17.
 */

public class ActivityUtils {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.activity_base_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.activity_base_content, fragment)
                .commit();
    }
}
