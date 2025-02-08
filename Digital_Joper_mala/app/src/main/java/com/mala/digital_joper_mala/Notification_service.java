package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Notification_service extends Service {

    //declaration ---------------------------------------------

    private static final String CHANNEL_ID = "my_notification";

    //declaration ---------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();

        create_notification_channel();
        start_foreg_notification();
    }

    private void create_notification_channel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);

            if (manager != null) manager.createNotificationChannel(channel);

        }

    }

    @SuppressLint("ForegroundServiceType")
    private void start_foreg_notification(){

        String bangla_date = BanglaDateUtils.getBanglafullDate();

        Intent intent = new Intent(this, HomeAllMalaActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int pending = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, pending);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)

                    .setContentTitle("তারিখ: " + bangla_date)
                    .setContentText("জপ মালা ব্যাকগ্রাউন্ডে চলছে ।")
                    .setSmallIcon(R.drawable.jopa_icon)
                    .build();

            startForeground(1, notification);
        }



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

} //public class ==============================
