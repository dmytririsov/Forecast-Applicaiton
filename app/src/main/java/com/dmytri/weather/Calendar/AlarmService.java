package com.dmytri.weather.Calendar;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dmytri.weather.R;


public class AlarmService extends IntentService {

    private static final String TAG = AlarmService.class.getSimpleName();
    private NotificationManager alarmNotificationManager;
    private Ringtone ringtone;
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        AlarmService getService() {
            // Return this instance of LocalService so clients can call public methods
            return AlarmService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("Tap to stop!");
    }

    public void stop() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }
    private void sendNotification(String msg) {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmActivity.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Alarm")
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.skip_next)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg);


        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.d(TAG, "Notification sent.");

    }
}
