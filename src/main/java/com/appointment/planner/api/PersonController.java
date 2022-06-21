package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.Person;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<ResponseType> findAll() {
        ResponseType response = personService.findAll();
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseType> findById(@PathVariable long id) {
        ResponseType response = personService.findById(id);
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @PostMapping
    public ResponseEntity<ResponseType> save(@RequestBody Person person) {
        ResponseType response = personService.save(person);
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @PutMapping("/timeslot/{timeslotId}/person/{personId}")
    public ResponseEntity<ResponseType> registerPersonToTimeSlot(@PathVariable long timeslotId, @PathVariable long personId) {
        ResponseType response = (personService.registerToTimeslot(personId, timeslotId));
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @DeleteMapping("/timeslot/{timeslotId}/person/{personId}")
    public ResponseEntity<ResponseType> unregisterPersonToTimeSlot(@PathVariable long timeslotId, @PathVariable long personId) {
        ResponseType response = (personService.unregisterToTimeslot(personId, timeslotId));
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseType> deletePerson(@PathVariable long id) {
       ResponseType response = personService.deleteById(id);
       return new ResponseEntity<>(response,response.getStatusCode());
    }
}
