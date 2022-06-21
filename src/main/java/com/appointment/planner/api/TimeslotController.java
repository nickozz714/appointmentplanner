package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.Person;
import com.appointment.planner.models.TimeSlot;
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
    public List<TimeSlot> getTimeslotList() {
        return timeslotService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable long id) {
        Optional<TimeSlot> timeslotOptional = timeslotService.findById(id);
        return timeslotOptional.map(timeSlot -> new ResponseEntity<>(timeSlot, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/event/{id}")
    public List<TimeSlot> getTimeSlotByEventId(@PathVariable long id) {
        return timeslotService.findByEventId(id);
    }

    @PostMapping
    public TimeSlot create(@RequestBody TimeSlot timeslot) {
        return this.timeslotService.save(timeslot);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTimeSlotById(@PathVariable long id) {
        Optional<TimeSlot> timeslotOptional = timeslotService.findById(id);
        if (timeslotOptional.isPresent()){
            try {
                timeslotService.delete(timeslotOptional.get());
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

    @PutMapping("{timeslot_id}/{person_id}")
    public ResponseEntity<TimeSlot> putPersonInTimeslot(@PathVariable long timeslot_id,@PathVariable long person_id) {
        Optional<TimeSlot> timeslotOptional = timeslotService.findById(timeslot_id);
        if (timeslotOptional.isPresent() && personService.existsById(person_id)) {
            try {
                Person selectedPerson = personService.findById(person_id).get();
                TimeSlot targetTimeslot = timeslotOptional.get();
                targetTimeslot.appendparticipants(selectedPerson);
                return new ResponseEntity<>(timeslotService.save(targetTimeslot), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }


}
