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
        getActivity().setTitle(R.string.saved_menu);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
