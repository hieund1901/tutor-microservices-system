package com.microservices.projectfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CourseServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApp.class, args);
    }
}
