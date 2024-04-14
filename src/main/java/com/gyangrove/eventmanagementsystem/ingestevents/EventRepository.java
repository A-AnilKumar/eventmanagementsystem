package com.gyangrove.eventmanagementsystem.ingestevents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event,String> {
    @Query("SELECT e FROM Event e WHERE e.date BETWEEN ?1 AND ?2 ORDER BY e.date ASC")
    List<Event> findByStartDateAfterAndEndDateBefore(LocalDate startDate, LocalDate endDate);

}