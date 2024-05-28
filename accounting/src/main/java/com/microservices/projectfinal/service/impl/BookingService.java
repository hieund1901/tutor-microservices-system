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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    @Override
    public void createBooking(String studentId, List<Long> availabilityIds) {
        var bookingExists = bookingRepository.findByStudentIdAndAvailabilityIdIn(studentId, availabilityIds);
        if (bookingExists.isPresent()) {
            throw new ResponseException("Booking already exists", HttpStatus.BAD_REQUEST);
        }
        var bookingEntities = availabilityIds.stream().map(availabilityId -> BookingEntity.builder()
                .studentId(studentId)
                .availabilityId(availabilityId)
                .build()
        ).toList();

        var saved = bookingRepository.saveAll(bookingEntities);
        var referenceIds = saved.stream().map(BookingEntity::getId).map(String::valueOf).collect(Collectors.joining(", "));

        var transaction = PaymentTransactionEntity.builder()
                .referenceIds(referenceIds)
                .userId(studentId)
                .referenceType(PaymentTransactionEntity.ReferenceType.CALL)
                .purchaseStatus(PaymentTransactionEntity.PurchaseStatus.PENDING)
                .build();
        paymentTransactionRepository.save(transaction);
    }
}
