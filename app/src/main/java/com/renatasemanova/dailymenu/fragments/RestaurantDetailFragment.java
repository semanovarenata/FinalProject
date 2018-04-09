package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;

@FragmentWithArgs
public class RestaurantDetailFragment extends BaseFragment implements OnMapReadyCallback {


    private GoogleMap mMap;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_restaurant_detail;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ((FirstActivity) getActivity()).getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        getActivity().setTitle(R.string.detail_restaurant);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
                // and move the map's camera to the same location.
                LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
