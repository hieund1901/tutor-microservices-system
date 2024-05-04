package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;

public interface ICourseService {
    CourseResponseDTO createCourse(CourseCreateDTO courseCreateDTO);
    CourseResponseDTO updateCourse(CourseCreateDTO courseCreateDTO, Long courseId);
}
