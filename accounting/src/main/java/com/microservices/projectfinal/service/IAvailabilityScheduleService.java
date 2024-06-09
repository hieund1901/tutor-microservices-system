package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.AvailabilitiesRegisterResponseDTO;
import com.microservices.projectfinal.dto.AvailabilitiesResponseDTO;
import com.microservices.projectfinal.dto.AvailabilitySchedulesCreateDTO;

import java.util.List;

public interface IAvailabilityScheduleService {
    void createAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO);
    void updateAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO);
    AvailabilitiesResponseDTO getAvailabilitySchedule(String tutorId);
    AvailabilitiesRegisterResponseDTO registerAvailability(String studentId, List<Long> availabilityId);
}
