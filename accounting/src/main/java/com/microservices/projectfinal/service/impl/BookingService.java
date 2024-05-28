package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.entity.BookingEntity;
import com.microservices.projectfinal.entity.PaymentTransactionEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.repository.BookingRepository;
import com.microservices.projectfinal.repository.PaymentTransactionRepository;
import com.microservices.projectfinal.service.IBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    @Override
    public void createBooking(String studentId, List<Long> availabilityIds) {
        var bookingExists = bookingRepository.findByStudentIdAndAvailabilityIdContaining(studentId, availabilityIds);
        if (bookingExists.isPresent()) {
            throw new ResponseException("Booking already exists", HttpStatus.BAD_REQUEST);
        }
        var bookingEntities = availabilityIds.stream().map(availabilityId -> BookingEntity.builder()
                .studentId(studentId)
                .availabilityId(availabilityId)
                .build()
        ).toList();

        var saved = bookingRepository.saveAll(bookingEntities);

        var transaction = PaymentTransactionEntity.builder()
                .referenceIds(saved.stream().map(BookingEntity::getId).toList())
                .userId(studentId)
                .referenceType(PaymentTransactionEntity.ReferenceType.CALL)
                .purchaseStatus(PaymentTransactionEntity.PurchaseStatus.PENDING)
                .build();
        paymentTransactionRepository.save(transaction);
    }
}
