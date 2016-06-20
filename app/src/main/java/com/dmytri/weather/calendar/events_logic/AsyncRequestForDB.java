/*
package com.dmytri.weather.calendar.events_logic;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.dmytri.weather.R;
import com.dmytri.weather.calendar.data.EventsModel;

import java.util.ArrayList;
import java.util.List;

public class AsyncRequestForDB extends AsyncTask<Void, Void, List<EventsModel>> {
    private ListView listView;
    private Context context;
    private String eventDate;
    private List <String> mEventsDescriptions;

    public AsyncRequestForDB(ListView listView, Context context, String eventDate) {
        super();
        this.listView = listView;
        this.context = context;
        this.eventDate = eventDate;
    }

    @Override
    protected List<EventsModel> doInBackground(Void... params) {
        List<EventsModel> eventDateFromDB = new Select(new String[]{"Id, Event_description, Event_spinner, Event_date"})
                .from(EventsModel.class)
                .where("Event_date = ?", eventDate)
                .executeSingle();
        List<String> eventDescriptionsFrom = new ArrayList<>();
        for (EventsModel model : eventDateFromDB) {
            eventDescriptionsFrom.add(
                            "Event date: " + model.event_date + "\n" +
                            "Event description: " + model.event_description + "\n" +
                            "Event spinner: " + model.event_spinner);
            mEventsDescriptions = eventDescriptionsFrom;
        }
        if (eventDateFromDB != null) {
            return eventDateFromDB;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<EventsModel> list) {
        super.onPostExecute(list);

        if (list!=null&&list.size() > 0 && listView != null&&context!=null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.events_list_item, mEventsDescriptions);
            listView.setAdapter(arrayAdapter);
        }
        else {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.events_list_item);
            listView.setAdapter(arrayAdapter);
        }

    }


}
*/
