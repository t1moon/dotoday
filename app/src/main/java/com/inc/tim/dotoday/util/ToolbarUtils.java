package com.inc.tim.dotoday.util;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TasksActivity;
import com.sdsmdg.harjot.crollerTest.Croller;

import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.MATERIAL_COLORS;
import static com.inc.tim.dotoday.util.CommonUtils.ColorUtil.STATUSBAR_MATERIAL_COLORS;

/**
 * Created by Timur on 05-Oct-17.
 */

public class ToolbarUtils {


    public static void changeAddToolbar(final AppCompatActivity activity, ActionBar actionBar, final Toolbar toolbar, AppBarLayout appBarLayout) {
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        int category = ((TasksActivity) activity).getCurrentCategory();
        ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[category]);
        toolbar.setBackgroundColor(toolbarColor.getColor());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(0);
        }
    }
    public static void changeDetailToolbar(final AppCompatActivity activity,
                                           ActionBar actionBar, Toolbar toolbar, Toolbar toolbar1,
                                           AppBarLayout appBarLayout, int position) {
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        String[] categories = activity.getResources().getStringArray(R.array.categories_array);
        actionBar.setTitle(categories[position]);
        actionBar.setDisplayShowTitleEnabled(true);

        int category = ((TasksActivity) activity).getCurrentCategory();
        ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[category]);
        toolbar.setBackgroundColor(toolbarColor.getColor());
        toolbar1.setBackgroundColor(toolbarColor.getColor());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(0);
        }
    }

    public static void returnToolbar(AppBarLayout appBarLayout, ActionBar actionBar) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            appBarLayout.setElevation(8);
        }
        actionBar.setDisplayShowTitleEnabled(false);

    }

    public static void changeToolbarColor(final AppCompatActivity activity, int position,
                                          final Toolbar toolbar) {
        int from= ((TasksActivity) activity).getCurrentCategory();
        int to = position;
        Integer colorFrom = MATERIAL_COLORS[from];
        Integer colorTo = MATERIAL_COLORS[to];
        Integer colorStatusFrom = STATUSBAR_MATERIAL_COLORS[from];
        Integer colorStatusTo = STATUSBAR_MATERIAL_COLORS[to];
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorStatusAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStatusFrom, colorStatusTo);

        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        colorStatusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
                }
            }
        });

        startAnimation(colorAnimator);
        startAnimation(colorStatusAnimator);
    }


    public static void changeAddToolbarColor(final AppCompatActivity activity, int position,
                                             final Toolbar toolbar, final Toolbar toolbar1, Spinner spinner, final Croller croller) {
        int from = ((TasksActivity) activity).getCurrentCategory();
        Integer colorFrom = MATERIAL_COLORS[from];
        Integer colorTo = MATERIAL_COLORS[position];

        Integer colorStatusFrom = STATUSBAR_MATERIAL_COLORS[from];
        Integer colorStatusTo = STATUSBAR_MATERIAL_COLORS[position];

        Integer colorCircleFrom = CommonUtils.ColorUtil.MATERIAL_COLORS_LIGHT[from];
        Integer colorCircleTo = CommonUtils.ColorUtil.MATERIAL_COLORS_LIGHT[position];


        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorStatusAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStatusFrom, colorStatusTo);
        ValueAnimator colorIndicatorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorProgressAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorBackCircleAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorCircleFrom, colorCircleTo);

        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                toolbar1.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        colorStatusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
                }
            }
        });
        colorIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                croller.setIndicatorColor((Integer) animation.getAnimatedValue());
            }
        });
        colorProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                croller.setProgressPrimaryColor((Integer) animation.getAnimatedValue());
            }
        });
        colorBackCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                croller.setBackCircleColor((Integer) animation.getAnimatedValue());
            }
        });
        startAnimation(colorAnimator);
        startAnimation(colorStatusAnimator);
        startAnimation(colorIndicatorAnimator);
        startAnimation(colorProgressAnimator);
        startAnimation(colorBackCircleAnimator);

        ColorDrawable toolbarColor = new ColorDrawable(MATERIAL_COLORS[position]);
        spinner.setPopupBackgroundDrawable(toolbarColor);
    }

    private static void startAnimation(ValueAnimator animator) {
        long duration = 600;
        long delay = 0;
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.start();
    }
}
