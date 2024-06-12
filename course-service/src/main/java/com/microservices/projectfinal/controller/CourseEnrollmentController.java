package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.service.ICourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollment")
public class CourseEnrollmentController {
    private final ICourseEnrollmentService courseEnrollmentService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getEnrolledCourses(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(courseEnrollmentService.getEnrolledCourses(id));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, @UserId String userId) {
        return ResponseEntity.ok(courseEnrollmentService.enrollCourse(courseId, userId));
    }


}
