package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.EventTimeslot;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.service.EventService;
import com.appointment.planner.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping
    public ResponseEntity<ResponseType> eventList() {
        ResponseType response = eventService.findAll();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseType> getEventById(@PathVariable long id)
    {
        ResponseType response = eventService.findById(id);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PostMapping
    public ResponseEntity<ResponseType> create(@RequestBody Event event) {
        ResponseType response = eventService.save(event);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PostMapping("/WithTimeslots")
    public ResponseEntity<ResponseType> createEventWithTimeslots(@RequestBody EventTimeslot eventTimeslot) {
        ResponseType response = eventService.saveEventWithTimeslots(eventTimeslot);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseType> deleteEventById(@PathVariable long id) {
        ResponseType response = eventService.deleteEventById(id);
        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
