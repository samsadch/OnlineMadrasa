package com.onlinemadrasa.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.onlinemadrasa.HomeActivity;
import com.onlinemadrasa.R;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationUtils {

    public static String CHANNEL_ID = "Online Madrasa";
    public static Integer NOTIFICATION_ID = 123123;

    public static void showNotification(Context context, String title, String content) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_quran)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static void showSnoozeNotification(Context context, String title, String content) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        Intent snoozeIntent = new Intent(context, HomeActivity.class);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getActivity(context, 0, snoozeIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_quran)
                .setContentTitle("Online Madrasa")
                .setContentText("Hello Students!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_home, "Home",
                        snoozePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static void showReplyNotification(Context context) {

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_quran)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        // Issue the initial notification with zero progress
        final int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        for (int i = 0; i < 100; i++) {
            final int x = i;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (x == 100) {
                        // When done, update the notification one more time to remove the progress bar
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                    } else {
                        mBuilder.setProgress(PROGRESS_MAX, x, false);
                    }

                }
            }, 2000);

        }

        // Do the job here that tracks the progress.
        // Usually, this should be in a
        // worker thread
        // To show progress, update PROGRESS_CURRENT and update the notification with:
        // mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        // notificationManager.notify(notificationId, mBuilder.build());


    }
}