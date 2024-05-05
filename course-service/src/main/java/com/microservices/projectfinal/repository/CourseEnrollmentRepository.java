package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.CourseEnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentEntity, Long> {
}
