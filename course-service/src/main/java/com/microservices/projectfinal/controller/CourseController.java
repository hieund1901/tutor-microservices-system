package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CourseController {
    private final ICourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseCreateDTO courseCreateDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseCreateDTO));
    }

    @PutMapping
    public String updateCourse() {
        return "Course updated";
    }


}
