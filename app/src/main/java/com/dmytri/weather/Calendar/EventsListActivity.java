package com.dmytri.weather.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.dmytri.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytri on 15.05.2016.
 */
public class EventsListActivity extends Activity {

    public static final String DAY_OF_YEAR_KEY = "dayOfYear";
    private static final String TAG = EventsListActivity.class.getSimpleName();
    private static final int DEFAULT_VALUE = 1;

    private Button mAddEventButton;
    private List <String> mEventsDescriptions;
    private List<EventsModel> models;
    private String eventDate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        Intent intent = getIntent();
        setContentView(R.layout.events_list_acitivity);
        ListView view = (ListView) findViewById(R.id.event_list);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "Hey bro, you click on item: " + mEventsDescriptions.get(position));
                String temp  = mEventsDescriptions.get(position);
                String strEnd=temp.replaceAll("[^\\d]","");
                new Delete().from(EventsModel.class).where("Id = ?", strEnd).execute();
                onCreate(savedInstanceState);
            }
        });
        final int position = intent.getIntExtra(CalendarFragment.POSITION_INTENT, DEFAULT_VALUE);
        final String month = intent.getStringExtra(CalendarFragment.MONTH_INTENT);
        eventDate =  month + ", " +  Integer.toString(position);

        mAddEventButton = (Button) findViewById(R.id.button_add_event);
        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click add event");
                Intent intent = new Intent(v.getContext(), EditEventActivity.class);
                intent.putExtra(CalendarFragment.POSITION_INTENT, position);
                intent.putExtra(CalendarFragment.MONTH_INTENT, month);
                v.getContext().startActivity(intent);
            }
        });
        mEventsDescriptions = updateList();
        if (mEventsDescriptions.isEmpty()) {
            mEventsDescriptions.add("Descryption is missing");
        }
        try{
            List<EventsModel> eventDateFromDB =
                new Select(new String[]{"Id, Event_description, Event_spinner"})
                        .from(EventsModel.class)
                        .where("Event_date = ?", eventDate)
                        .execute();
            List<String> eventDescriptionsFrom = new ArrayList<>();
            for (EventsModel model : eventDateFromDB) {
                eventDescriptionsFrom.add(
                        "ID: " + model.getId().toString() + "\n" +
                                "Event description: " + model.event_description + "\n" +
                                "Event spinner: " + model.event_spinner);
                mEventsDescriptions = eventDescriptionsFrom;
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item, mEventsDescriptions);
        view.setAdapter(arrayAdapter);
        Toast.makeText(getApplicationContext(), "To remove click on the item", Toast.LENGTH_SHORT).show();
    }

    private List<String> updateList() {
        List<EventsModel> models = new Select(new String[]{"Id, Event_description, Event_spinner"}).from(EventsModel.class).execute();

        List<String> eventDescriptions = new ArrayList<>();
        for (EventsModel model : models) {
            eventDescriptions.add(
                    "ID: " + model.getId().toString() + "\n" +
                    "Event description: " + model.event_description + "\n" +
                    "Event spinner: " + model.event_spinner);
        }
        return eventDescriptions;
    }


}
