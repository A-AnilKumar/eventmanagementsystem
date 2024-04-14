package com.gyangrove.eventmanagementsystem.events;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {


    private final EventsService eventsService;

    @Autowired
    EventsController(EventsService eventsService){
        this.eventsService = eventsService;
    }


    @GetMapping("/find")
    public ResponseEntity<List<EventsResponse>> find(@RequestParam(name = "Latitude", required = true)  Double latitude,
                                             @RequestParam(name = "Longitude",required = true) Double longitude,
                                                   @Valid @RequestParam(name="Date", required = true) String date ) {

        try{

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate localDate = LocalDate.parse(date, dateFormatter);

            // Validate latitude and longitude
            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                throw new IllegalArgumentException("Invalid latitude or longitude");
            }

            // Call service to find events
            List<EventsResponse> events = eventsService.find(latitude, longitude, localDate);
            return ResponseEntity.ok(events);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(null);
        }
    }
}

