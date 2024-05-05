package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.entity.CourseEnrollmentEntity;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.entity.CoursePurchaseEntity;
import com.microservices.projectfinal.repository.CourseEnrollmentRepository;
import com.microservices.projectfinal.repository.CoursePurchaseRepository;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.service.ICourseEnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class CourseEnrollmentService implements ICourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final CoursePurchaseRepository coursePurchaseRepository;

    @Transactional
    @Override
    public void enrollCourse(Long courseId, Long userId) {
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseEnrollmentEntity courseEnrollmentEntity = CourseEnrollmentEntity.builder()
                .course(courseEntity)
                .studentId(userId)
                .enrollmentDate(Instant.now())
                .build();

        CoursePurchaseEntity coursePurchaseEntity = CoursePurchaseEntity.builder()
                .course(courseEntity)
                .studentId(userId)
                .build();

        coursePurchaseRepository.save(coursePurchaseEntity);
        courseEnrollmentRepository.save(courseEnrollmentEntity);
    }

    @Override
    public void unEnrollCourse(Long courseId, Long userId) {

    }

    @Override
    public boolean isEnrolled(Long courseId, Long userId) {
        return false;
    }
}
