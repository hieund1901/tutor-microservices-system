package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.client.api.account.TutorClient;
import com.microservices.projectfinal.dto.*;
import com.microservices.projectfinal.entity.CourseEnrollmentEntity;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.repository.CourseEnrollmentRepository;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.security.AuthorDetails;
import com.microservices.projectfinal.service.ICourseEnrollmentService;
import com.microservices.projectfinal.service.ICourseService;
import com.microservices.projectfinal.service.ICourseVideoService;
import com.microservices.projectfinal.util.MediaFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final TutorClient tutorClient;
    private final ICourseVideoService courseVideoService;
    private final MediaFileUtils mediaFileUtils;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    @Transactional
    @Override
    public CourseResponseDTO createCourse(String userId, CourseCreateDTO courseCreateDTO) {
        TutorResponse tutorResponse = tutorClient.getTutorByUserId(userId);
        String thumbnailPath = mediaFileUtils.saveImage(courseCreateDTO.getThumbnail());

        var courseEntityBuilder = CourseEntity.builder()
                .courseName(courseCreateDTO.getName())
                .description(courseCreateDTO.getDescription())
                .subject(courseCreateDTO.getSubject())
                .price(courseCreateDTO.getPrice())
                .tutorId(tutorResponse.getAccountId())
                .userId(userId)
                .thumbnailPath(thumbnailPath)
                .build();

        CourseEntity courseEntity = courseRepository.save(courseEntityBuilder);

        return buildCourseResponse(courseEntity, tutorResponse);
    }

    private CourseResponseDTO buildCourseResponse(CourseEntity courseEntity, TutorResponse tutorResponse) {
        List<CourseVideoResponseDTO> courseVideoResponseDTOS = courseVideoService.getCourseVideoByCourseId(courseEntity.getId());
        return CourseResponseDTO.builder()
                .id(courseEntity.getId())
                .name(courseEntity.getCourseName())
                .description(courseEntity.getDescription())
                .subject(courseEntity.getSubject())
                .price(courseEntity.getPrice())
                .tutor(tutorResponse)
                .thumbnailPath(courseEntity.getThumbnailPath())
                .courseVideos(courseVideoResponseDTOS)
                .build();
    }

    @Override
    public CourseResponseDTO updateCourse(CourseCreateDTO courseCreateDTO, Long courseId) {
        return null;
    }

    @Override
    public ListCourseResponse getListCourse(int page, int size) {
        Page<CourseEntity> courseEntities = courseRepository.findAll(
                PageRequest.of(page - 1, size));
        List<CourseResponseDTO> courseResponseDTOS = courseEntities.map(courseEntity -> {
            TutorResponse tutorResponse = tutorClient.getTutorByUserId(courseEntity.getUserId());
            return buildCourseResponse(courseEntity, tutorResponse);
        }).toList();
        return ListCourseResponse.builder()
                .courses(courseResponseDTOS)
                .totalPage(courseEntities.getTotalPages())
                .totalElements(courseEntities.getTotalElements())
                .build();
    }

    @Override
    public InputStream getThumbnail(String thumbnailPath) {
        return mediaFileUtils.getImage(thumbnailPath);
    }

    @Override
    public CourseResponseDTO getCourse(Long courseId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow();
        TutorResponse tutorResponse = tutorClient.getTutorByUserId(courseEntity.getUserId());
        return buildCourseResponse(courseEntity, tutorResponse);
    }

    @Override
    public ListCourseResponse getListCourseBought(String userId, int page, int size) {
        var enrollments = courseEnrollmentRepository.findByStudentIdAndStatusIs(userId, CourseEnrollmentEntity.EnrollmentStatus.ACTIVE, PageRequest.of(page - 1, size));
        List<CourseResponseDTO> courseResponseDTOS = enrollments.stream().map(enrollment -> {
            CourseEntity courseEntity = enrollment.getCourse();
            TutorResponse tutorResponse = tutorClient.getTutorByUserId(courseEntity.getUserId());
            return buildCourseResponse(courseEntity, tutorResponse);
        }).toList();
        return ListCourseResponse.builder()
                .courses(courseResponseDTOS)
                .page(page)
                .size(size)
                .totalElements(enrollments.getTotalElements())
                .totalPage(enrollments.getTotalPages())
                .build();
    }

    @Override
    public ListCourseResponse getCoursesByUserId(String userId) {
        Page<CourseEntity> courseEntities = courseRepository.findAllByUserId(userId, PageRequest.of(0, 10));

        List<CourseResponseDTO> courseResponseDTOS = courseEntities.map(courseEntity -> {
            TutorResponse tutorResponse = tutorClient.getTutorByUserId(courseEntity.getUserId());
            return buildCourseResponse(courseEntity, tutorResponse);
        }).toList();

        return ListCourseResponse.builder()
                .courses(courseResponseDTOS)
                .totalPage(courseEntities.getTotalPages())
                .totalElements(courseEntities.getTotalElements())
                .build();
    }
}
