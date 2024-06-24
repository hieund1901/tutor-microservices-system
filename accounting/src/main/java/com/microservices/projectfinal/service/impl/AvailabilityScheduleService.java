package com.microservices.projectfinal.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.projectfinal.data.dao.AvailabilityScheduleDao;
import com.microservices.projectfinal.data.model.GetAvailableRequest;
import com.microservices.projectfinal.dto.*;
import com.microservices.projectfinal.entity.AccountEntity;
import com.microservices.projectfinal.entity.AvailabilityEntity;
import com.microservices.projectfinal.entity.TutorEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.mapper.TimeMapper;
import com.microservices.projectfinal.repository.AccountRepository;
import com.microservices.projectfinal.repository.AvailabilityRepository;
import com.microservices.projectfinal.repository.DimTimeRepository;
import com.microservices.projectfinal.repository.TutorRepository;
import com.microservices.projectfinal.service.IAvailabilityScheduleService;
import com.microservices.projectfinal.service.IBookingService;
import com.microservices.projectfinal.service.ITutorService;
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
    private final AccountRepository accountRepository;
    private final TutorRepository tutorRepository;
    @Override
    public void createAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        var availabilities = availabilityScheduleCreateDTO.getAvailabilitySchedules();
        var availabilitiesEntityToSave = availabilities.stream().map(
                item -> checkAvailabilitySchedule(tutorId, item.getTimeKey())
        ).toList();
        availabilityRepository.saveAll(availabilitiesEntityToSave);
    }

    @Override
    public void updateAvailabilitySchedule(String tutorId, AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        var availabilities = availabilityScheduleCreateDTO.getAvailabilitySchedules();
        var availabilitiesEntityToUpdate = availabilities.stream().map(
                item -> {
                    var availabilityEntity = checkAvailabilitySchedule(tutorId, item.getTimeKey());
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
    public AvailabilitiesRegisterResponseDTO registerAvailability(String studentId,String tutorId, List<Long> availabilityIds) {
        var availabilities = availabilityRepository.findByDimTimeKeyInAndTutorId(availabilityIds, tutorId);
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

    @Override
    public AvailabilitiesResponseDTO getByIds(List<Long> ids) {
        var timeKeyNow = TimeKeyUtils.generateTimeKey(Instant.now(), LocalTime.now());
        var availabilities = availabilityRepository.findByIdInAndDimTimeKeyGreaterThanEqual(ids, timeKeyNow);
        var mapped = availabilities.stream().map(this::mapToAvailabilityResponseDTO).toList();
        return AvailabilitiesResponseDTO.builder()
                .availabilities(mapped)
                .build();
    }

    @Override
    public AvailabilitiesResponseDTO getAvailabilityScheduleByUserIdAndTimeKey(String userId, Long timeKey) {
        var startTimeKey = TimeKeyUtils.startTimeKey(timeKey);
        var endTimeKey = TimeKeyUtils.endTimeKey(timeKey);
        var availabilities = availabilityRepository.findByTutorIdAndDimTimeKeyBetweenAndAvailableIsTrue(userId, startTimeKey, endTimeKey);
        if (CollectionUtil.isEmpty(availabilities)) {
            throw new ResponseException("No availability found", HttpStatus.BAD_REQUEST);
        }


        return AvailabilitiesResponseDTO.builder()
                .availabilities(availabilities.stream().map(this::mapToAvailabilityResponseDTO).toList())
                .build();
    }


    private AvailabilityEntity checkAvailabilitySchedule(String tutorId, Long timeKey) {
        var availabilityEntity = availabilityRepository.findByTutorIdAndDimTimeKey(tutorId, timeKey);
        return availabilityEntity.orElseGet(() -> AvailabilityEntity.builder()
                .tutorId(tutorId)
                .dimTimeKey(timeKey)
                .build());
    }

    private Long getTimeKey(AvailabilityScheduleCreateDTO availabilityScheduleCreateDTO) {
        return availabilityScheduleCreateDTO.getTimeKey();
    }

    private AvailabilitiesResponseDTO.AvailabilityResponseDTO mapToAvailabilitiesResponseDTO(Map<String, Object> availabilities) {

        var tutor = getTutorByUserId((String) availabilities.get("tutorId"));
        var availabilitiesResponseDTOBuilder = AvailabilitiesResponseDTO.AvailabilityResponseDTO
                .builder()
                .id((Long) availabilities.get("id"))
                .tutor(tutor);

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
        var tutor = getTutorByUserId(availabilityEntity.getTutorId());
        return AvailabilitiesResponseDTO.AvailabilityResponseDTO.builder()
                .id(availabilityEntity.getId())
                .tutor(tutor)
                .time(timeDTO)
                .build();
    }

    public TutorResponseDTO getTutorByUserId(String userId) {
        AccountEntity account = accountRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseException("Account not found", HttpStatus.NOT_FOUND)
        );

        TutorEntity tutor = tutorRepository.getByAccountId(account.getId());
        if (tutor != null) {
            return buildTutorResponse(tutor);
        }
        return null;
    }

    private TutorResponseDTO buildTutorResponse(TutorEntity tutorEntity) {
        AccountEntity account = tutorEntity.getAccount();
        return TutorResponseDTO.builder()
                .teachFee(tutorEntity.getTeachFee())
                .subject(tutorEntity.getSubject())
                .avatarPath(account.getAvatarPath())
                .address(account.getAddress())
                .email(account.getEmail())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .userId(account.getUserId())
                .build();
    }
}
