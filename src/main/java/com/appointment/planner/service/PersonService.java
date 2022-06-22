package com.appointment.planner.service;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.Person;
import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeslotService timeslotService;

    public ResponseType findAll() {
        return new ResponseType("OK", HttpStatus.CREATED, personRepository.findAll());
    }

    public ResponseType save(Person entity) {
        return new ResponseType("OK", HttpStatus.CREATED, personRepository.save(entity));
    }

    public ResponseType findById(Long aLong) {
        return new ResponseType("OK", HttpStatus.OK, personRepository.findById(aLong));
    }

    public boolean existsById(Long aLong) {
        return personRepository.existsById(aLong);
    }

    public ResponseType delete(Person entity) {
        personRepository.delete(entity);
        return new ResponseType("Person has been removed.", HttpStatus.OK);
    }

    public ResponseType deleteById(long id) {
        if (this.existsById(id)) {
            personRepository.deleteById(id);
            return new ResponseType("Person has been removed.", HttpStatus.OK);
        }
        else {
            return new ResponseType("Person is not found.", HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public ResponseType registerToTimeslot(long person_id, long timeslot_id) {
        Optional<Person> optionalPerson = (Optional<Person>) this.findById(person_id).getObject();
        Optional<TimeSlot> optionalTimeSlot = (Optional<TimeSlot>) timeslotService.findById(timeslot_id).getObject();
        if (optionalPerson.isPresent() && optionalTimeSlot.isPresent()) {
            Person selectedPerson = optionalPerson.get();
            TimeSlot selectedTimeslot = optionalTimeSlot.get();
            if (selectedTimeslot.placeAvailable(1)) {
                Set<TimeSlot> personTimeslots = selectedPerson.getJoinedTimeslots();
                personTimeslots.add(selectedTimeslot);
                selectedPerson.setJoinedTimeslots(personTimeslots);

                return new ResponseType("OK", HttpStatus.CREATED, personRepository.save(selectedPerson));
            }
            else {
                return new ResponseType("Timeslot does not allow more participants", HttpStatus.NOT_ACCEPTABLE);
            }
        }else {
            return new ResponseType("Timeslot and/or Person not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseType unregisterToTimeslot(long person_id, long timeslot_id) {
        Optional<Person> optionalPerson = (Optional<Person>) this.findById(person_id).getObject();
        Optional<TimeSlot> optionalTimeSlot = (Optional<TimeSlot>) timeslotService.findById(timeslot_id).getObject();
        if (optionalPerson.isPresent() && optionalTimeSlot.isPresent()) {
            Person selectedPerson = optionalPerson.get();
            TimeSlot selectedTimeslot = optionalTimeSlot.get();
            Set<TimeSlot> personTimeslots = selectedPerson.getJoinedTimeslots();
            personTimeslots.remove(selectedTimeslot);
            selectedPerson.setJoinedTimeslots(personTimeslots);
            return new ResponseType("OK", HttpStatus.OK, personRepository.save(selectedPerson));
        }else {
            return new ResponseType("Timeslot and/or Person not found", HttpStatus.NOT_FOUND);
        }
    }
}
