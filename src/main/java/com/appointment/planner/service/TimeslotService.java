package com.appointment.planner.service;

import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;

    public List<TimeSlot> findAll() {
        return timeslotRepository.findAll();
    }
    @Transactional
    public <S extends TimeSlot> S save(S entity) {

            if (!entity.getBeginTime().isBlank() || !entity.getBeginTime().isEmpty() || !entity.getEndTime().isEmpty() || !entity.getEndTime().isBlank())
                {
                    return timeslotRepository.save(entity);
                }
            else {
                throw new NullPointerException();
            }
    }

    public Optional<TimeSlot> findById(Long aLong) {
        return timeslotRepository.findById(aLong);
    }

    public void delete(TimeSlot entity) {
        timeslotRepository.delete(entity);
    }

    public List<TimeSlot> findByEventId(Long aLong) {
        return timeslotRepository.findByEventId(aLong);
    }

}
