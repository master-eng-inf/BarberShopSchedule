package com.udl.bss.barbershopschedule.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;

import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final static String TAG = "FCM";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            try {
                Map<String, String> notification = remoteMessage.getData();
                sendNotification(notification.get("service"), notification.get("time"), notification.get("type"));
            } catch (Exception ex) {

            }
        }
    }

    private void sendNotification(String serviceName, String serviceTime, String type) {
        String notificationTitle = type.equals("request") ? getString(R.string.notification_type_request_appointment) : getString(R.string.notification_type_request_appointment);
        String notificationBody = serviceName + "  " + serviceTime;

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.mipmap.logo_icon)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id),
                        "Default chanel",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify((new Random()).nextInt(999999), notificationBuilder.build());
        }


    }
}
