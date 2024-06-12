package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.dto.ListCourseResponse;

import java.io.InputStream;

public interface ICourseService {
    CourseResponseDTO createCourse(String userId, CourseCreateDTO courseCreateDTO);

    CourseResponseDTO updateCourse(CourseCreateDTO courseCreateDTO, Long courseId);

    ListCourseResponse getListCourse(int page, int size);

    InputStream getThumbnail(String thumbnailPath);

    CourseResponseDTO getCourse(Long courseId);

}
