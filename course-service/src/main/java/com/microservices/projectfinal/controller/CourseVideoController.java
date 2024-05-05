package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.dto.CourseVideoCreateDTO;
import com.microservices.projectfinal.dto.CourseVideoUpdateDTO;
import com.microservices.projectfinal.service.ICourseVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class CourseVideoController {

    private final ICourseVideoService courseVideoService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCourseVideo(@ModelAttribute CourseVideoCreateDTO courseVideoCreateDTO) {
        return ResponseEntity.ok(courseVideoService.createCourseVideo(courseVideoCreateDTO));
    }

    @GetMapping(value = "/stream/{courseId}/{courseVideoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Resource> getVideoResource(@PathVariable Long courseId, @PathVariable Long courseVideoId) {
        return courseVideoService.getVideoResource(courseId, courseVideoId);
    }

    @GetMapping(value = "/information/{courseId}/{courseVideoId}")
    public ResponseEntity<?> getVideoInformation(@PathVariable Long courseId, @PathVariable Long courseVideoId) {
        return ResponseEntity.ok(courseVideoService.getCourseVideoById(courseId, courseVideoId));
    }

    @PutMapping(value = "/{courseId}/{courseVideoId}")
    public ResponseEntity<?> updateCourseVideo(@PathVariable Long courseId, @PathVariable Long courseVideoId, @ModelAttribute CourseVideoUpdateDTO courseVideoUpdateDTO) {
        return ResponseEntity.ok(courseVideoService.updateCourseVideo(courseId, courseVideoId, courseVideoUpdateDTO));
    }

    @DeleteMapping(value = "/{courseId}/{courseVideoId}")
    public ResponseEntity<?> deleteCourseVideo(@PathVariable Long courseId, @PathVariable Long courseVideoId) {
        courseVideoService.deleteCourseVideo(courseId, courseVideoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
