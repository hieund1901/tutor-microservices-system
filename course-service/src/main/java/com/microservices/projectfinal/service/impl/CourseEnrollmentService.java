package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.CourseEnrollmentDTO;
import com.microservices.projectfinal.entity.CourseEnrollmentEntity;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.entity.PaymentTransactionEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.repository.CourseEnrollmentRepository;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.repository.PaymentTransactionRepository;
import com.microservices.projectfinal.service.ICourseEnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseEnrollmentService implements ICourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    @Override
    public CourseEnrollmentDTO enrollCourse(Long courseId, String userId) {
        var courseEnrollmentEntityOptional = courseEnrollmentRepository.findByCourseIdAndStudentId(courseId, userId);
        if (courseEnrollmentEntityOptional.isPresent()) {
            throw new ResponseException("Already enrolled", HttpStatus.BAD_REQUEST);
        }

        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseException("Course not found", HttpStatus.NOT_FOUND));

        CourseEnrollmentEntity courseEnrollmentEntity = CourseEnrollmentEntity.builder()
                .course(courseEntity)
                .studentId(userId)
                .enrollmentDate(Instant.now())
                .status(CourseEnrollmentEntity.EnrollmentStatus.INACTIVE)
                .build();

        var courseEnrollment = courseEnrollmentRepository.save(courseEnrollmentEntity);

        PaymentTransactionEntity paymentTransaction = PaymentTransactionEntity.builder()
                .referenceIds(courseEnrollment.getId().toString())
                .userId(userId)
                .referenceType(PaymentTransactionEntity.ReferenceType.COURSE)
                .purchaseStatus(PaymentTransactionEntity.PurchaseStatus.PENDING)
                .build();

        var transactionSaved = paymentTransactionRepository.save(paymentTransaction);

        return CourseEnrollmentDTO.builder()
                .id(courseEnrollment.getId())
                .courseId(courseEntity.getId())
                .studentId(userId)
                .transactionId(transactionSaved.getId())
                .enrollmentDate(courseEnrollment.getEnrollmentDate())
                .status(courseEnrollment.getStatus().name())
                .createdAt(courseEnrollment.getCreatedAt())
                .modifiedAt(courseEnrollment.getModifiedAt())
                .build();
    }

    @Override
    public void unEnrollCourse(Long courseId, Long userId) {

    }

    @Override
    public boolean isEnrolled(Long courseId, String userId) {
        var courseEnrollmentEntityOptional = courseEnrollmentRepository.findByCourseIdAndStudentId(courseId, userId);
        return courseEnrollmentEntityOptional.filter(courseEnrollmentEntity -> courseEnrollmentEntity.getStatus() == CourseEnrollmentEntity.EnrollmentStatus.ACTIVE).isPresent();
    }

    @Transactional
    @Override
    public void activateCourse(Long transactionId, String userId) {
        var coursePaymentTransaction = paymentTransactionRepository.findByReferenceIdsAndReferenceTypeAndUserId(String.valueOf(transactionId), PaymentTransactionEntity.ReferenceType.COURSE, userId)
                .orElse(null);
        if (coursePaymentTransaction == null) {
            log.error("Transaction not found");
            return;
        }

        coursePaymentTransaction.setPurchaseStatus(PaymentTransactionEntity.PurchaseStatus.APPROVED);
        List<Long> referenceIds = Arrays.stream(coursePaymentTransaction.getReferenceIds().split(",")).map(Long::parseLong).toList();
        var courseEnrollmentId = Objects.requireNonNull(referenceIds).stream().findFirst().orElseThrow(
                () -> new ResponseException("Reference id not found", HttpStatus.BAD_REQUEST)
        );
        var courseEnrollment = courseEnrollmentRepository.findById(courseEnrollmentId).orElse(null);
        if (courseEnrollment == null) {
            log.error("Course enrollment not found");
            return;
        }

        courseEnrollment.setStatus(CourseEnrollmentEntity.EnrollmentStatus.ACTIVE);
        courseEnrollmentRepository.save(courseEnrollment);
        paymentTransactionRepository.save(coursePaymentTransaction);
    }

    @Override
    public CourseEnrollmentDTO getEnrolledCourses(Long id) {
        return courseEnrollmentRepository.findById(id)
                .map(courseEnrollmentEntity -> CourseEnrollmentDTO.builder()
                        .id(courseEnrollmentEntity.getId())
                        .courseId(courseEnrollmentEntity.getCourse().getId())
                        .studentId(courseEnrollmentEntity.getStudentId())
                        .enrollmentDate(courseEnrollmentEntity.getEnrollmentDate())
                        .status(courseEnrollmentEntity.getStatus().name())
                        .createdAt(courseEnrollmentEntity.getCreatedAt())
                        .modifiedAt(courseEnrollmentEntity.getModifiedAt())
                        .build())
                .orElseThrow(() -> new ResponseException("Course enrollment not found", HttpStatus.NOT_FOUND));
    }
}
