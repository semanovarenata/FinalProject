package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.BaseFragment;
import com.renatasemanova.dailymenu.R;

import butterknife.BindView;

@FragmentWithArgs
public class RestaurantDetailFragment extends BaseFragment implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Arg
    String address;

    @Arg
    String latitude;

    @Arg
    String longitude;

    @BindView(R.id.address)
    TextView addressText;

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
        addressText.setText(address);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng address = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

        googleMap.addMarker(new MarkerOptions().position(address)
                .title("Address"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15.0f));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
    }
}
