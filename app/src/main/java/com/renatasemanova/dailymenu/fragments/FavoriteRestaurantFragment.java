package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.API.model.Location;
import com.renatasemanova.dailymenu.API.model.Restaurant;
import com.renatasemanova.dailymenu.API.model.Restaurant_;
import com.renatasemanova.dailymenu.API.model.UserRating;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.DB.RestaurantDB;
import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@FragmentWithArgs
public class FavoriteRestaurantFragment extends BaseFragment {

    @BindView(R.id.recycler_view_favourite_restaurants)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favourite_restaurants;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity)baseActivity).enableViews(false);

        showLoading();
        FirebaseDatabase.getInstance().getReference().child("restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Restaurant> restaurantList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    RestaurantDB post = child.getValue(RestaurantDB.class);
                    Restaurant restaurant = new Restaurant();
                    Restaurant_ restaurant_ = new Restaurant_();
                    UserRating userRating = new UserRating();
                    Location location = new Location();
                    userRating.setRatingText(post.rating.getRatingText());
                    userRating.setAggregateRating(post.rating.getAggregateRating());
                    userRating.setVotes(post.rating.getVotes());
                    location.setLatitude(post.latitude);
                    location.setLongitude(post.longitude);
                    location.setAddress(post.address);
                    restaurant_.setId(post.id);
                    restaurant_.setName(post.restaurant);
                    restaurant_.setLocation(location);
                    restaurant_.setUserRating(userRating);

                    restaurant.setRestaurant(restaurant_);
                    restaurantList.add(restaurant);
                }

                if (recyclerView != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(new SearchAdapter(baseActivity, restaurantList));
                }

                hideLoading();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                hideLoading();
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseActivity.getSupportActionBar().setTitle(R.string.fav_restaurant);
        ((FirstActivity) getActivity()).getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }
}
