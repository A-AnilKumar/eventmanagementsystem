package com.gyangrove.eventmanagementsystem.events;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
public class Events {

    @Id
    private String event_name;

    private String city_name;

    private LocalDate date;

    private String weather;

    private Double distance_km;
}
