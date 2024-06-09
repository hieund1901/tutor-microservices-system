package com.microservices.projectfinal.service;

import java.util.List;

public interface IBookingService {
    Long createBookingAndTakeTransactionId(String studentId, List<Long> availabilityId);
    void activeBooking(Long transactionId, String userId);
}
