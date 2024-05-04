package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "BookingPurchases")
public class BookingPurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AccountEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_status", nullable = false)
    private BookingPurchaseStatus purchaseStatus;

    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // getters and setters

    enum BookingPurchaseStatus {
        PENDING,
        CONFIRMED,
        CANCELED
    }
}

