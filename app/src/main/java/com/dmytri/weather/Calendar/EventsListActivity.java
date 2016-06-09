package com.dmytri.weather.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.dmytri.weather.R;
import com.dmytri.weather.Weather.Models.Model;

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
    private List<CalendarEvent> mEvents;
    private EventsModel eventsModel;
    private String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.events_list_acitivity);
        mEvents = new ArrayList<>();
        Intent intent = getIntent();
        ListView view = (ListView) findViewById(R.id.event_list);
        final int dayOfYear = intent.getIntExtra(DAY_OF_YEAR_KEY, DEFAULT_VALUE);
        final int position = intent.getIntExtra("position", DEFAULT_VALUE);
        final String month = intent.getStringExtra("month");
        mAddEventButton = (Button) findViewById(R.id.button_add_event);
        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click add event");
                Intent intent = new Intent(v.getContext(), EditEventActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("month", month);
                v.getContext().startActivity(intent);
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item, calculateEventsInDay(dayOfYear));
        view.setAdapter(arrayAdapter);


    }

    @Nullable
    private List<String> calculateEventsInDay(int dayOfYear) {
       /*return new Select()
               .from(EventsModel.class)
               .where("Event_description", eventsModel.getId())
               .executeSingle();*/
        List<String> events = new ArrayList<>();
        for (CalendarEvent event: mEvents) {
            if (dayOfYear == event.getDayOfYear()) {
                events.add(event.getEventDetails());
            }
        }
        events.add("Go");
        return events;
    }

    private List<Model> updateList (View view) {
        Select select = new Select();
        List<EventsModel> eventsModels = select.all().from(EventsModel.class).execute();
        StringBuilder builder = new StringBuilder();
        for (EventsModel eventsModel : eventsModels) {
            builder.append("Description: ")
                    .append(eventsModel.event_description);
        }
        return null;
    }
}
