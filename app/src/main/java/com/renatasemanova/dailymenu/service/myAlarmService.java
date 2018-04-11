package com.renatasemanova.dailymenu.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import com.renatasemanova.dailymenu.R;
import com.renatasemanova.dailymenu.fragments.FirstActivity;

public class myAlarmService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()  {

        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        displayNotification();
    }

    @Override
    public void onDestroy()  {

        super.onDestroy();
    }


    public void displayNotification() {

        Intent mainIntent = new Intent(this, FirstActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_alarm_white)
                .setTicker(getString(R.string.alarm))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.new_menu_notification));

        nm.notify(100, builder.build());
    }

}
