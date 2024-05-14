package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "course_purchases")
public class CoursePurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "purchase_date", nullable = false)
    private Instant purchaseDate;

    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'pending'")
    private PurchaseStatus status = PurchaseStatus.PENDING;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    // getters and setters

    public enum PurchaseStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }
}

