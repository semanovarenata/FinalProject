package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.DB.RestaurantDB;
import com.renatasemanova.dailymenu.R;

import butterknife.BindView;
import timber.log.Timber;

@FragmentWithArgs
public class SavedMenuFragment extends BaseFragment{

    @BindView(R.id.saved_menu)
    TextView saved_menu;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_saved_menu;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity)baseActivity).enableViews(false);

        showLoading();
        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Timber.d("Found matching value in " + item.getKey());
                            if (item.getKey().equals("menu")) {
                                for (DataSnapshot restaurants : item.getChildren()) {
                                    if (restaurants != null) {
                                        restaurants.getRef().addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                RestaurantDB post = dataSnapshot.getValue(RestaurantDB.class);
                                                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + post.restaurant);



                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("The read failed: " + databaseError.getCode());

                                            }
                                        });
                                    }
                                }
                            }
                            hideLoading();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
        baseActivity.getSupportActionBar().setTitle(R.string.saved_menu);

    }
}
