package com.appointment.planner.api;

import com.appointment.planner.models.Event;
import com.appointment.planner.models.pojo.ResponseType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class PlannerApplicationIT {

        private static long currentId = -1;

        @Autowired
        private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

        @Test
        public void test1CreateUsingPost() {
            Event event = new Event();
            event.setName("Test event");
            event.setDate("2022-10-10");
            event.setContinues(true);
            event.setLocation("test");


            ResponseEntity<ResponseType> response = this.restTemplate.postForEntity(EventController.url, event, ResponseType.class);

            ResponseType responseType = response.getBody();
            Event returnedEvent = objectMapper.convertValue(responseType.getObject(), new TypeReference<>(){});
//            Event returnedEvent = (Event)responseType.getObject();
            assertNotEquals(0,returnedEvent.getId());
            assertEquals("Test event",event.getName());
            currentId = returnedEvent.getId();
        }

        @Test
        public void testIncorrectDateUsingPost() {
            Event event = new Event();
            event.setName("Test event");
            event.setDate("2022-22-10");

            ResponseEntity<ResponseType> response = this.restTemplate.postForEntity(EventController.url, event, ResponseType.class);


            assertEquals(HttpStatus.NOT_ACCEPTABLE,response.getStatusCode());
        }

        @Test
        public void test1GetUsingGetById() {
            ResponseEntity<ResponseType> response = this.restTemplate.getForEntity(EventController.url + "/" + currentId, ResponseType.class);
            assertNotNull(response.getBody());
            Event returnedEvent = (Event)response.getBody().getObject();
            assertEquals("2022-10-10",returnedEvent.getDate());


        }


}
