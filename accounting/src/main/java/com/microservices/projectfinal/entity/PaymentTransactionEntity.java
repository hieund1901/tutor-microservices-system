package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_transactions")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PaymentTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_ids", nullable = false)
    private String referenceIds;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ReferenceType referenceType;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_status")
    private PurchaseStatus purchaseStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    // getters and setters

    public enum ReferenceType {
        COURSE,
        CALL
    }

    public enum PurchaseStatus {
        PENDING,
        APPROVED,
        DECLINED,
        REFUNDED
    }
}

