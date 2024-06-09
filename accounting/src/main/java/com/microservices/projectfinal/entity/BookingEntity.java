package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.*;


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

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    // getters and setters
    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }
}

