package com.gyangrove.eventmanagementsystem.events;

import com.gyangrove.eventmanagementsystem.ingestevents.Event;
import com.gyangrove.eventmanagementsystem.ingestevents.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class EventsService {

    private final EventRepository eventRepository;

    @Autowired
    EventsService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    private static final String WEATHER_API_URL = "API_URL_HERE";
    private static final String WEATHER_API_CODE = "API_CODE_HERE";
    private static final String DISTANCE_API_URL =  "API_URL_HERE";
    private static final String DISTANCE_API_CODE = "API_CODE_HERE";


    public List<EventsResponse> find(Double userLatitude, Double userLongitude, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(14);
        List<Event> events = eventRepository.findByStartDateAfterAndEndDateBefore(startDate, endDate);
        List<EventsResponse> response = new ArrayList<>();

        int page = 1;
        int pageSize = 10;
        int totalEvents = events.size();

        for (int i = 0; i < totalEvents; i+= pageSize) {
            List<Events> tempResponse = new ArrayList<>();
            int end = Math.min(i+pageSize,totalEvents);
            for(int j = i;j < end;j++){
                Event event = events.get(j);
                Events tempEvent = createEvent(event,userLatitude,userLongitude);
                tempResponse.add(tempEvent);
            }
            response.add(new EventsResponse(tempResponse,page++,pageSize,totalEvents));
        }
        return response;
    }

    private Events createEvent(Event event, Double userLatitude, Double userLongitude) {
        Events tempEvent = new Events();
        tempEvent.setEvent_name(event.getEventName());
        tempEvent.setCity_name(event.getCityName());
        tempEvent.setDate(event.getDate());

        CompletableFuture<String> weatherFuture = fetchWeather(event.getCityName(), event.getDate());
        CompletableFuture<Double> distanceFuture = calculateDistance(userLatitude, userLongitude, event.getLatitude(), event.getLongitude());

        CompletableFuture.allOf(weatherFuture, distanceFuture).join();

        tempEvent.setWeather(weatherFuture.join());
        tempEvent.setDistance_km(distanceFuture.join());

        return tempEvent;
    }


    static class WeatherResponse{
        private String weather;
        private String getWeather(){ return weather; }
        private void setWeather(String weather){ this.weather = weather; }
    }

    @Async
    public CompletableFuture<String> fetchWeather(String cityName, LocalDate date) {
        // Make API request to Weather API
        RestTemplate restTemplate = new RestTemplate();

        String weatherAPI = WEATHER_API_URL + "?code=" + WEATHER_API_CODE +"&city=" + cityName + "&date=" + date;

        WeatherResponse weatherResponse = restTemplate.getForObject(weatherAPI, WeatherResponse.class);
        return CompletableFuture.completedFuture(weatherResponse != null ? weatherResponse.getWeather() : null);
    }

    private static class DistaceResponse{
        private double distance;
        private double getDistance(){return distance;}
        private void setDistance(double distance){this.distance=distance;}
    }

    @Async
    public CompletableFuture<Double> calculateDistance(Double userLatitude, Double userLongitude, Double eventLatitude, Double eventLongitude) {
        // Make API request to Distance Calculation API
        RestTemplate restTemplate = new RestTemplate();

        String distanceAPI = DISTANCE_API_URL + "?code=" +DISTANCE_API_CODE+"&latitude1=" + userLatitude +"&longitude1=" + userLongitude + "&latitude2=" + eventLatitude + "&longitude2=" + eventLongitude;
        DistaceResponse distanceResponse = restTemplate.getForObject(distanceAPI, DistaceResponse.class);
        return CompletableFuture.completedFuture(distanceResponse != null ? distanceResponse.getDistance() : 0.0);
    }


}
