package com.appointment.planner.service;

import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.EventTimeslot;
import com.appointment.planner.repository.EventRepository;
import com.appointment.planner.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TimeslotService timeslotService;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public <S extends Event> S save(S entity) {
        return eventRepository.save(entity);
    }

    public Optional<Event> findById(Long aLong) {
        return eventRepository.findById(aLong);
    }

    public void delete(Event entity) {
        eventRepository.delete(entity);
    }

    public boolean existsById(Long aLong) {
        return eventRepository.existsById(aLong);
    }

    @Transactional
    public EventTimeslot saveEventWithTimeslots(EventTimeslot eventTimeslot) {
        Event newEvent = new Event();
        newEvent.setName(eventTimeslot.getName());
        newEvent.setDate(eventTimeslot.getDate());
        newEvent.setLocation(eventTimeslot.getLocation());
        newEvent.setContinues(eventTimeslot.isContinues());
        Event targetEvent = eventRepository.save(newEvent);
        if (this.existsById(targetEvent.getId())) {
            eventTimeslot.getTimeSlotList().forEach(timeSlot -> {
                TimeSlot newTimeslot = timeSlot;
                newTimeslot.setEvent(targetEvent);
                timeslotService.save(newTimeslot);
            });
        }
        else {
            throw new NullPointerException();
        }
        EventTimeslot returnedEventTimeslot = new EventTimeslot();
        returnedEventTimeslot.setId(targetEvent.getId());
        returnedEventTimeslot.setName(targetEvent.getName());
        returnedEventTimeslot.setLocation(targetEvent.getLocation());
        returnedEventTimeslot.setDate(targetEvent.getDate());
        returnedEventTimeslot.setContinues(targetEvent.isContinues());
        List<TimeSlot> timeslotList;
        timeslotList = timeslotService.findByEventId(targetEvent.getId());
        returnedEventTimeslot.setTimeSlotList(timeslotList);
        return returnedEventTimeslot;
        }
}
