package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByStudentIdAndAvailabilityIdIn(String studentId, List<Long> availabilityIds);

    Page<BookingEntity> findByStudentIdAndStatusIs(String studentId, BookingEntity.BookingStatus status, Pageable pageable);

    List<BookingEntity> findByIdIn(Collection<Long> ids);
}
