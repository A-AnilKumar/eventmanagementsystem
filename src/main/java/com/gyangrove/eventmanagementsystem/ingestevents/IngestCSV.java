package com.gyangrove.eventmanagementsystem.ingestevents;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class IngestCSV {


    private final EventRepository eventRepository;

    @Autowired
    IngestCSV(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    private final String filePath = "/Backend_assignment_gg_dataset.csv";

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() throws IOException, CsvValidationException, ParseException {
        CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath)), StandardCharsets.UTF_8));
        reader.skip(1); // Skip header row
        String[] line;
        long count = eventRepository.count();
        while ( count < 1 &&  (line = reader.readNext()) != null) {
            Event event = new Event();
            event.setEventName(line[0]);
            event.setCityName(line[1]);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate localDate = LocalDate.parse(line[2], dateTimeFormatter);
            event.setDate(localDate);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m:s");
            LocalTime localTime = LocalTime.parse(line[3],timeFormatter);
            event.setTime(localTime);

            event.setLatitude(Double.parseDouble(line[4]));
            event.setLongitude(Double.parseDouble(line[5]));

            eventRepository.save(event);
        }
    }
}

