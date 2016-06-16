package com.dmytri.weather.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class AlarmAlertDialog extends Activity {
    private static final String TAG = AlarmAlertDialog.class.getSimpleName();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, AlarmService.class);
        startBind();
        displayAlert();
    }
    protected ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected");
            AlarmService binderService = (AlarmService) binder;
            binderService.stop();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    public void startBind() {
        bindService(intent, mServerConn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    public void stopBind() {
        stopService(new Intent(this, AlarmService.class));
        unbindService(mServerConn);
    }

    private void displayAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Disable alarm").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AlarmAlertDialog.this, EventsListActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
