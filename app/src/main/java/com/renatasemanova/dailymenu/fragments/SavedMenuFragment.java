package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;

@FragmentWithArgs
public class SavedMenuFragment extends BaseFragment{


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_saved_menu;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity)baseActivity).enableViews(false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseActivity.getSupportActionBar().setTitle(R.string.saved_menu);

    }
}
