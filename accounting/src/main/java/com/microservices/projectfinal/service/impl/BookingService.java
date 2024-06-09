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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    @Override
    public Long createBookingAndTakeTransactionId(String studentId, List<Long> availabilityIds) {
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
        var transactionSaved = paymentTransactionRepository.save(transaction);
        return transactionSaved.getId();
    }

    @Override
    public void activeBooking(Long transactionId, String userId) {
        var transaction = paymentTransactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResponseException("Transaction not found", HttpStatus.NOT_FOUND));

        if (transaction.getPurchaseStatus() == PaymentTransactionEntity.PurchaseStatus.PENDING) {
            transaction.setPurchaseStatus(PaymentTransactionEntity.PurchaseStatus.APPROVED);
            paymentTransactionRepository.save(transaction);
        }

        var referenceIds = Arrays.stream(transaction.getReferenceIds().split(", ")).map(Long::parseLong).collect(Collectors.toList());
        var bookingEntities = bookingRepository.findByIdIn(referenceIds);
        bookingEntities.forEach(bookingEntity -> bookingEntity.setStatus(BookingEntity.BookingStatus.CONFIRMED));
        bookingRepository.saveAll(bookingEntities);
    }
}
