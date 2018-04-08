package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.renatasemanova.dailymenu.BaseActivity;
import com.renatasemanova.dailymenu.R;

public class FirstActivity extends BaseActivity {


    private DrawerLayout drawer;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

        changeTo(new FirstFragmentBuilder().build());
    }

    public DrawerLayout getDrawer(){
        return this.drawer;
    }



}
