package com.gyangrove.eventmanagementsystem.ingestevents;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/events")
public class EventController {


    private final EventService eventService;

    @Autowired
    EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> findAllEvents(){
        List<Event> eventsResponse = eventService.findEvents();
        return ResponseEntity.ok(eventsResponse);
    }

    @PostMapping
    public ResponseEntity<String> saveEvent(@Valid @RequestBody Event newEvent){
        try {

            newEvent.setEventName(newEvent.getEventName().trim());
            newEvent.setCityName(newEvent.getCityName().trim());
            if(eventService.getEvent(newEvent.getEventName().trim())){
                throw new IllegalArgumentException("Event already exists, try with other event name");
            }

            if(newEvent.getDate().isBefore(LocalDate.now()) ||
                    (newEvent.getDate().isBefore(LocalDate.now()) && newEvent.getTime().isBefore(LocalTime.now()))){
                throw new IllegalArgumentException("Date, time cannot be past. It should be present or future.");
            }


            return ResponseEntity.ok(eventService.saveEvent(newEvent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
