package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.annotation.UserId;
import com.microservices.projectfinal.dto.AvailabilitySchedulesCreateDTO;
import com.microservices.projectfinal.service.IAvailabilityScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/availability-schedule")
public class AvailabilityScheduleController {
    private final IAvailabilityScheduleService availabilityScheduleService;

    @PostMapping
    public ResponseEntity<?> createAvailabilitySchedule(@UserId String userId, @RequestBody AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        availabilityScheduleService.createAvailabilitySchedule(userId, availabilityScheduleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<?> updateAvailabilitySchedule(@UserId String userId, @RequestBody AvailabilitySchedulesCreateDTO availabilityScheduleCreateDTO) {
        availabilityScheduleService.updateAvailabilitySchedule(userId, availabilityScheduleCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<?> getAvailabilityScheduleByUserId(@UserId String userId) {
        return ResponseEntity.ok(availabilityScheduleService.getAvailabilitySchedule(userId));
    }

    @GetMapping("/{tutorId}")
    public ResponseEntity<?> getAvailabilityScheduleByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(availabilityScheduleService.getAvailabilitySchedule(tutorId));
    }

    @GetMapping("/register/{tutorId}")
    public ResponseEntity<?> registerAvailabilitySchedule(@UserId String studentId, @PathVariable String tutorId, @RequestParam(name = "timeKeys") List<Long> availabilityIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityScheduleService.registerAvailability(studentId, tutorId,availabilityIds));
    }

    @GetMapping(value = "/{tutorId}", params = "timeKey")
    public ResponseEntity<?> getAvailabilityScheduleByTimeKey(@PathVariable String tutorId,@RequestParam Long timeKey) {
        return ResponseEntity.ok(availabilityScheduleService.getAvailabilityScheduleByUserIdAndTimeKey(tutorId, timeKey));
    }
}
