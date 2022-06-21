package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.EventTimeslot;
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
    public List<Event> eventList() {return eventService.findAll();}

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id)
    {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()){
            return new ResponseEntity<>(eventOptional.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Event create(@RequestBody Event event) {
        return this.eventService.save(event);
    }

    @PostMapping("/WithTimeslots")
    public ResponseEntity<EventTimeslot> createEventWithTimeslots(@RequestBody EventTimeslot eventTimeslot) {
        return new ResponseEntity<>(eventService.saveEventWithTimeslots(eventTimeslot),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable long id) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()){
            try {
                eventService.delete(eventOptional.get());
                return new ResponseEntity<>("msg: Event has been deleted",HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>("Error:" + e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
