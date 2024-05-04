package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.dto.ListCourseResponse;
import com.microservices.projectfinal.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CourseController {
    private final ICourseService courseService;

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CourseResponseDTO> createCourse(@ModelAttribute CourseCreateDTO courseCreateDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseCreateDTO));
    }

    @GetMapping
    public ResponseEntity<ListCourseResponse> getListCourse(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getListCourse(page, size));
    }


}
