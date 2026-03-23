package com.emtlabs.emtlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmtLabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmtLabsApplication.class, args);
    }

}
