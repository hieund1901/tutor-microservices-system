package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseEnrollmentDTO;

public interface ICourseEnrollmentService {
    CourseEnrollmentDTO enrollCourse(Long courseId, String userId);
    void unEnrollCourse(Long courseId, Long userId);
    boolean isEnrolled(Long courseId, String userId);
    void activateCourse(Long transactionId, String userId);
    CourseEnrollmentDTO getEnrolledCourses(Long id);
}
