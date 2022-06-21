package com.appointment.planner.service;

import com.appointment.planner.models.TimeSlot;
import com.appointment.planner.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;

    public List<TimeSlot> findAll() {
        return timeslotRepository.findAll();
    }

    public <S extends TimeSlot> S save(S entity) {
        return timeslotRepository.save(entity);
    }

    public Optional<TimeSlot> findById(Long aLong) {
        return timeslotRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        timeslotRepository.deleteById(aLong);
    }

    public Optional<TimeSlot> findByEventId(Long aLong) {
        return timeslotRepository.findByEventId(aLong);
    }

}
