package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "availability_id")
    private Long availabilityId;

    @Column(name = "student_id")
    private String studentId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // getters and setters
    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }
}

