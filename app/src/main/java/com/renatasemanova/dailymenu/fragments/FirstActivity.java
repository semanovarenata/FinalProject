package com.renatasemanova.dailymenu.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.SearchView;

import com.renatasemanova.dailymenu.BaseActivity;
import com.renatasemanova.dailymenu.R;

import butterknife.BindView;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.search)
    SearchView searchView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_first;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        changeTo(new FirstFragmentBuilder().build());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
