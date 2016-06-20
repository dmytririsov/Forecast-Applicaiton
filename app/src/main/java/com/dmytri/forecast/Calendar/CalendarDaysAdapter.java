package com.dmytri.forecast.Calendar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmytri.weather.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author dmytry.risovanyi
 */
public class CalendarDaysAdapter extends BaseAdapter {

    private static final String TAG = CalendarDaysAdapter.class.getSimpleName();

    private final GregorianCalendar mCalendar;
    private ImageView mDayEventIcon;
    private final Context mContext;

    public CalendarDaysAdapter(Context context, int monthNumber, int yearNumber) {
        mCalendar = new GregorianCalendar(yearNumber, monthNumber, 1);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Integer getItem(int position) {
        return mCalendar.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mCalendar.set(Calendar.DAY_OF_MONTH, position + 1);

        // TODO rework this using view holder
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_item, parent, false);
        }else {

        }
        TextView dayNumber = (TextView) convertView.findViewById(R.id.day_number_text_view);
        dayNumber.setText(String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)));
        return convertView;
    }

}
