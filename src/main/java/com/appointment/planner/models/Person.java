package com.appointment.planner.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String firstName;
    String lastName;
    int age;
    String role;

@ManyToMany(cascade = CascadeType.ALL)
@JoinTable (
        name = "timeslot_registration",
        joinColumns = @JoinColumn(name="person_id"),
        inverseJoinColumns = @JoinColumn(name = "timeslot_id"))
    Set<TimeSlot> joinedTimeslots;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Set<TimeSlot> getJoinedTimeslots() {
        return joinedTimeslots;
    }

    public void setJoinedTimeslots(Set<TimeSlot> joinedTimeslots) {
        this.joinedTimeslots = joinedTimeslots;
    }
}
