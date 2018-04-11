package com.renatasemanova.dailymenu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatasemanova.dailymenu.API.model.Location;
import com.renatasemanova.dailymenu.API.model.Restaurant;
import com.renatasemanova.dailymenu.API.model.Restaurant_;
import com.renatasemanova.dailymenu.API.model.UserRating;
import com.renatasemanova.dailymenu.BaseActivity;
import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.fragments.RestaurantDetailFragmentBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private BaseActivity activity;
    private final List<Restaurant> restaurants;

    public SearchAdapter(BaseActivity activity, List<Restaurant> restaurants) {
        this.activity = activity;
        this.restaurants = restaurants;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflatovanie jedneho riadku, stale je to rovnake, -> layoutInflater -> inflatovanie riadku
        // Vytvorenie noveho view holderu s riadkom z layoutu !!!
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_restaurant, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bind(restaurants, position);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_name)
        TextView name;

        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final List<Restaurant> results, final int position) {
            name.setText(results.get(position).getRestaurant().getName());
//            Log.d("TAG", String.format("Trailer %d: %s",position,results.get(position).getKey()));

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location location = results.get(position).getRestaurant().getLocation();
                    Restaurant_ name = results.get(position).getRestaurant();

                    UserRating rating = results.get(position).getRestaurant().getUserRating();

                    activity.changeToWithBack(new RestaurantDetailFragmentBuilder(
                            location.getAddress(),
                            name.getId(),
                            location.getLatitude(),
                            location.getLongitude(),
                            rating,
                            name.getName()

                            ).build()
                            ,this);

//                    Log.d("TAG","key: "+results.get(position).getKey());
                }
            });
        }
    }

}