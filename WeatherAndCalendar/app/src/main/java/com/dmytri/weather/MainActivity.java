package com.dmytri.weather;


import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dmytri.weather.Calendar.CalendarFragment;
import com.dmytri.weather.Weather.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final WeatherFragment weatherFragment = new WeatherFragment();
        final CalendarFragment calendarFragment = new CalendarFragment();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabCalendar = actionBar.newTab();
        tabCalendar.setText("Calendar");
        tabCalendar.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                Log.d(TAG, "Calendar");
                ft.add(R.id.container, calendarFragment);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                ft.remove(calendarFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }
        });
        actionBar.addTab(tabCalendar);

        ActionBar.Tab tabWeather = actionBar.newTab();
        tabWeather.setText("Weather");
        tabWeather.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                Log.d(TAG, "Weather");
                ft.add(R.id.container, weatherFragment);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                ft.remove(weatherFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }
        });
        actionBar.addTab(tabWeather);
    }

}
