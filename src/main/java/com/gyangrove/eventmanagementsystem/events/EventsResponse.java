package com.gyangrove.eventmanagementsystem.events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import  java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {
    private List<Events> events;
    private int page;
    private int pageSize;
    private long totalEvents;
    private int totalPages;

    // Constructors, getters, setters
    public EventsResponse(List<Events> events, int page, int pageSize, long totalEvents) {
        this.events = events;
        this.page = page;
        this.pageSize = pageSize;
        this.totalEvents = totalEvents;
        this.totalPages = (int) Math.ceil((double) totalEvents / pageSize);
    }

}

