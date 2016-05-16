package com.dmytri.weather.Calendar;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dmytri.weather.R;

public class EditEventActivity extends AppCompatActivity {


    private Button mEventButtonAlarm;
    private Button mEventButtonSubmit;
    private TextView mEventDate;
    private EditText mEventEditText;
    private CalendarEvent mCalendarEvent;

    private final String[] mEventType = {"Meeting", "Birthday", "Reminder"};
    private final static String TAG = EditEventActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
       // CalendarEvent mCalendarEvent = new CalendarEvent();
        setContentView(R.layout.event_details);
        mEventButtonAlarm = (Button)findViewById(R.id.button_set_alarm);
        mEventButtonSubmit = (Button)findViewById(R.id.event_button_submit);
        mEventDate = (TextView)findViewById(R.id.event_details_date);
        String month = intent.getStringExtra("month");
        int position = intent.getIntExtra("position", 1);
        mEventDate.setText(month + ", " +  position);
        mEventEditText = (EditText) findViewById(R.id.event_details_edit_text);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mEventType);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        final Spinner spinner = (Spinner) findViewById(R.id.event_details_spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Event details"); //for click in spinner event
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}


