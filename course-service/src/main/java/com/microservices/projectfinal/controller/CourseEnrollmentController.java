package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.service.ICourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollment")
public class CourseEnrollmentController {
    private final ICourseEnrollmentService courseEnrollmentService;

    @GetMapping("/{courseId}/{userId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        return ResponseEntity.ok(courseEnrollmentService.enrollCourse(courseId, userId));
    }
}
