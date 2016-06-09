package com.dmytri.weather.Calendar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Events")
public class EventsModel extends Model {

    @Column(name = "ID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int ID;
    @Column(name = "Event_description")
    public String event_description;
    @Column(name = "Event_spinner")
    public String event_spinner;
  /*  @Column(name = "Event_date")
    public String event_date;*/

    public EventsModel() {
        super();
    }

    public EventsModel(String event_description, int ID, String event_spinner){
        super();

        this.event_description = event_description;
        this.ID = ID;
        this.event_spinner = event_spinner;
        //this.event_date = event_date;

    }
}
