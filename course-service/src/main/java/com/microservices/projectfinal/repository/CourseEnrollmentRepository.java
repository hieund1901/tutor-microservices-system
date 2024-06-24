package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.CourseEnrollmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentEntity, Long> {
    Optional<CourseEnrollmentEntity> findByCourseIdAndStudentId(Long courseId, String studentId);
    boolean existsByCourseIdAndStudentId(Long courseId, String studentId);
    Page<CourseEnrollmentEntity> findByStudentIdAndStatusIs(String studentId, CourseEnrollmentEntity.EnrollmentStatus status, Pageable pageable);
}
