package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.Person;
import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.service.PersonService;
import com.appointment.planner.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<ResponseType> getTimeslotList() {
        ResponseType response = timeslotService.findAll();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseType> getTimeSlotById(@PathVariable long id) {
        ResponseType response = timeslotService.findById(id);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<ResponseType> getTimeSlotByEventId(@PathVariable long id) {
        ResponseType response = timeslotService.findByEventId(id);
        return new ResponseEntity<>(response, response.getStatusCode());

    }

    @PostMapping
    public ResponseEntity<ResponseType> create(@RequestBody TimeSlot timeslot) {
        ResponseType response = timeslotService.save(timeslot);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseType> deleteTimeSlotById(@PathVariable long id) {
        ResponseType response = timeslotService.deleteById(id);
        return new ResponseEntity<>(response, response.getStatusCode());


    }

//    @PutMapping("{timeslot_id}/{person_id}")
//    public ResponseEntity<ResponseType> putPersonInTimeslot(@PathVariable long timeslot_id,@PathVariable long person_id) {
//        ResponseType response = timeslotService.putPersonInTimeslot(timeslot_id, person_id);
//        return new ResponseEntity<>(response, response.getStatusCode());
//
//    }


}
