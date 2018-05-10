package id.mzennis.m_health.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import id.mzennis.m_health.MainActivity;
import id.mzennis.m_health.R;

/**
 * Created by meta on 10/05/18.
 */

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Yeay! it's time to breakfast, \nbefore you eat do not forget to enter glucose level! ^^");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String msg) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                1, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = this.getResources();
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker("Alarm")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Alarm")
                .setContentText(msg);
        Notification n = builder.build();

        nm.notify(2, n);
    }
}