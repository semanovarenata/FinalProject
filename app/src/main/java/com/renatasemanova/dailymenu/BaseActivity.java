package com.renatasemanova.dailymenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import activitystarter.ActivityStarter;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    private AlertDialog progressDialog;

    @LayoutRes
    protected abstract int getLayoutResId();
    protected abstract void init(@Nullable Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //TODO Parceler https://github.com/johncarl81/parceler
        //TODO ActivityStarter https://github.com/MarcinMoskala/ActivityStarter
        ActivityStarter.fill(this);
        init(savedInstanceState);
    }

    public void launchActivity(Intent intent, Boolean finishAll) {
        Intent finalIntent = intent;
        if (finishAll) {
            finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(finalIntent);
    }

    public void changeTo(BaseFragment fragment) {
        changeTo(fragment, null);
        showArrow(false);
    }

    public void changeTo(BaseFragment fragment, String tag) {
        Timber.i("Change fragment to " + name(fragment));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                       .replace(R.id.container, fragment, tag)
                       .commit();
    }

    public void changeToWithBack(BaseFragment fragment, Object thisFragmentOrActivity) {
        changeToWithBack(fragment, name(thisFragmentOrActivity) + "_" + name(fragment));
        showArrow(true);
    }

    public void changeToWithBack(BaseFragment fragment, String tag) {
        Timber.i("Change fragment to " + name(fragment) + " with tag '" + tag + "'");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                       .addToBackStack(tag)
                       .replace(R.id.container, fragment, tag)
                       .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof BaseFragment) || ((BaseFragment) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void showArrow(boolean show) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
            getSupportActionBar().setDisplayShowHomeEnabled(show);
        }
    }

    public static String name(Object o) {
        String ret = o.getClass()
                      .getSimpleName();
        if (o instanceof Class) {
            ret = ((Class) o).getSimpleName();
        } else if (ret.length() == 0) {
            ret = o.getClass()
                   .getName();
            ret = ret.substring(ret.lastIndexOf('.') + 1, ret.length() - 1);
        }
        return ret;
    }

    public static AlertDialog.Builder showProgress(Context context) {
        return new AlertDialog.Builder(context, R.style.DialogProgress).setCancelable(false)
                                                                       .setView(R.layout.dialog_loading);
    }

    public boolean isLoading() {
        return progressDialog != null;
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = showProgress(this).show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
