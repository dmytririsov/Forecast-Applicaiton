package com.dmytri.weather.Weather;
/*
    This class is responsible for set default city if user not chosen city.
    Now as default city - Kiev
*/

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {

    private static final String CITY_KEY = "city";

    private SharedPreferences mPreferences;
    private static CityPreference sInstance;
    private String mCity;

    public static CityPreference getInstance(Activity activity) {
        if (sInstance == null) {
            sInstance = new CityPreference(activity);
        }
        return sInstance;
    }

    private CityPreference(Activity activity) {
        mPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        if (mCity == null) {
            mCity = mPreferences.getString("city", "Kiev,UA"); //if not chosen city, as default Kiev
        }
        return mCity;
    }

    void setCity(String city) {
        mCity = city;
        mPreferences.edit().putString(CITY_KEY, city).apply();
    }
}
