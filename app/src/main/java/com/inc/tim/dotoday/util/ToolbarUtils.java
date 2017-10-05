package com.inc.tim.dotoday.util;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TasksActivity;

import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.STATUSBAR_MATERIAL_COLORS;

/**
 * Created by Timur on 05-Oct-17.
 */

public class ToolbarUtils {

    public static void changeToolbar(ActionBar actionBar, AppBarLayout appBarLayout) {
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24dp);
        actionBar.setDisplayShowTitleEnabled(false);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(0);
        }
    }

    public static void unchangeToolbar(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, AppBarLayout appBarLayout) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(8);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);

         /* Change color of toolbars */
        ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[1]); //indigo
        actionBar.setBackgroundDrawable(toolbarColor);
        toolbar.setBackground(toolbarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(STATUSBAR_MATERIAL_COLORS[1]);
        }
    }
}