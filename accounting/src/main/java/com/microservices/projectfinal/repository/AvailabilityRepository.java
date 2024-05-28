package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {
    Optional<AvailabilityEntity> findByTutorIdAndDimTimeKey(String tutorId, Long dimTimeKey);
    List<AvailabilityEntity> findByIdContainsAndDimTimeKeyGreaterThan(List<Long> ids, Long dimTimeKey);
}
