package com.microservices.projectfinal.service;

import java.util.List;

public interface IBookingService {
    void createBooking(String studentId, List<Long> availabilityId);
}
