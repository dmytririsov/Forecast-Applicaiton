package com.dmytri.weather.calendar.events_logic;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmytri.weather.R;
import com.dmytri.weather.calendar.data.EventsModel;

import java.util.List;

public class AsyncRequestForDB extends AsyncTask<Void, Void, List<EventsModel>> {
    private ListView listView;
    private Context context;

    public AsyncRequestForDB(ListView listView, Context context) {
        super();
        this.listView = listView;
        this.context = context;
    }

    @Override
    protected List<EventsModel> doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<EventsModel> list) {
        super.onPostExecute(list);

        if (list!=null&&list.size() > 0 && listView != null&&context!=null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.events_list_item);
            listView.setAdapter(arrayAdapter);
        }
    }


}
