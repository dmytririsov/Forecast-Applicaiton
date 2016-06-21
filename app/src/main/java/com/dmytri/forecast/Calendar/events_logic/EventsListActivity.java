package com.dmytri.forecast.Calendar.events_logic;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.dmytri.forecast.Calendar.CalendarFragment;
import com.dmytri.forecast.Preference;
import com.dmytri.weather.R;
import com.dmytri.forecast.Calendar.data.EventsModel;

import java.util.ArrayList;
import java.util.List;


public class EventsListActivity extends AppCompatActivity {

    public static final String DAY_OF_YEAR_KEY = "dayOfYear";
    private static final String TAG = EventsListActivity.class.getSimpleName();

    private Button mAddEventButton;
    private List <String> mEventsDescriptions;
    private List <String> mIdFromDB;
    private String eventDate;
    private String month;
    private int position;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.events_list_acitivity);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent.getStringExtra(CalendarFragment.MONTH_INTENT) != null
                && !intent.getStringExtra(CalendarFragment.MONTH_INTENT).isEmpty()) {
            month = intent.getStringExtra(CalendarFragment.MONTH_INTENT);
            position = intent.getIntExtra(CalendarFragment.POSITION_INTENT, 1);
            Preference.getInstance(this).setCurrentMonth(month);
            Preference.getInstance(this).setCurrentPosition(position);
        }
        else {
            month = Preference.getInstance(this).getCurrentMonth();
            position = Preference.getInstance(this).getCurrentPosition();
        }
        ListView view = (ListView) findViewById(R.id.event_list);
        assert view != null;
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "Hey bro, you click on item: " + mIdFromDB.get(position));
                String temp  = mIdFromDB.get(position);
                String strEnd = temp.replaceAll("[^\\d]","");
                new Delete().from(EventsModel.class).where("Id = ?", strEnd).execute();
                Toast.makeText(view.getContext(), "Event was removed", Toast.LENGTH_SHORT).show();
            }
        });
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
            List<EventsModel> eventDateFromDB = EventsModel.getDateDescription(eventDate);
            if (eventDateFromDB.size() != 0) {
                List<String> eventDescriptionsFrom = new ArrayList<>();
                for (EventsModel model : eventDateFromDB) {
                    eventDescriptionsFrom.add(
                                    "Event date: " + model.event_date + "\n" +
                                    "Event description: " + model.event_description + "\n" +
                                    "Event spinner: " + model.event_spinner);
                    mEventsDescriptions = eventDescriptionsFrom;
                }
            }

            List<EventsModel> idFromDB = EventsModel.getID(eventDate);
            if (idFromDB.size() != 0) {
                List<String> eventIdFrom = new ArrayList<>();
                for (EventsModel model : idFromDB) {
                    eventIdFrom.add("ID: " + model.getId().toString());
                    mIdFromDB = eventIdFrom;
                }
            }
            if (mEventsDescriptions == null) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item);
                view.setAdapter(arrayAdapter);
            }
            else {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.events_list_item, mEventsDescriptions);
                view.setAdapter(arrayAdapter);
            }
    }

}
