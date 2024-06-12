package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.client.api.account.TutorClient;
import com.microservices.projectfinal.dto.*;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.service.ICourseService;
import com.microservices.projectfinal.service.ICourseVideoService;
import com.microservices.projectfinal.util.MediaFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final TutorClient tutorClient;
    private final ICourseVideoService courseVideoService;
    private final MediaFileUtils mediaFileUtils;

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
}
