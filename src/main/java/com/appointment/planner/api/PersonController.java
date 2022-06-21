package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.Person;
import com.appointment.planner.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable long id) {
        return personService.findById(id);
    }

    @PostMapping
    public <S extends Person> S save(S entity) {
        return personService.save(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable long id) {
        Optional<Person> personOptional = personService.findById(id);
        if (personOptional.isPresent()){
            try {
                personService.delete(personOptional.get());
                return new ResponseEntity<>("msg: Event has been deleted", HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>("Error:" + e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
