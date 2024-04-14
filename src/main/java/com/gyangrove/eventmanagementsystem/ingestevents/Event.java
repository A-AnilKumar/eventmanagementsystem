package com.gyangrove.eventmanagementsystem.ingestevents;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event", indexes = {@Index(name = "idx_event_date", columnList = "date")})
public class Event {


    @Id
    @NotBlank(message = "Event name must not be blank")
    @NotNull(message = "Event name must not be null")
    private String eventName;

    @NotBlank(message = "City name must not be blank")
    @NotNull(message = "City name must not  be null")
    private String cityName;

    @Column(name = "date")
    @NotNull(message = "Date must not be null")
//    @FutureOrPresent(message = "Date must be in the present or future")
    @JsonFormat(pattern="yyyy-M-d")
    @Temporal(TemporalType.DATE)
//    @Index(name = "idx_event_date")
    private LocalDate date;

    @NotNull(message = "Time must not be null")
//    @FutureOrPresent(message = "Time must be in the present or future")
    @JsonFormat(pattern = "H:m:s")
    private LocalTime time;

    @Column(name = "latitude", columnDefinition = "DOUBLE")
    @DecimalMin(value = "-90.0", message = "Latitude must be at least -90")
    @DecimalMax(value = "90.0", message = "Latitude must be at most 90")
    @NotNull(message = "Latitude must not be null")
//    @Digits(integer = 2, fraction = 6, message = "Latitude must have at most 2 integer digits and 6 fractional digits")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "DOUBLE")
    @DecimalMin(value = "-180.0", message = "Longitude must be at least -180")
    @DecimalMax(value = "180.0", message = "Longitude must be at most 180")
    @NotNull(message = "Longitude must not be null")
//    @Digits(integer = 3, fraction = 6, message = "Longitude must have at most 3 integer digits and 6 fractional digits")
    private Double longitude;

}
