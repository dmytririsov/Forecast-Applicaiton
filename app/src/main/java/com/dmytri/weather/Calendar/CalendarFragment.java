package com.dmytri.weather.calendar;

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
import com.dmytri.weather.calendar.events_logic.EventsListActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Dmytri on 11.05.2016.
 */
public class CalendarFragment extends Fragment {

    public static final String POSITION_INTENT = "position";
    public static final String MONTH_INTENT = "month";
    private static final String TAG = CalendarFragment.class.getSimpleName();
    private static final int mOutOfNextMonth = 13;
    private static final int mOutOfPrevMonth = 0;

    private String mNameOfMonth;
    private ImageButton mNextMonthButton;
    private ImageButton mPrevMonthButton;
    private int mCurrentMonth;
    private int mCurrentYear;

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
        final Calendar calendarInstance = Calendar.getInstance();
        mCurrentMonth = calendarInstance.get(Calendar.MONTH) + 1;
        mCurrentYear  = calendarInstance.get(Calendar.YEAR);
        final View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        final GridView grid = (GridView) rootView.findViewById(R.id.calendar_days);

        final TextView mNameOfMonthTextView = (TextView)rootView.findViewById(R.id.name_of_month);
        mNameOfMonth = calendarInstance.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        mNameOfMonthTextView.setText(calendarInstance.get(Calendar.YEAR) + " " + mNameOfMonth.toUpperCase());

        final CalendarDaysAdapter adapter = new CalendarDaysAdapter(getActivity(), mCurrentMonth, mCurrentYear);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "on item click");
                int dayOfYear = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventsListActivity.class);
                intent.putExtra(EventsListActivity.DAY_OF_YEAR_KEY, dayOfYear);
                intent.putExtra(POSITION_INTENT, position + 1);
                intent.putExtra(MONTH_INTENT, mNameOfMonth);
                view.getContext().startActivity(intent);
            }
        });
        mNextMonthButton = (ImageButton)rootView.findViewById(R.id.imageButtonNext);

        mPrevMonthButton = (ImageButton) rootView.findViewById(R.id.imageButtonPrev);
        mPrevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick NextMonth: "  + mCurrentMonth + " " + mCurrentYear);
                mCurrentMonth--;
                CalendarDaysAdapter newAdapter = new CalendarDaysAdapter(getActivity(),
                        mCurrentMonth, mCurrentYear);
                if (mCurrentMonth == mOutOfPrevMonth) {
                    mCurrentYear--;
                    mCurrentMonth = 12;
                }
                mNameOfMonth = getMonthFromInt(mCurrentMonth);
                mNameOfMonthTextView.setText(mCurrentYear + " " + mNameOfMonth.toUpperCase(Locale.getDefault()));
                grid.setAdapter(newAdapter);
                adapter.notifyDataSetChanged();
            }
        });

        mNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Current month: " + mNameOfMonth);
                Log.d(TAG, "onClick NextMonth: "  + mCurrentMonth + " " + mCurrentYear);
                mCurrentMonth++;
                    CalendarDaysAdapter newAdapter = new CalendarDaysAdapter(getActivity(),
                            mCurrentMonth, mCurrentYear);
                if (mCurrentMonth == mOutOfNextMonth) {
                    mCurrentMonth = 1;
                    mCurrentYear++;
                }
                mNameOfMonth = getMonthFromInt(mCurrentMonth);
                mNameOfMonthTextView.setText(mCurrentYear + " " + mNameOfMonth.toUpperCase(Locale.getDefault()));
                grid.setAdapter(newAdapter);
                adapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }
    public String getMonthFromInt(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
