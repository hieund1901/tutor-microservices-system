package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.dto.ListCourseResponse;

public interface ICourseService {
    CourseResponseDTO createCourse(String userId, CourseCreateDTO courseCreateDTO);

    CourseResponseDTO updateCourse(CourseCreateDTO courseCreateDTO, Long courseId);

    ListCourseResponse getListCourse(String userId,int page, int size);

}
