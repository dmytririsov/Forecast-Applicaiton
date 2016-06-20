package com.dmytri.forecast.Calendar.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name = "Events")
public class EventsModel extends Model {


    @Column(name = "Event_description")
    public String event_description;
    @Column(name = "Event_spinner")
    public String event_spinner;
    @Column(name = "Event_date")
    public String event_date;

    public EventsModel() {
        super();
    }

    public EventsModel(String event_description, String event_spinner, String event_date){
        super();
        this.event_description = event_description;
        this.event_spinner = event_spinner;
        this.event_date = event_date;
    }

    public static List<EventsModel> getID(final String eventDate) {
        return new Select(new String[]{"Id"})
                .from(EventsModel.class)
                .where("Event_date = ?", eventDate)
                .execute();
    }public static List<EventsModel> getDateDescription(final String eventDate) {
        return new Select(new String[]{"Id, Event_description, Event_spinner, Event_date"})
                .from(EventsModel.class)
                .where("Event_date = ?", eventDate)
                .execute();
    }
}
