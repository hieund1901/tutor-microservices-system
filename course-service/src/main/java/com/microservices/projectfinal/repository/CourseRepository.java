package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Page<CourseEntity> findAllByTutorId(Long tutorId, Pageable pageable);
    Optional<CourseEntity> findById(Long courseId);
}
