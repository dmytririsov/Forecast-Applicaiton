package com.dmytri.forecast;
/*
    This class is responsible for set default city if user not chosen city.
    Now as default city - Kiev
*/

import android.app.Activity;
import android.content.SharedPreferences;

import com.dmytri.forecast.Calendar.CalendarFragment;

public class Preference {

    private static final String CITY_KEY = "city";

    private SharedPreferences mPreferences;
    private static Preference sInstance;
    private String mCity;
    private int mPosition;
    private String mMonth;

    public static Preference getInstance(Activity activity) {

        if (sInstance == null) {
            sInstance = new Preference(activity);
        }
        return sInstance;
    }

    private Preference(Activity activity) {
        mPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        if (mCity == null) {
            mCity = mPreferences.getString(CITY_KEY, "Kiev,UA");
        }
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
        mPreferences.edit().putString(CITY_KEY, city).apply();
    }
    public int getCurrentPosition() {
        mPosition = mPreferences.getInt(CalendarFragment.POSITION_INTENT, 0);
        return mPosition;
    }

    public void setCurrentPosition(int position) {
        mPosition = position;
        mPreferences.edit().putInt(CalendarFragment.POSITION_INTENT, position).apply();
    }

    public String getCurrentMonth() {
        mMonth = mPreferences.getString(CalendarFragment.MONTH_INTENT, "null");
        return mMonth;
    }

    public void setCurrentMonth(String month) {
        mMonth = month;
        mPreferences.edit().putString(CalendarFragment.MONTH_INTENT, month).apply();
    }
}
