package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<TutorEntity, Long> {
    TutorEntity getByAccountId(Long accountId);
}
