package com.dmytri.weather.Weather;

/*
    This class is responsible for main Activity
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmytri.weather.R;

public class WeatherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new WeatherFragment()).commit();
    }
}
