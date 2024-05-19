package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseVideoCreateDTO;
import com.microservices.projectfinal.dto.CourseVideoResponseDTO;
import com.microservices.projectfinal.dto.CourseVideoUpdateDTO;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICourseVideoService {
    CourseVideoResponseDTO createCourseVideo(CourseVideoCreateDTO courseVideoCreateDTO);
    List<CourseVideoResponseDTO> getCourseVideoByCourseId(Long courseId);
    CourseVideoResponseDTO getCourseVideoById(Long userId, Long courseId, Long courseVideoId);

    Mono<Resource> getVideoResource(Long userId, Long courseId, Long courseVideoId);
    CourseVideoResponseDTO updateCourseVideo(Long courseId, Long courseVideoId, CourseVideoUpdateDTO courseVideoUpdateDTO);
    void deleteCourseVideo(Long courseId, Long courseVideoId);
}
