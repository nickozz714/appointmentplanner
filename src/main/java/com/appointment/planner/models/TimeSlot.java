package com.appointment.planner.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TimeSlot {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    String beginTime;
    String endTime;
    String location;

    @ManyToOne
    private Event event;

    @ManyToMany
    @JoinTable (
        name = "timeslot_registration",
        joinColumns = @JoinColumn(name="timeslot_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id"))
    Set<TimeSlot> timeSlotSet;
}
