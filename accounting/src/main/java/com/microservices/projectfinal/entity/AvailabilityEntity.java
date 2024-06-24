package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "availabilities")
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "tutor_id", nullable = false)
    private String tutorId;

    @Setter
    @Column(name = "dim_time_key", nullable = false)
    private Long dimTimeKey;

    @Setter
    @Builder.Default
    @Column(name = "is_available")
    private boolean available = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
