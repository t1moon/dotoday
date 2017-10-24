package com.inc.tim.dotoday.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.inc.tim.dotoday.R;
import com.inc.tim.dotoday.util.ActivityUtils;
import com.roughike.bottombar.BottomBar;

public class TasksActivity extends AppCompatActivity {
    BottomBar bottomBar;
    private int currentCategory = 0;
    private boolean bottomBarSelected = true; // when you back from add, we know that bottom bar is not selected manually

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActivityUtils.replaceFragment(getSupportFragmentManager(),new TaskFragment(), "TaskFragment");

        // manage fragment backstack
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                {
                    /* when you are at home screen, change icon to default and disable as up*/
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_24dp);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                else
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);

                    /* Everytime when I go deep in stack I need to set listener on toolbar */
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        });

        // init bottom bar
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getCurrentFocus() != null) {
                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    fab.hide();
                    bottomBar.setVisibility(View.GONE);
                } else {
                    fab.show();
                    bottomBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public int getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
    }

    public boolean isBottomBarSelected() {
        return bottomBarSelected;
    }

    public void setBottomBarSelected(boolean bottomBarSelected) {
        this.bottomBarSelected = bottomBarSelected;
    }
}
