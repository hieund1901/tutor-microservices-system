package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.dto.ListCourseResponse;
import com.microservices.projectfinal.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {
    private final ICourseService courseService;

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CourseResponseDTO> createCourse(@UserId String userId, @ModelAttribute CourseCreateDTO courseCreateDTO) {
        return ResponseEntity.ok(courseService.createCourse(userId, courseCreateDTO));
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<ListCourseResponse> getListCourse(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getListCourse(page, size));
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<InputStreamResource> getThumbnail(@RequestParam String thumbnailPath) throws IOException {
        InputStream thumbnail = courseService.getThumbnail(thumbnailPath);
        String contentType = null;
        if (thumbnailPath.endsWith(".png")) {
            contentType = "image/png";
        } else if (thumbnailPath.endsWith(".jpg") || thumbnailPath.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        }


        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(new InputStreamResource(thumbnail));
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }
}
