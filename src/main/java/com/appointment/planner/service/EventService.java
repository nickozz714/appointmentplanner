package com.appointment.planner.service;

import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.EventTimeslot;
import com.appointment.planner.models.pojo.ResponseType;
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

    @Autowired
    private CheckEntityService checkEntityService;

    public ResponseType findAll() {
        return new ResponseType("List of events", HttpStatus.OK,eventRepository.findAll());

    }

    public ResponseType save(Event event) {
        if (checkEntityService.isBlank(event.getName()) || checkEntityService.isNull(event.getName())) {
            return new ResponseType("Name is not specified correctly", HttpStatus.NOT_ACCEPTABLE);
        } else if (checkEntityService.isBlank(event.getDate()) || checkEntityService.isNull(event.getDate())) {
            return new ResponseType("Date is not specified correctly", HttpStatus.NOT_ACCEPTABLE);
        } else if (!checkEntityService.isCorrectDate(event.getDate())) {
            return new ResponseType("Date is not formed correctly", HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseType("OK",HttpStatus.CREATED, eventRepository.save(event));
        }
    }

    public ResponseType findById(Long aLong) {
        return new ResponseType("OK",HttpStatus.FOUND,eventRepository.findById(aLong));
    }


    public boolean existsById(Long aLong) {
        return eventRepository.existsById(aLong);
    }

    @Transactional
    public ResponseType saveEventWithTimeslots(EventTimeslot eventTimeslot) {
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
            return new ResponseType("Error: Creating the new event was not successfull. Please contact an admin or try again.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EventTimeslot returnedEventTimeslot = new EventTimeslot();
        returnedEventTimeslot.setId(targetEvent.getId());
        returnedEventTimeslot.setName(targetEvent.getName());
        returnedEventTimeslot.setLocation(targetEvent.getLocation());
        returnedEventTimeslot.setDate(targetEvent.getDate());
        returnedEventTimeslot.setContinues(targetEvent.isContinues());
        List<TimeSlot> timeslotList;
        timeslotList = (List<TimeSlot>) timeslotService.findByEventId(targetEvent.getId()).getObject();
        returnedEventTimeslot.setTimeSlotList(timeslotList);
        return new ResponseType("Succesfully saved Event with timeslots",HttpStatus.CREATED,returnedEventTimeslot);
        }

        public ResponseType deleteEventById(long id){
            Optional<Event> eventOptional = (Optional<Event>) this.findById(id).getObject();
            if (eventOptional.isPresent()){
                try {
                    Event targetEvent = eventOptional.get();
                    List<TimeSlot> timeslotList = (List<TimeSlot>) timeslotService.findByEventId(targetEvent.getId()).getObject();
                    timeslotList.forEach(timeSlot -> {
                        timeslotService.delete(timeSlot);
                    });
                    eventRepository.delete(eventOptional.get());
                    return new ResponseType("Event and timeslots have been deleted", HttpStatus.OK);
                }
                catch (Exception e) {
                    return new ResponseType("Error:" + e, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else {
                return new ResponseType("Event not found", HttpStatus.NOT_FOUND);
            }
        }
}
