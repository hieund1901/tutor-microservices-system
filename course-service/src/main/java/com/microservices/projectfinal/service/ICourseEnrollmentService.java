package com.microservices.projectfinal.service;

public interface ICourseEnrollmentService {
    void enrollCourse(Long courseId, Long userId);
    void unEnrollCourse(Long courseId, Long userId);
    boolean isEnrolled(Long courseId, Long userId);
}
