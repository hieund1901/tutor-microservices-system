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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class CourseEnrollmentService implements ICourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    @Override
    public CourseEnrollmentDTO enrollCourse(Long courseId, Long userId) {
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
                .referenceId(courseEnrollment.getId())
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
    public boolean isEnrolled(Long courseId, Long userId) {
        return false;
    }
}
