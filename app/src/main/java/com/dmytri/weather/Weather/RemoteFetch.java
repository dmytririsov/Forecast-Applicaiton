package com.dmytri.weather.Weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;

import android.support.annotation.Nullable;
import android.util.Log;

public class RemoteFetch {

    private static final String TAG = RemoteFetch.class.getSimpleName();
    private static final String OPEN_WEATHER_MAP_API
            = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=9c4a47ff53dff3ea499b0bd0f239df78&units=metric";


    @Nullable
    public static JSONObject getJSON(String city) {
        Log.d(TAG, "start to receiving JSON");
        try {
        URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));//Open API and connection
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            Log.d(TAG, connection.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Get input stream

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while((tmp = reader.readLine()) != null)//Append jsno
                json.append(tmp).append("\n");
            reader.close();


            JSONObject data = new JSONObject(json.toString());
            int returnCode = data.getInt("cod");
            //if request not successful return 404
            if(returnCode != 200){
                Log.d(TAG, "bad response code" + String.valueOf(returnCode));
                return null;
            }
            Log.d(TAG, "response: " + json.toString());
            return data;
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}
