package com.dmytri.weather.Weather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dmytri.weather.R;

import org.json.JSONObject;


public class GetWeatherTask extends AsyncTask<String, JSONObject, JSONObject> {
    private static final String TAG = "GetWeatherTask: ";

    private Context mContext;
    private OnLoadFinishedListener mOnLoadFinishedListener;

    public interface OnLoadFinishedListener {
        void onLoadFinished(JSONObject json);
    }

    public GetWeatherTask(Context context, OnLoadFinishedListener listener) {
        mContext = context;
        mOnLoadFinishedListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    @Nullable
    protected JSONObject doInBackground(String... params) {
        final JSONObject jsonObject = RemoteFetch.loadJSON(params[0]);
        if (jsonObject != null) {
            Log.d(TAG, jsonObject.toString());
        } else {
            Log.d(TAG, "JSON == null");
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        if (json != null && mOnLoadFinishedListener != null) {
            mOnLoadFinishedListener.onLoadFinished(json);
        } else {
            Toast.makeText(mContext,
                    mContext.getString(R.string.place_not_found),
                    Toast.LENGTH_SHORT).show();
        }
    }

}

