package com.dmytri.weather.Calendar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Events")
public class EventsModel extends Model {

    @Column(name = "event_description")
    public String event_description;
    @Column(name = "event_spinner")
    public String event_spinner;
    @Column(name = "event_data")
    public String event_data;

    public EventsModel() {
        super();
    }

    public EventsModel(String event_description, String event_spinner){
        super();

        this.event_description = event_description;
        this.event_spinner = event_spinner;
        //this.event_data = event_data;

    }
}
