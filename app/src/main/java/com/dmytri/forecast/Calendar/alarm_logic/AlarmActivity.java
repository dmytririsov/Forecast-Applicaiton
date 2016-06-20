package com.dmytri.forecast.Calendar.alarm_logic;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.dmytri.forecast.Calendar.CalendarFragment;
import com.dmytri.weather.R;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = AlarmActivity.class.getSimpleName();

    private AlarmManager alarmManager;
    private TimePicker timePicker;
    private PendingIntent pendingIntent;
    private TextView alarmTextView;
    private ToggleButton toggleButton;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggleButton = (ToggleButton)findViewById(R.id.alarmToggle);
        timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView)findViewById(R.id.alarmText);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d(TAG, "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d(TAG, "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    public static class AlarmReceiver extends WakefulBroadcastReceiver {


        public AlarmReceiver() {
        }

        private final String TAG = AlarmReceiver.class.getSimpleName();

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            ComponentName comp = new ComponentName(context.getPackageName(), AlarmService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
            Intent intent1 = new Intent(context, AlarmAlertDialog.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(intent1);
            setResultCode(Activity.RESULT_OK);
        }
    }
}

