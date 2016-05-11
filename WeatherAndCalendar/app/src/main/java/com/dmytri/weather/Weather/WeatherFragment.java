package com.dmytri.weather.Weather;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dmytri.weather.R;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private static final int TIME_TO_MILLISECONDS = 1000;

    private Button mUpdateButton;
    private Typeface mWeatherFont;

    private TextView mCityField;
    private TextView mUpdateField;
    private TextView mDetailsField;
    private TextView mCurrentTemperatureField;
    private TextView mWeatherIcon;

    private final Handler mHandler;

    public WeatherFragment() {
        mHandler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons.ttf");
        updateWeatherData(CityPreference.getInstance(getActivity()).getCity());
    }

    @Nullable
    @Override/* Inflate layout */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        mCityField = (TextView) rootView.findViewById(R.id.city_field);
        mUpdateField = (TextView) rootView.findViewById(R.id.update_city);
        mDetailsField = (TextView) rootView.findViewById(R.id.details_field);
        mCurrentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        mWeatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        mWeatherIcon.setTypeface(mWeatherFont);
        mUpdateButton = (Button) rootView.findViewById(R.id.btnUpdate);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click");
                updateWeatherData(CityPreference.getInstance(getActivity()).getCity());
            }
        });
        return rootView;
    }

    //This method performs function update
    public void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(city); //Get currently city
                if (json == null) { //if JSON empty, start new handler thread which display error
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else // call renderWeather method in another thread
                {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    //This method performs render json data from input JSONObject

    @SuppressLint("SetTextI18n")
    // TODO remove all string to resources
    private void renderWeather(JSONObject json) {
        Log.d(TAG, "start weather rendering");
        try {
            mCityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " + json.getJSONObject("sys").getString("country"));
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            mDetailsField.setText(
                    details.getString(getString(R.string.text_view_description_field)).toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + "hPa");
            mCurrentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "℃"); //setting up details
            DateFormat df = DateFormat.getDateTimeInstance(); //current time
            String updateOn = df.format(new Date(json.getLong("dt") * TIME_TO_MILLISECONDS));
            mUpdateField.setText("Last update: " + updateOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * TIME_TO_MILLISECONDS,
                    json.getJSONObject("sys").getLong("sunset") * TIME_TO_MILLISECONDS);
            Log.d(TAG, "Weather was rendered");
        } catch (Exception e) {
            Log.e(TAG, "Field not found on JSON DATA");
        }
    }

    private void setWeatherIcon(int actulalId, long sunrise, long sunset) {
        int id = actulalId / 100;
        String icon = "";
        long currentTime = new Date().getTime();
        if (actulalId == 800) {
            if (currentTime >= sunrise && currentTime < sunset)
                icon = getActivity().getString(R.string.weather_sunny);
            else
                icon = getActivity().getString(R.string.weather_clear_night);
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
            }
        }
        mWeatherIcon.setText(icon);
    }

    public void changeCity(String city) {
        updateWeatherData(city);
    }
}
