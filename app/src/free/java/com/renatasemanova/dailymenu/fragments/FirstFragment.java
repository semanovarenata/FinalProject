package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.renatasemanova.dailymenu.R;

@FragmentWithArgs
public class FirstFragment extends FirstFragmentParent {
    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        adHoc();

    }

    private void adHoc() {
        AdView mAdView = (AdView) getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }
}
