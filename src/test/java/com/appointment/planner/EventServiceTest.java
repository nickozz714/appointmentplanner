package com.appointment.planner;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.pojo.ResponseType;
import com.appointment.planner.repository.EventRepository;
import com.appointment.planner.service.CheckEntityService;
import com.appointment.planner.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {
    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CheckEntityService checkEntityService;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        {
            Event mockedEvent = new Event();
            mockedEvent.setId(3);
            mockedEvent.setName("Mocked Festival");
            Optional<Event> mockedOptionalEvent = Optional.of(mockedEvent);
            Mockito.when(this.eventRepository.findById(3L)).thenReturn(mockedOptionalEvent);
        }

        ResponseType response = this.eventService.findById(3L);
        Optional<Event> optionalEvent = (Optional<Event>) response.getObject();

        assertEquals(3L, optionalEvent.get().getId());
        assertEquals("Mocked Festival",optionalEvent.get().getName());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void testSave() {
        //GIVEN
        Event event = new Event();
        event.setName("Mock event");
        event.setId(400);
        event.setDate("1995-11-20");
        event.setContinues(false);
        Mockito.when(eventRepository.save(event)).thenReturn(event);
        Mockito.when(checkEntityService.isBlank(event.getName())).thenReturn(false);
        Mockito.when(checkEntityService.isEmpty(event.getName())).thenReturn(false);
        Mockito.when(checkEntityService.isBlank(event.getDate())).thenReturn(false);
        Mockito.when(checkEntityService.isEmpty(event.getDate())).thenReturn(false);
        Mockito.when(checkEntityService.isCorrectDate(event.getDate())).thenReturn(true);


        ResponseType response = eventService.save(event);
        Event savedEvent = (Event) response.getObject();

        Mockito.verify(this.eventRepository).save(event);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(400,savedEvent.getId());

    }
}
