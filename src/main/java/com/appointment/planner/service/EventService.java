package com.appointment.planner.service;

import com.appointment.planner.repository.EventRepository;
import com.appointment.planner.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

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
}
