package com.microservices.projectfinal.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "payment_logs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long paymentId;
    @NotNull
    private Long transactionId;
    @NotNull
    private String paymentType;

    @NotBlank
    @Size(max = 50)
    private String verb;

    @NotNull
    private String data;

    @CreatedDate
    private Instant createdOn;
}
