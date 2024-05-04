package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "Tutors")
@EntityListeners(AuditingEntityListener.class)
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private AccountEntity account;

    @Column(nullable = false)
    private String subject;

    @Column
    private String resume;

    @Column(name = "teach_fee", nullable = false)
    private BigDecimal teachFee;

    @Column(name = "time_per_one_purchase", nullable = false)
    private Long timePerOnePurchase;

    @CreatedDate
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    // getters and setters
}
