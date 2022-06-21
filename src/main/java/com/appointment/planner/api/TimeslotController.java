package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.TimeSlot;
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
    public ResponseEntity<TimeSlot> getTimeSlotByEventId(@PathVariable long id) {
        Optional<TimeSlot> timeslotOptional = timeslotService.findByEventId(id);
        return timeslotOptional.map(timeSlot -> new ResponseEntity<>(timeSlot, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
