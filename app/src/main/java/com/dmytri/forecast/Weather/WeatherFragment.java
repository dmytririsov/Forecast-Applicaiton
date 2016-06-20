package com.dmytri.forecast.Weather;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmytri.forecast.Preference;
import com.dmytri.weather.R;
import com.dmytri.forecast.Weather.Models.Model;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/";
    private static final String APPID = "9c4a47ff53dff3ea499b0bd0f239df78";
    private static final String UNITS = "metric";
    private static final String WEATHER_ICONS = "fonts/weathericons.ttf";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE = "US/Central";
    private static final int TIME_FROM_MILLISECONDS = 1000;
    private static final int SUNNY_WEATHER = 800;

    private Button mUpdateButton;
    private Button mChangeCity;
    private Typeface mWeatherFont;
    private TextView mCityField;
    private TextView mUpdateField;
    private TextView mDetailsField;
    private TextView mCurrentTemperatureField;
    private TextView mWeatherIcon;
    private long current_time = System.currentTimeMillis();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherFont = Typeface.createFromAsset(getActivity().getAssets(), WEATHER_ICONS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView");
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
                Log.d(TAG, "on click update");
                getJson(Preference.getInstance(getActivity()).getCity());
                Toast.makeText(getActivity(), R.string.toast_data_updated, Toast.LENGTH_SHORT).show();

            }
        });
        mChangeCity = (Button) rootView.findViewById(R.id.change_city);
        mChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click change city");
                showInputDialog();
            }
        });
        getJson(Preference.getInstance(getActivity()).getCity());
        return rootView;
    }

    public void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.forecast_title_change_city);
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(R.string.forecast_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().isEmpty()) {
                    changeCity(input.getText().toString());
                    Log.d(TAG, "city updated");
                } else {
                    Toast.makeText(getActivity(),
                            getActivity().getString(R.string.city_not_found),
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "city not found");
                }
            }
        });
        builder.show();
    }

    public void updateWeatherData(final String city) {
        getJson(city);
        if (city != null && !city.isEmpty()) {
            Preference.getInstance(getActivity()).setCity(city);
        }
    }

    private void getJson (String city) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(current_time);
        mUpdateField.setText("Last update: " + sdf.format(calendar.getTime()));
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(OPEN_WEATHER_MAP_API)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<Model> call = service.getWeatherReport(city, APPID, UNITS);
        Log.d(TAG, "Need query - " + OPEN_WEATHER_MAP_API +"data/2.5/weather?q=" + city + "&appid=9c4a47ff53dff3ea499b0bd0f239df78&UNITS=metric");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                try {
                    String city = response.body().getName();
                    String country = response.body().getSys().getCountry();
                    String status = response.body().getWeather().get(0).getDescription();
                    String humidity = response.body().getMain().getHumidity().toString();
                    String pressure = response.body().getMain().getPressure().toString();
                    String wind = response.body().getWind().getSpeed().toString();
                    Double temp = response.body().getMain().getTemp();
                    Long sunrise = (long)response.body().getSys().getSunrise();
                    Long sunset = (long)response.body().getSys().getSunset();
                    String details =  response.body().getWeather().get(0).getId().toString();
                    Integer updatedDetails = Integer.parseInt(details);


                    mCityField.setText(city.toUpperCase() + ", " + country);
                    mCurrentTemperatureField.setText(String.format("%.1f", temp) + "℃"); //setting up details
                    setWeatherIcon(updatedDetails,
                            sunrise * TIME_FROM_MILLISECONDS,
                            sunset * TIME_FROM_MILLISECONDS);
                    mDetailsField.setText(
                                    "\n" + "Status: " + status +
                                    "\n" + "Humidity: " + humidity + "%" +
                                    "\n" + "Pressure: " + pressure + "hPa" +
                                    "\n" + "Wind: " + wind + "m/s");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        long currentTime = new Date().getTime();
        final Activity activity = getActivity();
        if (actualId == SUNNY_WEATHER) {
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = activity.getString(R.string.weather_sunny);
            } else {
                icon = activity.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = activity.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = activity.getString(R.string.weather_drizzle);
                    break;
                case 5:
                    icon = activity.getString(R.string.weather_rainy);
                    break;
                case 6:
                    icon = activity.getString(R.string.weather_snowy);
                    break;
                case 7:
                    icon = activity.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = activity.getString(R.string.weather_cloudy);
                    break;
            }
        }
        mWeatherIcon.setText(icon);
    }


    public void changeCity(String city) {
        updateWeatherData(city);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateWeatherData(Preference.getInstance(getActivity()).getCity());
        Log.d(TAG, "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop");
    }
}
