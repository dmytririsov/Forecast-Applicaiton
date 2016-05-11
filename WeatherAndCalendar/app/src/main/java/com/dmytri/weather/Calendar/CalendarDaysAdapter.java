package com.dmytri.weather.Calendar;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmytri.weather.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDaysAdapter extends BaseAdapter {

    private static final String TAG = CalendarDaysAdapter.class.getSimpleName();
    private final GregorianCalendar mCalendar;
    private final Context mContext;

    public CalendarDaysAdapter(Context context, int monthNumber) {
        mCalendar = new GregorianCalendar(2016, monthNumber + 1, 1);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_item, parent, false);
        }
        TextView dayNumber = (TextView) convertView.findViewById(R.id.day_number_text_view);
        dayNumber.setText(String.valueOf(position + 1));
        return convertView;
    }

}
