package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
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

import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class CourseVideoController {

    private final ICourseVideoService courseVideoService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCourseVideo(@UserId String userId, @ModelAttribute CourseVideoCreateDTO courseVideoCreateDTO) {
        return ResponseEntity.ok(courseVideoService.createCourseVideo(userId,courseVideoCreateDTO));
    }

    @GetMapping(value = "/stream/{courseId}/{courseVideoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<InputStream> getVideoResource(@UserId String userId, @PathVariable Long courseId, @PathVariable Long courseVideoId) {
        return courseVideoService.getVideoResource(userId, courseId, courseVideoId);
    }

    @GetMapping(value = "/information/{courseId}/{courseVideoId}")
    public ResponseEntity<?> getVideoInformation(@UserId String userId, @PathVariable Long courseId, @PathVariable Long courseVideoId) {
        return ResponseEntity.ok(courseVideoService.getCourseVideoById(userId, courseId, courseVideoId));
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
