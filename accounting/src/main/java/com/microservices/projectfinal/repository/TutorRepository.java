package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.TutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorRepository extends JpaRepository<TutorEntity, Long> {
    TutorEntity getByAccountId(Long accountId);
    Page<TutorEntity> findAll(Pageable pageable);
}
