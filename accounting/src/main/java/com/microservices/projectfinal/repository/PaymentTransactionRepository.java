package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {
    Optional<PaymentTransactionEntity> findByReferenceIdsLikeAndReferenceTypeAndUserId(String referenceIds, PaymentTransactionEntity.ReferenceType referenceType, String userId);
    Optional<PaymentTransactionEntity> findByIdAndUserId(Long id, String userId);
}
