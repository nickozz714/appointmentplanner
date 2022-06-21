package com.appointment.planner.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String name;
    String date;
    String location;
    boolean continues;

    @OneToMany(mappedBy = "event")
    private List<TimeSlot> timeslots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isContinues() {
        return continues;
    }

    public void setContinues(boolean continues) {
        this.continues = continues;
    }
}
