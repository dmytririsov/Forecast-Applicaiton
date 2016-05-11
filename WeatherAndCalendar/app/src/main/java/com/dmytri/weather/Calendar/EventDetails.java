package com.dmytri.weather.Calendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dmytri.weather.R;

public class EventDetails extends AppCompatActivity {
    public EventDetails() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
    }
}
