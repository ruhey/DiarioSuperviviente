package org.aecc.superdiary.presentation.view.activity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;


import junit.framework.Test;

import org.aecc.superdiary.R;

import java.util.Random;

/**
 * Created by Ruhey on 25/08/2015.
 */
public class Receiver extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Intent intent = new Intent(this, Test.class);
        long[] pattern = {0, 300, 0};
        Random r = new Random();
        int notid_id = r.nextInt(Integer.MAX_VALUE - 1) + 1;
        PendingIntent pi = PendingIntent.getActivity(this, notid_id, intent, 0);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.aecc_corazonb)
                .setContentTitle("Diario de un Superviviente")
                .setContentText("Tómate la medicación.")
                .setVibrate(pattern)
                .setAutoCancel(true);

        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notid_id, mBuilder.build());
    }
}
