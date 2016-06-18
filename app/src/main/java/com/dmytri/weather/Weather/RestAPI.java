package com.dmytri.weather.weather;


import com.dmytri.weather.weather.models.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestAPI {
    @GET("data/2.5/weather")
    Call<Model> getWeatherReport(@Query("q") String city, @Query("appid") String appid, @Query("units") String units);
}
