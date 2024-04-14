package com.gyangrove.eventmanagementsystem.ingestevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Page<Event> getEvents(int page, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return eventRepository.findAll(pageable);
    }

    public  String saveEvent(Event newEvent){

        eventRepository.save(newEvent);
        return " New Event added Successfully !!";
    }

    public List<Event> findEvents() {
        return eventRepository.findAll();
    }

    public boolean getEvent(String eventName) {
        Optional<Event> event = eventRepository.findById(eventName);
        return event.isPresent();
    }
}

