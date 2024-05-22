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

    @GetMapping("/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(courseEnrollmentService.enrollCourse(courseId, userId));
    }
}
