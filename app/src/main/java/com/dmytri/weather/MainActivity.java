package com.dmytri.weather;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.dmytri.weather.Calendar.CalendarFragment;
import com.dmytri.weather.Weather.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CALENDAR_FRAGMENT_KEY = "calendarFragment";
    private static final String WEATHER_FRAGMENT_KEY = "weatherFragment";
    private static final String TAB_INDEX_KEY = "tabIndex";

    private CalendarFragment mCalendarFragment;
    private WeatherFragment mWeatherFragment;
    private int mTabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main_activity);
        setupFragments(savedInstanceState);
        setupActionBar(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAB_INDEX_KEY, mTabPosition);
    }

    private void setupFragments(Bundle savedInstance) {
        if (mCalendarFragment == null) {
            mCalendarFragment = new CalendarFragment();
        }

        if (mWeatherFragment == null) {
            mWeatherFragment = new WeatherFragment();
        }
    }

    private void setupActionBar(final Bundle savedInstance) {

        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            ActionBar.Tab tabCalendar = actionBar.newTab();
            tabCalendar.setText("Calendar");
            tabCalendar.setTabListener(new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                    Log.d(TAG, "Calendar");
                    mTabPosition = 0;
                    ft.replace(R.id.container, mCalendarFragment);
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                    // ignore
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                    // ignore
                }
            });
            actionBar.addTab(tabCalendar);

            ActionBar.Tab tabWeather = actionBar.newTab();
            tabWeather.setText("Weather");
            tabWeather.setTabListener(new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                    Log.d(TAG, "Weather");
                    mTabPosition = 1;
                    ft.replace(R.id.container, mWeatherFragment);
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab,
                                            android.support.v4.app.FragmentTransaction ft) {
                    // ignore
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab,
                                            android.support.v4.app.FragmentTransaction ft) {
                    // ignore
                }
            });
            actionBar.addTab(tabWeather);

            // TODO investigate how to improve selection of tabs
            if (savedInstance != null) {
                mTabPosition = savedInstance.getInt(TAB_INDEX_KEY);
                if (mTabPosition == 0) {
                    actionBar.selectTab(tabCalendar);
                } else {
                    actionBar.selectTab(tabWeather);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error");
            e.printStackTrace();
        }
    }

}
