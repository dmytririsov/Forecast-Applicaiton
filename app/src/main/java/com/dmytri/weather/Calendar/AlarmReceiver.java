package com.dmytri.weather.Calendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        AlarmActivity inst = AlarmActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");
        ComponentName comp = new ComponentName(context.getPackageName(), AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        Intent intent1 = new Intent(context, AlarmAlertDialog.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent1);
        setResultCode(Activity.RESULT_OK);
    }
}
