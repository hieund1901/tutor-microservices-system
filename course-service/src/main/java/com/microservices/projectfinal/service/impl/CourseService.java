package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.client.api.account.TutorClient;
import com.microservices.projectfinal.dto.CourseCreateDTO;
import com.microservices.projectfinal.dto.CourseResponseDTO;
import com.microservices.projectfinal.dto.TutorResponse;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.security.AuthenticationFacade;
import com.microservices.projectfinal.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final TutorClient tutorClient;

    @Override
    public CourseResponseDTO createCourse(CourseCreateDTO courseCreateDTO) {


        TutorResponse tutorResponse = tutorClient.getTutorByEmail("sonld6@gmail.com");


        var courseEntityBuilder = CourseEntity.builder()
                .courseName(courseCreateDTO.getName())
                .description(courseCreateDTO.getDescription())
                .subject(courseCreateDTO.getSubject())
                .price(courseCreateDTO.getPrice())
                .tutorId(tutorResponse.getAccountId()).build();

        CourseEntity courseEntity = courseRepository.save(courseEntityBuilder);

        return CourseResponseDTO.builder()
                .id(courseEntity.getId())
                .name(courseEntity.getCourseName())
                .description(courseEntity.getDescription())
                .subject(courseEntity.getSubject())
                .price(courseEntity.getPrice())
                .build();

    }

    @Override
    public CourseResponseDTO updateCourse(CourseCreateDTO courseCreateDTO, Long courseId) {
        return null;
    }
}
