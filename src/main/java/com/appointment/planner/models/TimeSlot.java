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
    int amount;

    @ManyToOne
    private Event event;

    @ManyToMany(mappedBy = "joinedTimeslots")
    Set<Person> participants;


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    public void setParticipantsSet(Set<Person> participants) {
        this.participants = participants;
    }

    public void appendparticipants(Person person) {
        this.participants.add(person);
    }
}
