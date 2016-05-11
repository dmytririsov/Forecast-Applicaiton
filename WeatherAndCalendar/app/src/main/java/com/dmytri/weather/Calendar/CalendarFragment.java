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
import android.widget.ImageView;
import android.widget.TextView;

import com.dmytri.weather.R;

/**
 *@author Dmytri on 11.05.2016.
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();

    // please insert enter near static and non-static members
    private ImageView mDayEventIcon;

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
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        GridView grid = (GridView) rootView.findViewById(R.id.calendar_days);
        // TODO set current month to adapter
        final CalendarDaysAdapter adapter = new CalendarDaysAdapter(getActivity(), 4);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "on item click");
                adapter.getItem(position);
            }
        });
        return rootView;
    }


}
