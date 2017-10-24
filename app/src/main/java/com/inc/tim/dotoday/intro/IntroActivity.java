package com.inc.tim.dotoday.intro;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.tasks.TasksActivity;


public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setColorTransitionsEnabled(true);

        String title = getResources().getString(R.string.intro_title);
        String description = getResources().getString(R.string.intro_description);
        int image = R.drawable.intro_eisen;
        int backgroundColor = getResources().getColor(android.R.color.white);
        int titleColor = getResources().getColor(android.R.color.black);
        addSlide(AppIntro2Fragment.newInstance(title, description, image, backgroundColor, titleColor, titleColor));

        //FOCUS
        title = getResources().getString(R.string.focus_title);
        description = getResources().getString(R.string.focus_description);
        image = R.drawable.intro_focus;
        backgroundColor = getResources().getColor(R.color.red);
        addSlide(AppIntro2Fragment.newInstance(title, description,image, backgroundColor));

        //GOAL
        title = getResources().getString(R.string.goal_title);
        description = getResources().getString(R.string.goal_description);
        image = R.drawable.intro_goal;
        backgroundColor = getResources().getColor(R.color.indigo);
        addSlide(AppIntro2Fragment.newInstance(title, description,image, backgroundColor));

        //DELEGATE
        title = getResources().getString(R.string.delegate_title);
        description = getResources().getString(R.string.delegate_description);
        image = R.drawable.intro_delegate;
        backgroundColor = getResources().getColor(R.color.amber);
        addSlide(AppIntro2Fragment.newInstance(title, description,image, backgroundColor));

        //THROW
        title = getResources().getString(R.string.throw_title);
        description = getResources().getString(R.string.throw_description);
        image = R.drawable.intro_delete;
        backgroundColor = getResources().getColor(R.color.purple);
        addSlide(AppIntro2Fragment.newInstance(title, description,image, backgroundColor));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        markAsPassed();
        goToTaskActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        markAsPassed();
        goToTaskActivity();
    }
    private void markAsPassed() {
        PreferenceManager
                .getDefaultSharedPreferences(getBaseContext()).edit()
                .putBoolean("firstStart", false)
                .apply();
    }

    private void goToTaskActivity() {
        Intent intent = new Intent(IntroActivity.this, TasksActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }


}
