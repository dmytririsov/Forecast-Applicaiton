package com.dmytri.weather.Weather;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

import com.dmytri.weather.R;
import com.dmytri.weather.Weather.Models.Model;
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
    private static final int TIME_FROM_MILLISECONDS = 1000;
    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/";

    private Button mUpdateButton;
    private Button mChangeCity;
    private Typeface mWeatherFont;
    private TextView mCityField;
    private TextView mUpdateField;
    private TextView mDetailsField;
    private TextView mCurrentTemperatureField;
    private TextView mWeatherIcon;
    private long current_time = System.currentTimeMillis();


    private final Handler mHandler;

    public WeatherFragment() {
        mHandler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "City - " + CityPreference.getInstance(getActivity()).getCity());
        mWeatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons.ttf");
    }

    @Nullable
    @Override/* Inflate layout */
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
                Toast.makeText(getActivity(), "Data updated", Toast.LENGTH_SHORT).show();

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
        getJson(CityPreference.getInstance(getActivity()).getCity());
        return rootView;
    }

    public void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change city");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
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

    //This method performs function update
    public void updateWeatherData(final String city) {
        getJson(city);
        if (city != null && !city.isEmpty()) {
            CityPreference.getInstance(getActivity()).setCity(city);
        }
    }

    private void getJson (String city) {
        //final DateFormat timeFormat = DateFormat.getTimeInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
        calendar.setTimeInMillis(current_time);
        mUpdateField.setText("Last update: " + sdf.format(calendar.getTime()));
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(OPEN_WEATHER_MAP_API)
                .build();
        final String query = city;
        final String appid = "9c4a47ff53dff3ea499b0bd0f239df78";
        final String units = "metric";
        GetJson service = retrofit.create(GetJson.class);
        Call<Model> call = service.getWeatherReport(query, appid, units);
        Log.d(TAG, "Need query - " + OPEN_WEATHER_MAP_API +"data/2.5/weather?q=" + city + "&appid=9c4a47ff53dff3ea499b0bd0f239df78&units=metric");
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

    private void setWeatherIcon(int actulalId, long sunrise, long sunset) {
        int id = actulalId / 100;
        String icon = "";
        long currentTime = new Date().getTime();
        final Activity activity = getActivity(); //TODO: string magic numbers
        if (actulalId == 800) {
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
        //updateWeatherData(CityPreference.getInstance(getActivity()).getCity());
        Log.d(TAG, "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop");
    }
}
