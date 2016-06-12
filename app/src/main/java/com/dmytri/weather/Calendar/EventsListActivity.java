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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.events_list_acitivity);
        Intent intent = getIntent();
        ListView view = (ListView) findViewById(R.id.event_list);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "Hey bro, you click on item: " + mEventsDescriptions.get(position));
            }
        });
        final int position = intent.getIntExtra(CalendarFragment.POSITION_INTENT, DEFAULT_VALUE);
        final String month = intent.getStringExtra(CalendarFragment.MONTH_INTENT);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item, mEventsDescriptions);
        view.setAdapter(arrayAdapter);
    }

    private List<String> updateList() {
        List<EventsModel> models = new Select(new String[]{"Id, Event_description, Event_spinner"}).from(EventsModel.class).execute();
        List<String> eventDescriptions = new ArrayList<>();
        for (EventsModel model : models) {
            eventDescriptions.add(model.getId().toString() + " "
                    + model.event_description + " "
                    + model.event_spinner);
        }
        return eventDescriptions;
    }
}
