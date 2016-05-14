package com.dmytri.weather.Calendar;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private TextView mEventDate;
    private EditText mEventEditText;
    private final String[] mEventType = {"Meeting", "Birthday", "Reminder"};
    private final static String TAG = EditEventActivity.class.getSimpleName();

    public EditEventActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
        mEventButtonAlarm = (Button)findViewById(R.id.button_set_alarm);
        mEventDate = (TextView)findViewById(R.id.event_details_date);
        //TODO: Set current date (day, month, year) from input date
        //mEventDate.setText();
        mEventEditText = (EditText) findViewById(R.id.event_details_edit_text);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 2);
        int id = intent.getIntExtra("id", 3);
        String parent = intent.getStringExtra("parent");
        Log.d(TAG, "pos: " + pos);
        Log.d(TAG, "id: " + pos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mEventType);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.event_details_spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Event details"); //for click in spinner event
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Put selected item in and edittext db

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mEventButtonAlarm.setOnClickListener(new View.OnClickListener() { // add alarm
            @Override
            public void onClick(View v) {

            }
        });
    }

}


