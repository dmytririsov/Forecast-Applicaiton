package com.dmytri.weather.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dmytri.weather.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dmytri on 11.05.2016.
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

    private String mNameOfMonth;
    private ImageButton mNextMonthButton;
    private ImageButton mPrevMonthButton;
    private Map<String, CalendarDaysAdapter> mDayAdapters;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "on create view");
        Calendar calendarInstance = Calendar.getInstance();
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        final GridView grid = (GridView) rootView.findViewById(R.id.calendar_days);

        TextView mNameOfMonthTextView = (TextView)rootView.findViewById(R.id.name_of_month);
        mNameOfMonth = calendarInstance.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        mNameOfMonthTextView.setText(mNameOfMonth.toUpperCase() + " " + calendarInstance.get(Calendar.YEAR));

        final CalendarDaysAdapter adapter = new CalendarDaysAdapter(getActivity(), calendarInstance.get(Calendar.MONTH));
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "on item click");
                int dayOfYear = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventsListActivity.class);
                intent.putExtra(EventsListActivity.DAY_OF_YEAR_KEY, dayOfYear);
                view.getContext().startActivity(intent);
            }
        });
        mNextMonthButton = (ImageButton)rootView.findViewById(R.id.imageButtonNext);
        mNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPrevMonthButton = (ImageButton) rootView.findViewById(R.id.imageButtonPrev);
        mPrevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement next month button event
            }
        });
        return rootView;
    }


}
