package com.renatasemanova.dailymenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    BaseActivity baseActivity;
    private Unbinder unbinder = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();
        if (layoutResId != 0) {
            return inflater.inflate(layoutResId, container, false);
        } else {
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        FragmentArgs.inject(this);
        init(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivity = null;
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public boolean onBackPressed() {
        return true;
    }

    public void showLoading() {
        if (baseActivity != null && !baseActivity.isLoading()) {
            baseActivity.showLoading();
        }
    }

    public void hideLoading() {
        if (baseActivity != null && baseActivity.isLoading()) {
            baseActivity.hideLoading();
        }
    }

    @LayoutRes
    protected abstract int getLayoutResId();
    protected abstract void init(@Nullable Bundle savedInstanceState);
}