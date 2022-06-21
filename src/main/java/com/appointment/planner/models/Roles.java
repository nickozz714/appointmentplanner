package com.appointment.planner.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Roles {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    String name;
}
