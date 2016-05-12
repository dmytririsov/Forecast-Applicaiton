package com.dmytri.weather.Weather;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dmytri.weather.R;

import org.json.JSONObject;


public class GetWeatherTask extends AsyncTask<String, JSONObject, JSONObject> {
    private static final String TAG = "GetWeatherTask: ";
    private Context context;
    private Listener listener;

    public GetWeatherTask(Context context,Listener listener) {

        this.context = context;
        this.listener = listener;
    }
    public interface Listener{
        void getJson(JSONObject jsonObject);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        final JSONObject jsonObject = RemoteFetch.getJSON(params[0]);
        Log.d(TAG, jsonObject.toString());
        listener.getJson(jsonObject);
        return jsonObject;
    }

}

