package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@JsonRootName("course_enrollment")
@Getter
@Builder
public class CourseEnrollmentDTO {

    private Long id;

    private Long courseId;

    private String studentId;

    private Long transactionId;

    private Instant enrollmentDate;

    private String status;

    private Instant createdAt;

    private Instant modifiedAt;
}
