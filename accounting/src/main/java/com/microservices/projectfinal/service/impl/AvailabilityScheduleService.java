package com.microservices.projectfinal.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.projectfinal.data.dao.AvailabilityScheduleDao;
import com.microservices.projectfinal.data.model.GetAvailableRequest;
import com.microservices.projectfinal.dto.*;
import com.microservices.projectfinal.entity.AvailabilityEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.mapper.TimeMapper;
import com.microservices.projectfinal.repository.AvailabilityRepository;
import com.microservices.projectfinal.repository.DimTimeRepository;
import com.microservices.projectfinal.service.IAvailabilityScheduleService;
import com.microservices.projectfinal.service.IBookingService;
import com.microservices.projectfinal.util.TimeKeyUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AvailabilityScheduleService implements IAvailabilityScheduleService {
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityScheduleDao availabilityScheduleDao;
    private final IBookingService bookingService;
    private final DimTimeRepository dimTimeRepository;
    private final TimeMapper timeMapper;

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
        var availabilities = availabilityScheduleDao.getAvailabilitySchedule(GetAvailableRequest.builder()
                .dimTimeKey(timeKeyNow)
                .tutorId(tutorId)
                .build());

        var mapped = availabilities.stream().map(this::mapToAvailabilitiesResponseDTO).toList();

        return AvailabilitiesResponseDTO.builder()
                .availabilities(mapped)
                .build();
    }

    @Transactional
    @Override
    public AvailabilitiesRegisterResponseDTO registerAvailability(String studentId, List<Long> availabilityIds) {
        var availabilities = availabilityRepository.findByIdInAndDimTimeKeyGreaterThan(availabilityIds, TimeKeyUtils.generateTimeKey(Instant.now(), LocalTime.now()));
        if (CollectionUtil.isEmpty(availabilities)) {
            throw new ResponseException("No availability found", HttpStatus.BAD_REQUEST);
        }

        var availabilitiesNotAvailable = availabilities.stream().filter(item -> !item.isAvailable()).toList();
        if (CollectionUtil.isNotEmpty(availabilitiesNotAvailable)) {
            var timeKeys = availabilitiesNotAvailable.stream().map(AvailabilityEntity::getDimTimeKey).toList();
            String message = String.format("Some availabilities not available: %s", timeKeys);
            throw new ResponseException(message, HttpStatus.BAD_REQUEST);
        }

        var makeNotAvailable = availabilities.stream().peek(item -> item.setAvailable(false)).toList();
        var availabilitiesSaved = availabilityRepository.saveAll(makeNotAvailable);
        var transactionId = bookingService.createBookingAndTakeTransactionId(studentId, availabilitiesSaved.stream().map(AvailabilityEntity::getId).toList());
        return AvailabilitiesRegisterResponseDTO.builder()
                .transactionId(transactionId)
                .availabilities(availabilitiesSaved.stream().map(this::mapToAvailabilityResponseDTO).toList())
                .build();
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

    private AvailabilitiesResponseDTO.AvailabilityResponseDTO mapToAvailabilitiesResponseDTO(Map<String, Object> availabilities) {
        var availabilitiesResponseDTOBuilder = AvailabilitiesResponseDTO.AvailabilityResponseDTO
                .builder()
                .id((Long) availabilities.get("id"))
                .tutorId((String) availabilities.get("tutor_id"));
        ;
        var timeDTO = new TimeDTO();
        for (var field : timeDTO.getClass().getDeclaredFields()) {
            var fieldAnnotation = field.getAnnotation(JsonProperty.class);
            var fieldName = fieldAnnotation.value();
            var type = field.getType();
            var fieldValue = availabilities.get(fieldName);
            try {
                field.setAccessible(true);
                if (fieldValue instanceof Time && type == String.class) {
                    field.set(timeDTO, fieldValue.toString());
                } else {
                    field.set(timeDTO, fieldValue);
                }
            } catch (IllegalAccessException e) {
                log.error("Error when set field {} with value {}", fieldName, fieldValue);
            }
        }

        availabilitiesResponseDTOBuilder.time(timeDTO);
        return availabilitiesResponseDTOBuilder.build();
    }

    private AvailabilitiesResponseDTO.AvailabilityResponseDTO mapToAvailabilityResponseDTO(AvailabilityEntity availabilityEntity) {
        var time = dimTimeRepository.findByTimeKey(availabilityEntity.getDimTimeKey());
        var timeDTO = timeMapper.toTimeDTO(time);
        return AvailabilitiesResponseDTO.AvailabilityResponseDTO.builder()
                .id(availabilityEntity.getId())
                .tutorId(availabilityEntity.getTutorId())
                .time(timeDTO)
                .build();
    }
}
