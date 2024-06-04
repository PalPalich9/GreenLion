package com.example.myapplication;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;



import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//Класс для создания уведомления

public class NotificationReceiver extends BroadcastReceiver {

    //Названия канала для создания уведомления
    private static final String CHANNEL_ID = "CHANNEL_ID";


    //Метод создания уведомления
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                //.setAutoCancel(false)
                .setPriority(PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Напоминание")
                .setContentText("Пора принять лекарство")
                .setAutoCancel(false);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        createChannelIfNeeded(notificationManager);

        notificationManager.notify(Integer.parseInt(Arr.readNumFile(context)), builder.build());
        //Arr.writeNumFile(context);

    }

    //Проверка версии android (с 8-ой версии нужно создавать канал для уведомлений)
    public static void createChannelIfNeeded(NotificationManagerCompat notificationManager) {
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
