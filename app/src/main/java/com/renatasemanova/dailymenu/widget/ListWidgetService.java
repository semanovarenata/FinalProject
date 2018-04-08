package com.renatasemanova.dailymenu.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pixplicity.easyprefs.library.Prefs;
import com.renatasemanova.dailymenu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static com.renatasemanova.dailymenu.widget.ListWidgetService.DAILY_MENU;

public class ListWidgetService extends RemoteViewsService {
    public static final String DAILY_MENU = "DAILY_MENU";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        return new AppWidgetListView(this, Prefs.getOrderedStringSet(DAILY_MENU, Collections.<String>emptySet()));
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

    private List<String> dataList;
    private Context context;

    public AppWidgetListView(Context applicationContext, Set<String> dataList) {
        this.context = applicationContext;
        this.dataList = new ArrayList<>(dataList);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d("LISTEIDGET", "UPDATED");
        this.dataList = new ArrayList<>(Prefs.getOrderedStringSet(DAILY_MENU, Collections.<String>emptySet()));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget_row);
        views.setTextViewText(R.id.item_name, dataList.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
