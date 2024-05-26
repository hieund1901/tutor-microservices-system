package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.AvailabilitiesResponseDTO;
import com.microservices.projectfinal.dto.AvailabilityScheduleCreateDTO;
import com.microservices.projectfinal.dto.AvailabilitySchedulesCreateDTO;
import com.microservices.projectfinal.entity.AvailabilityEntity;
import com.microservices.projectfinal.repository.AvailabilityRepository;
import com.microservices.projectfinal.service.IAvailabilityScheduleService;
import com.microservices.projectfinal.util.TimeKeyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AvailabilityScheduleService implements IAvailabilityScheduleService {
    private final AvailabilityRepository availabilityRepository;

    @Override
    public void createAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        var availabilities = availabilityScheduleCreateDTO.getAvailabilitySchedules();
        var availabilitiesEntityToSave = availabilities.stream().map(
                item -> checkAvailabilitySchedule(tutorId, item)
        ).toList();
        availabilityRepository.saveAll(availabilitiesEntityToSave);
    }

    @Override
    public void updateAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        var availabilities = availabilityScheduleCreateDTO.getAvailabilitySchedules();
        var availabilitiesEntityToUpdate = availabilities.stream().map(
                item -> {
                    var availabilityEntity = checkAvailabilitySchedule(tutorId, item);
                    availabilityEntity.setAvailable(item.isAvailable());
                    return availabilityEntity;
                }
        ).toList();

        availabilityRepository.saveAll(availabilitiesEntityToUpdate);
    }

    @Override
    public AvailabilitiesResponseDTO getAvailabilitySchedule(String tutorId) {
        var timeKeyNow = TimeKeyUtils.generateTimeKey(Instant.now(), LocalTime.now());
        var availabilities = availabilityRepository.findByTutorIdAndDimTimeKeyGreaterThanEqual(tutorId, timeKeyNow);

        return null;
    }

    private AvailabilityEntity checkAvailabilitySchedule(String tutorId, AvailabilityScheduleCreateDTO availabilityScheduleCreateDTO) {
        var timeKey = getTimeKey(availabilityScheduleCreateDTO);
        var availabilityEntity = availabilityRepository.findByTutorIdAndDimTimeKey(tutorId, timeKey);
        return availabilityEntity.orElseGet(() -> AvailabilityEntity.builder()
                .tutorId(tutorId)
                .dimTimeKey(timeKey)
                .build());
    }

    private Long getTimeKey(AvailabilityScheduleCreateDTO availabilityScheduleCreateDTO) {
        var time = availabilityScheduleCreateDTO.getStartTime();
        var day = availabilityScheduleCreateDTO.getDayStart();
        return TimeKeyUtils.generateTimeKey(day, time);
    }
}
