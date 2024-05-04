package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseVideoCreateDTO;
import com.microservices.projectfinal.dto.CourseVideoResponseDTO;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICourseVideoService {
    void createCourseVideo(CourseVideoCreateDTO courseVideoCreateDTO);
    List<CourseVideoResponseDTO> getCourseVideoByCourseId(Long courseId);
    CourseVideoResponseDTO getCourseVideoById(Long courseId, Long courseVideoId);

    Mono<Resource> getVideoResource(Long courseId, Long courseVideoId);
}
