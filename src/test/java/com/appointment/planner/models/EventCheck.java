package com.appointment.planner.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventCheck {

    private Event event;

    @BeforeEach
    public void init() {
        this.event = new Event();
    }

    @Test
    public void testGetSetEventName() {
        this.event.setName("Event name");
        Assertions.assertEquals("Event name", this.event.getName());
    }
}
