package com.renatasemanova.dailymenu.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.fragments.FirstActivity;

public class ListWidgetProvider extends AppWidgetProvider {

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget_provider);

        Intent intent = new Intent(context, FirstActivity.class);
        Intent intentData = new Intent(context, ListWidgetService.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget, pendingIntent);
        views.setRemoteAdapter(R.id.widget_listView,intentData);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ListWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_listView);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


}

