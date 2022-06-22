package com.appointment.planner.service;

import com.appointment.planner.models.Person;
import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;

//    @Autowired
//    private PersonService personService;

    @Autowired
    CheckEntityService checkEntityService;

    public ResponseType findAll() {

        return new ResponseType("OK",HttpStatus.CREATED,timeslotRepository.findAll());
    }
    @Transactional
    public ResponseType save(TimeSlot entity) {

        if (checkEntityService.isBlank(entity.getBeginTime()) || checkEntityService.isEmpty(entity.getBeginTime()) || checkEntityService.isNull(entity.getBeginTime()) || !checkEntityService.isCorrectTime(entity.getBeginTime()))
                {
                    return new ResponseType("Begin Time not specified correctly",HttpStatus.NOT_ACCEPTABLE);
                }
        else if (checkEntityService.isBlank(entity.getEndTime()) || checkEntityService.isEmpty(entity.getEndTime()) || checkEntityService.isNull(entity.getEndTime())|| !checkEntityService.isCorrectTime(entity.getEndTime()))
        {
            return new ResponseType("End Time not specified correctly",HttpStatus.NOT_ACCEPTABLE);
        }
        else if (entity.getAmount() <= 0) {
            return new ResponseType("Amount not specified correctly",HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            return new ResponseType("Created",HttpStatus.CREATED, timeslotRepository.save(entity));
        }
    }

    public ResponseType findById(Long aLong) {
        return new ResponseType("OK",HttpStatus.CREATED,timeslotRepository.findById(aLong));
    }

    @Transactional
    public void delete(TimeSlot timeSlot) {
        timeslotRepository.delete(timeSlot);
    }

    @Transactional
    public ResponseType deleteById(long id) {

        Optional<TimeSlot> timeslotOptional = (Optional<TimeSlot>) this.findById(id).getObject();
        if (timeslotOptional.isPresent()){
            try {
                timeslotRepository.deleteById(id);
                return new ResponseType("Deleted",HttpStatus.OK);
           }
            catch (Exception e) {
                return new ResponseType("Error:" + e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseType("Not found",HttpStatus.NOT_FOUND);
        }
    }


    public ResponseType findByEventId(Long aLong) {
        return new ResponseType("OK",HttpStatus.CREATED,timeslotRepository.findByEventId(aLong));
    }

//    @Transactional
//    public ResponseType putPersonInTimeslot(long timeslot_id, long person_id) {
//        Optional<TimeSlot> timeslotOptional = (Optional<TimeSlot>) this.findById(timeslot_id).getObject();
//        Optional<Person> optionalPerson = (Optional<Person>) personService.findById(person_id).getObject();
//        if (timeslotOptional.isPresent() && personService.existsById(person_id)) {
//            try {
//                Person selectedPerson = optionalPerson.get();
//                TimeSlot targetTimeslot = timeslotOptional.get();
//                targetTimeslot.appendparticipants(selectedPerson);
//                return new ResponseType("OK",HttpStatus.CREATED,timeslotRepository.save(targetTimeslot));
//            } catch (Exception e) {
//                return new ResponseType("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//        else {
//            return new ResponseType("Not found",HttpStatus.NOT_FOUND);
//        }
//    }
}
