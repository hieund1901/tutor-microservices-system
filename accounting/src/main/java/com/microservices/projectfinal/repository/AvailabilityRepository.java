package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {
    Optional<AvailabilityEntity> findByTutorIdAndDimTimeKey(String tutorId, Long dimTimeKey);
    List<AvailabilityEntity> findByDimTimeKeyInAndTutorId(List<Long> timeKeys, String tutorId);
    List<AvailabilityEntity> findByIdInAndDimTimeKeyGreaterThanEqual(List<Long> ids, Long timeKey);
    List<AvailabilityEntity> findByTutorIdAndDimTimeKeyBetweenAndAvailableIsTrue(String tutorId, Long start, Long end);
}
