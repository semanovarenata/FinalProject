package com.renatasemanova.dailymenu.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.API.model.UserRating;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.DB.RestaurantDB;
import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.widget.ListWidgetProvider;

import java.util.Random;

import butterknife.BindView;
import timber.log.Timber;

import static android.content.ContentValues.TAG;

@FragmentWithArgs
public class RestaurantDetailFragment extends BaseFragment implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Arg
    String address;

    @Arg
    String id;

    @Arg
    String latitude;

    @Arg
    String longitude;

    @Arg
    String restaurant_name;

    @Arg
    UserRating rating;

    String dailyMenu;

    @BindView(R.id.address)
    TextView addressText;

    @BindView(R.id.save_restaurant_button)
    Button save_restaurant_button;

    @BindView(R.id.remove_restaurant_button)
    Button remove_restaurant_button;

    @BindView(R.id.save_menu_button)
    Button save_menu_button;

    @BindView(R.id.save_menu_button_done)
    Button save_menu_button_done;

    @BindView(R.id.rating_text)
    TextView rating_data;

    @BindView(R.id.menu_text)
    TextView menu_data;


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String restaurant_id;
    private static final Random random = new Random();


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_restaurant_detail;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity) getActivity()).getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((FirstActivity) baseActivity).enableViews(true);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addressText.setText(address);
        dailyMenu();
        initializeDB();
        getRestaurantState();
        saveRestaurant();
        removeRestaurant();
        showReview();
        saveMenu();

    }

    public static final String[] DAILY_MENU = {
            "Slepačia s cestovinou a zeleninou \nFajítas z hovädzej sviečkovice podávaný na panvičke s tortillou a zemiakovými hranolčekmi /1,6/ 7,70 € ",
            "Šošovicová kyslá \nMorčacie RED-THAI-CURRY podávané s mrkvovo-ananásovým šalátikom/1,7/  4,90 ",
            "Paradajková \nGreen lady pizza /1,3,7/ 4,10 €",
            "Fazulová \nHawai pizza/1,7/  5,20 €",

    };

    public static String getRandomMenu() {
        return DAILY_MENU[new Random().nextInt(DAILY_MENU.length - 1)];
    }


    public void dailyMenu() {
        menu_data.setText(getRandomMenu());
        dailyMenu = getRandomMenu();
    }

    private void getRestaurantState() {
        showLoading();
        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Timber.d("Found matching value in " + item.getKey());
                            if (item.getKey().equals("restaurants")) {
                                for (DataSnapshot restaurants : item.getChildren()) {
                                    if (restaurants != null && restaurants.getValue(RestaurantDB.class).restaurant.equals(restaurant_name)) {
                                        save_restaurant_button.setVisibility(View.GONE);
                                        remove_restaurant_button.setVisibility(View.VISIBLE);

                                        return;
                                    } else {
                                        save_restaurant_button.setVisibility(View.VISIBLE);
                                        remove_restaurant_button.setVisibility(View.GONE);
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

    private void removeRestaurant() {
        remove_restaurant_button.setOnClickListener(new View.OnClickListener() {
            DataSnapshot snapshot;

            @Override
            public void onClick(View view) {
                showLoading();
                FirebaseDatabase.getInstance().getReference()
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot item : snapshot.getChildren()) {
                                    Timber.d("Found matching value in " + item.getKey());
                                    if (item.getKey().equals("restaurants")) {
                                        for (DataSnapshot restaurants : item.getChildren()) {
                                            if (restaurants != null && restaurants.getValue(RestaurantDB.class).restaurant.equals(restaurant_name)) {
                                                restaurants.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        hideLoading();
                                                        if (task.isSuccessful()) {
                                                            save_restaurant_button.setVisibility(View.VISIBLE);
                                                            remove_restaurant_button.setVisibility(View.GONE);
                                                            Toast.makeText(getContext(), "REMOVED", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getContext(), "NOT WORKING", Toast.LENGTH_SHORT).show();

                                                        }
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

                save_restaurant_button.setVisibility(View.VISIBLE);
                remove_restaurant_button.setVisibility(View.GONE);

            }
        });
    }

    private void removeDataFromMenu() {

    }

    private void saveRestaurant() {
        // Save / update the restaurant
        save_restaurant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = mFirebaseInstance.getReference("restaurants");

                if (TextUtils.isEmpty(restaurant_id)) {
                    createRestaurant(restaurant_name, id);
                } else {
                    createRestaurant(restaurant_name, id);
                }

                save_restaurant_button.setVisibility(View.GONE);
                remove_restaurant_button.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveMenu() {
        save_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = mFirebaseInstance.getReference("menu");
                mFirebaseDatabase.getRef().removeValue();

                createRestaurant(restaurant_name, id);

                save_menu_button.setVisibility(View.GONE);
                save_menu_button_done.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initializeDB() {
        //DATABASE
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'restaurant' node
        mFirebaseDatabase = mFirebaseInstance.getReference("restaurants");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Daily Menu");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // update toolbar title
                if (baseActivity.getSupportActionBar() != null) {
                    baseActivity.getSupportActionBar().setTitle(restaurant_name);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    private void initializeMenuDB() {
        //DATABASE
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'restaurant' node
        mFirebaseDatabase = mFirebaseInstance.getReference("menu");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Daily Menu");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // update toolbar title
                if (baseActivity.getSupportActionBar() != null) {
                    baseActivity.getSupportActionBar().setTitle(restaurant_name);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    private void createRestaurant(String id, String restaurant_name) {
        if (TextUtils.isEmpty(restaurant_id)) {
            restaurant_id = mFirebaseDatabase.push().getKey();
        }

        RestaurantDB restaurantDB = new RestaurantDB(id, restaurant_name, address, latitude, longitude, rating, dailyMenu);
        mFirebaseDatabase.child(restaurant_id).setValue(restaurantDB);
        addUserChangeListener();
    }


    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(restaurant_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RestaurantDB restaurantDB = dataSnapshot.getValue(RestaurantDB.class);

                // Check for null
                if (restaurantDB == null) {
                    Log.e(TAG, "Menu data is null!");
                    return;
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }


    public void showReview() {
        rating_data.setText(rating.toString());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng address = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        googleMap.addMarker(new MarkerOptions().position(address)
                .title("Address"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15.0f));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
    }


    private void updateWidget(final Context context) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.setComponent(new ComponentName(context, ListWidgetProvider.class));
                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        baseActivity.getSupportActionBar().setTitle(R.string.save_menu);

    }
}
