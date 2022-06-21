package com.appointment.planner.service;

import com.appointment.planner.models.Person;
import com.appointment.planner.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public <S extends Person> S save(S entity) {
        return personRepository.save(entity);
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return personRepository.existsById(aLong);
    }
}
