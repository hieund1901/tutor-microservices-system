package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.CoursePurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePurchaseRepository extends JpaRepository<CoursePurchaseEntity, Long> {
}
