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
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "course_enrollments")
public class CourseEnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "enrollment_date", nullable = false)
    private Instant enrollmentDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'inactive'")
    private EnrollmentStatus status = EnrollmentStatus.INACTIVE;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    // getters and setters

    public enum EnrollmentStatus {
        ACTIVE,
        INACTIVE
    }
}