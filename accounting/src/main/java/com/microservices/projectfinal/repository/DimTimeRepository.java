package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.DimTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimTimeRepository extends JpaRepository<DimTimeEntity, Long>{
    DimTimeEntity findByTimeKey(Long timeKey);
}
