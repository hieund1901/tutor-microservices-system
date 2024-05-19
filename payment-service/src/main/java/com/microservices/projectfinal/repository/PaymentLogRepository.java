package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.PaymentLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLogEntity, Long> {
}
