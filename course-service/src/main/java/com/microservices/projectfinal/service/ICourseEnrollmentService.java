package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseEnrollmentDTO;

public interface ICourseEnrollmentService {
    CourseEnrollmentDTO enrollCourse(Long courseId, Long userId);
    void unEnrollCourse(Long courseId, Long userId);
    boolean isEnrolled(Long courseId, Long userId);
}
