package com.onlinemadrasa.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.onlinemadrasa.HomeActivity;
import com.onlinemadrasa.R;

import static com.onlinemadrasa.utils.NotificationUtils.CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = "Firebase";
    private Context context;
    private PrefManager session;
    private Boolean isLoggedIn;
    private Intent intent;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = remoteMessage.getNotification().getBody();
        //String orderId = remoteMessage.getData().get("order_id");
        NotificationUtils.showNotification(getApplicationContext(), "Online Madrasa", message);
        context = getApplicationContext();


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("message"));
            Log.d(TAG,"Title  : "+remoteMessage.getNotification().getTitle());


            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Intent i = new  Intent(context, HomeActivity.class);
                    i.putExtra("TITLE",remoteMessage.getNotification().getTitle());
                    i.putExtra("MESSAGE",remoteMessage.getNotification().getBody());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });

            if(intent!=null) {
                intent = new  Intent(context, HomeActivity.class);
                intent.putExtra("TITLE",remoteMessage.getNotification().getTitle());
                intent.putExtra("MESSAGE",remoteMessage.getNotification().getBody());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                //String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_quran)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(0, builder.build());
            }
        }
    }

}
