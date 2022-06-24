package com.appointment.planner.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

@Service
public class CheckEntityService {

    public boolean isCorrectTime(String timeString) {
        try {
            DateTimeFormatter strictTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalTime.parse(timeString, strictTimeFormatter);
            return true;
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    public boolean isCorrectDate(String dateString) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateString, dateTimeFormatter);
            return true;
        }catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }
    public boolean isEmpty(String value) {
        return value.isEmpty();
    }

    public boolean isNull(String value) {
        return value == null;
    }

    public boolean isBlank(String value) {
        return value.isBlank();
    }
}
