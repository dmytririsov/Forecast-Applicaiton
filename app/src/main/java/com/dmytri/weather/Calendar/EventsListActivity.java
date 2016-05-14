package com.dmytri.weather.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.dmytri.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytri on 15.05.2016.
 */
public class EventsListActivity extends Activity {

    private static final String TAG = EventsListActivity.class.getSimpleName();

    public static final String DAY_OF_YEAR_KEY = "dayOfYear";

    private Button mAddEventButton;
    private List<CalendarEvent> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.events_list_acitivity);
        mEvents = new ArrayList<>();
        mAddEventButton = (Button) findViewById(R.id.button_add_event);
        ListView view = (ListView) findViewById(R.id.event_list);
        int dayOfYear = getIntent().getIntExtra(DAY_OF_YEAR_KEY, -1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item, calculateEventsInDay(dayOfYear));
        view.setAdapter(arrayAdapter);
    }

    @Nullable
    private List<String> calculateEventsInDay(int dayOfYear) {
        List<String> events = new ArrayList<>();
        for (CalendarEvent event: mEvents) {
            if (dayOfYear == event.getDayOfYear()) {
                events.add(event.getEventDetails());
            }
        }
        events.add("Hey");
        events.add("Goo");
        return events;
    }

}
