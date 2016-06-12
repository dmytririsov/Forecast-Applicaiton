package com.dmytri.weather.Calendar;

import android.support.annotation.Nullable;

/**
 * @author Dmytri on 14.05.2016.
 */
public class CalendarEvent {

    private static final String TAG = CalendarEvent.class.getSimpleName();

    private String mEventDetails;
    private EventType mEventType;
    private int mDayOfYear;

    public enum EventType {
        MEETING,
        BIRTHDAY,
        REMINDER,
        NONE
    }

    public CalendarEvent(@Nullable String eventDetails, @Nullable EventType eventType, int dayOfYear) {
        mEventDetails = eventDetails == null ? "" : eventDetails;
        mEventType = eventType == null ? EventType.NONE : eventType;
        mDayOfYear = dayOfYear;
    }

    public String getEventDetails() {
        return mEventDetails;
    }

    public void setEventDetails(String eventDetails) {
        mEventDetails = eventDetails;
    }

    public EventType getEventType() {
        return mEventType;
    }

    public void setEventType(EventType eventType) {
        mEventType = eventType;
    }

    public int getDayOfYear() {
        return mDayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        mDayOfYear = dayOfYear;
    }

}
