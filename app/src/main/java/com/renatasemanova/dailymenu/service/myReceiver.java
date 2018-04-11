package com.renatasemanova.dailymenu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class myReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myService1 = new Intent(context, myAlarmService.class);
        context.startService(myService1);
    }
}
