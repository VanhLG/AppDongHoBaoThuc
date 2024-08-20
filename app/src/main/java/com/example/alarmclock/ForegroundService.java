package com.example.alarmclock;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

public class ForegroundService extends Service {
    Uri alarmUri;
    SharedPreferences sharedPreferences;
    private static final String CHANNEL_ID1 = "channel_id";
    //private LocalBinder localBinder = new LocalBinder();
    public class LocalBinder extends Binder
    {
        ForegroundService getService()
        {
            return ForegroundService.this;
        }
    }
    Ringtone ringtone;
    @Override
    public void onCreate() {
        createNotificationChannel();
        sharedPreferences =getSharedPreferences("com.example.alarmclock", Context.MODE_PRIVATE);
        Intent intent = new Intent(this, AlarmNotification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, CHANNEL_ID1)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Đã đến thời gian báo thức")
                        .setContentText("Mời ban giải toán để tắt báo thức!")
                        .setAutoCancel(true)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setFullScreenIntent(pendingIntent,true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();
        startForeground(getNextId(), notification.build());
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        ringtone.stop();
        super.onDestroy();
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String d = getString(R.string.d);
            int a = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID1,name,a);
            channel.setDescription(d);
            NotificationManager nt = getSystemService(NotificationManager.class);
            nt.createNotificationChannel(channel);
        }
    }
    private int getNextId()
    {
        return (int) new Date().getTime();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
