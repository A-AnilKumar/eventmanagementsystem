package com.gyangrove.eventmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

  class EventmanagementsystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventmanagementsystemApplication.class, args);
    }
}
