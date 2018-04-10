package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;

import butterknife.BindView;

@FragmentWithArgs
public class FavoriteRestaurantFragment extends BaseFragment{

    @BindView(R.id.recycler_view_favourite_restaurants)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favourite_restaurants;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {


        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setAdapter(new SearchAdapter(baseActivity, response.body()));
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseActivity.getSupportActionBar().setTitle(R.string.fav_restaurant);

    }
}
