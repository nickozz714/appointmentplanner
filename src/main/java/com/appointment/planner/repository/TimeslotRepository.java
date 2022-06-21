package com.appointment.planner.repository;

import com.appointment.planner.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByEventId(long event_id);
}
