package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.CourseVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideoEntity, Long>{
    List<CourseVideoEntity> findByCourseId(Long courseId);
    CourseVideoEntity findByCourseIdAndId(Long courseId, Long courseVideoId);
}
