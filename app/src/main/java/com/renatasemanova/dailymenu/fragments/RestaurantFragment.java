package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;

@FragmentWithArgs
public class RestaurantFragment extends BaseFragment{


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_restaurant_detail;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity)getActivity()).getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        getActivity().setTitle(R.string.detail_restaurant);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
