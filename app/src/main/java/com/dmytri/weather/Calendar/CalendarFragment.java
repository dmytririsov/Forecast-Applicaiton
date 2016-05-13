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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmytri.weather.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Dmytri on 11.05.2016.
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();


    // please insert enter near static and non-static members
    private ImageView mDayEventIcon;
    private String mNameOfMonthText;
    private Calendar mCalendarInst;
    private ImageButton mNextMonth;
    private String mDateForIntent;
    public CalendarFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "on create view");
        mCalendarInst = Calendar.getInstance();
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        final GridView grid = (GridView) rootView.findViewById(R.id.calendar_days);

        TextView mNameOfMonthTextView = (TextView)rootView.findViewById(R.id.name_of_month);
        mNameOfMonthText = mCalendarInst.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        mNameOfMonthTextView.setText(mNameOfMonthText.toUpperCase() + ", " + mCalendarInst.get(Calendar.YEAR));

        final CalendarDaysAdapter adapter = new CalendarDaysAdapter(getActivity(), false);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "on item click");
                adapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventDetails.class);
                intent.putExtra("parent", parent.toString());
                intent.putExtra("pos", position);
                intent.putExtra("id", id);
                view.getContext().startActivity(intent);

            }
        });
        mNextMonth = (ImageButton)rootView.findViewById(R.id.imageButtonNext);
        final CalendarDaysAdapter adapterNextSlide = new CalendarDaysAdapter(getActivity(), true);
        mNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //grid.setAdapter(adapterNextSlide);

            }
        });
        return rootView;
    }


}
