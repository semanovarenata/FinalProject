package com.renatasemanova.dailymenu.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatasemanova.dailymenu.API.model.DatabaseRestaurant;
import com.renatasemanova.dailymenu.API.model.Restaurant;
import com.renatasemanova.dailymenu.BaseActivity;
import com.renatasemanova.dailymenu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatabaseRestaurantAdapter extends RecyclerView.Adapter<DatabaseRestaurantAdapter.DatabaseRestaurantViewHolder> {

    private BaseActivity activity;
    private final DatabaseRestaurant restaurants;

    public DatabaseRestaurantAdapter(BaseActivity activity, DatabaseRestaurant restaurants) {
        this.activity = activity;
        this.restaurants = restaurants;
    }

    @Override
    public DatabaseRestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_restaurant, parent, false);
        return new DatabaseRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseRestaurantViewHolder holder, int position) {

    }


    public void onBindViewHolder(DatabaseRestaurantViewHolder holder) {
        holder.bind(restaurants.getRestaurants());
    }

    @Override
    public int getItemCount() {
        return restaurants.getRestaurants().size();
    }

    class DatabaseRestaurantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_name)
        TextView name;

        DatabaseRestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final List<Restaurant> results) {
//            name.setText(results);
        }

    }

}