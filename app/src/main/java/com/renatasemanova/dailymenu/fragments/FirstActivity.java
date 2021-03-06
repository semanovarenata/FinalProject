package com.renatasemanova.dailymenu.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.renatasemanova.dailymenu.BaseActivity;
import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.service.myReceiver;

import java.util.Calendar;

public class FirstActivity extends BaseActivity {


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_first;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        Calendar cal = Calendar.getInstance();
        // it is set to 10.30
        cal.set(Calendar.HOUR, 10);
        cal.set(Calendar.MINUTE, 53);
        cal.set(Calendar.SECOND, 0);

        long start = cal.getTimeInMillis();
        if (cal.before(Calendar.getInstance())) {
            start += AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        }

        Intent mainIntent = new Intent(this, myReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager myAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        myAlarm.setRepeating(AlarmManager.RTC_WAKEUP, start, AlarmManager.INTERVAL_DAY, pIntent);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        if (menuItem.getItemId() == R.id.find_restaurant) {
                            changeTo(new FirstFragmentBuilder().build());
                        } else if (menuItem.getItemId() == R.id.favorite_restaurant) {
                            changeTo(new FavoriteRestaurantFragmentBuilder().build());
                        } else if (menuItem.getItemId() == R.id.saved_menu) {
                            changeTo(new SavedMenuFragmentBuilder().build());
                        }

                        menuItem.setChecked(true);

                        drawer.closeDrawers();
                        return true;
                    }
                });
        if (savedInstanceState == null) {
            changeTo(new FirstFragmentBuilder().build());
        }
    }

    private boolean mToolBarNavigationListenerIsRegistered = false;

    public void enableViews(boolean enable) {

        if (enable) {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }

    public DrawerLayout getDrawer() {
        return this.drawer;
    }

    public ActionBarDrawerToggle getToggle() {
        return this.toggle;
    }

}
