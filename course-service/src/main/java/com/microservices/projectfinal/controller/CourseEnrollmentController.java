package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.service.ICourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollment")
public class CourseEnrollmentController {
    private final ICourseEnrollmentService courseEnrollmentService;

    @PostMapping("/${courseId}/${userId}")
    public ResponseEntity<?> enrollCourse(Long courseId, Long userId) {
        courseEnrollmentService.enrollCourse(courseId, userId);
        return ResponseEntity.ok().build();
    }
}
