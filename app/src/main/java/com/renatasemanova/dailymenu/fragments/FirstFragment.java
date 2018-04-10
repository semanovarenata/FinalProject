package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.API.ApiUtils;
import com.renatasemanova.dailymenu.API.model.Search;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.adapters.SearchAdapter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FragmentWithArgs
public class FirstFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search)
    SearchView searchView;

    public String text;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_restaurant;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.find_restaurant);
        userInput();
        search(text);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void userInput() {
        // perform set on query text listener event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                text = query;
                Toast.makeText(baseActivity, "Our word : " + query, Toast.LENGTH_SHORT).show();
                search(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                text = newText;
                return false;
            }
        });
    }

    public void search(String text) {
        showLoading();
        ApiUtils.getZomatoApi().searchRestaurant(text).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                hideLoading();
                if(recyclerView!= null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(new SearchAdapter(baseActivity, response.body()));
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {

            }
        });

//        ApiUtils.getZomatoApi().dailyMenu(16514912).enqueue(new Callback<Search>() {
//            @Override
//            public void onResponse(Call<Search> call, Response<Search> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Search> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FirstActivity) getActivity()).getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
